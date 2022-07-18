package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("resumes", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
            return;
        }
        Resume resume = null;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                resp.sendRedirect("resume");
                break;
            case "add":
                resume = Resume.EMPTY;
                break;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = storage.get(uuid);
//                for (SectionType type : SectionType.values()) {
//                    AbstractSection section = resume.getSection(type);
//                    switch (type) {
//                        case PERSONAL:
//                        case OBJECTIVE:
//                            if (section == null) {
//                                section = TextSection.EMPTY;
//                            }
//                            break;
//                        case ACHIEVEMENT:
//                        case QUALIFICATIONS:
//                            if (section == null) {
//                                section = ListSection.EMPTY;
//                            }
//                            break;
//                        case EDUCATION:
//                        case EXPERIENCE:
//                            OrganizationSection orgSection = (OrganizationSection) section;
//                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
//                            emptyFirstOrganizations.add(Organization.EMPTY);
//                            if (orgSection != null) {
//                                for (Organization org : orgSection.getSectionData()) {
//                                    List<Organization.Position> emptyFirstPositions = new ArrayList<>();
//                                    emptyFirstPositions.add(Organization.Position.EMPTY);
//                                    emptyFirstPositions.addAll(org.getPositionList());
//                                    emptyFirstOrganizations.add(new Organization(org.getLink(), emptyFirstPositions));
//                                }
//                            }
//                            section = new OrganizationSection(emptyFirstOrganizations);
//                            break;
//                    }
//                    resume.addSection(type, section);
//                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("resume", resume);
        req.getRequestDispatcher((action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        if (req.getCharacterEncoding() == null) {
            req.setCharacterEncoding("UTF-8");
        }
        String uuid = req.getParameter("uuid");
        Resume resume = storage.get(uuid);

        resume.setFullName(req.getParameter("name"));
        for (ContactType type : ContactType.values()) {
            String contact = req.getParameter(type.getTitle());
            if (contact != null && !contact.equals("")) {
                resume.addContact(type, contact);
            }
        }

        for (SectionType type : SectionType.values()) {
            String sectionContent = req.getParameter(type.name());
            String[] values = req.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(sectionContent) && values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(type, new TextSection(sectionContent));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(type, new ListSection(sectionContent.split("\\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = new ArrayList<>();
                        for (int i = 0; i < values.length; i++) {
                            String prfx = type.name() + i;
                            Link link = new Link(values[i], req.getParameter("url" + type.name()));
                            String[] positions = req.getParameterValues(prfx + "posName");
                            List<Organization.Position> positionList = new ArrayList<>();
                            for (int j = 0; j < positions.length; j++) {
                                Organization.Position position = new Organization.Position(
                                        DateUtil.parse(req.getParameter(prfx + "startDate")),
                                        DateUtil.parse(req.getParameter(prfx + "endDate")),
                                        positions[j],
                                        req.getParameter(prfx + "posDescription")
                                );
                                positionList.add(position);
                            }
                            organizationList.add(new Organization(link, positionList));
                        }
                        resume.setSection(type, new OrganizationSection(organizationList));
                        break;
                }
            }
        }
        storage.update(resume);
        req.setAttribute("resume", resume);
        resp.sendRedirect("resume");
    }
}
