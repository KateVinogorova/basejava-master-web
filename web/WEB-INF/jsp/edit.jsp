<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" class="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<section class="section_container">
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">

        <%--Name--%>
        <p class="edit_section_header">Имя</p>
        <input type="text" name="name" size="50" value="${resume.fullName}" style="margin: 20px 0;">
        <%--Contacts--%>
        <p class="edit_section_header">Контакты</p>
        <dl>
            <c:forEach var="contactType" items="${ContactType.values()}">
                <jsp:useBean id="contactType" type="com.urise.webapp.model.ContactType"/>
                <label class="edit_contacts_title">${contactType.getTitle()}:
                    <input type="text" name="${contactType.name()}" size="50"
                           value="${resume.getContactType(contactType)}"
                           class="edit_contacts_input">
                </label>
            </c:forEach>
        </dl>
        <%--Sections--%>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <p class="edit_section_header">${type.getTitle()}</p>
            <c:choose>
                <c:when test="${type==\"OBJECTIVE\"}">
                    <input type='text' name='${type.name()}' size=105 value='${section}' class="edit_sections">
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <textarea name='${type.name()}' cols=100 rows=5 class="edit_sections">${section}</textarea>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type.name()}' cols=100 rows=5 class="edit_sections">
                        <%=String.join("\n", ((ListSection) section).getSectionData())%>
                    </textarea>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="organization" items="${section.sectionData}" varStatus="counter">
                        <jsp:useBean id="organization" type="com.urise.webapp.model.Organization"/>
                        <div class="edit_position_sections">
                            <label for="edit_org_name" class="label_inline">Наименование организации</label>
                            <input type="text" name="${type}" value="${organization.link.name}" id="edit_org_name"
                                   size="70">
                            <label for="edit_org_website" class="label_inline">Сайт организации</label>
                            <input type="text" name="url${type}" value="${organization.link.url}"
                                   id="edit_org_website">
                        </div>
                        <c:forEach var="position" items="${organization.positionList}">
                            <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>
                            <div class="edit_position_sections">
                                <label for="edit_org_pos_date1" class="label_inline">Дата начала</label>
                                <input type="text" name="${type}${counter.index}startDate" value="${position.startDate}"
                                       id="edit_org_pos_date1" size="10" placeholder="YYYY-MM-DD">
                                <label for="edit_org_pos_date2" class="label_inline">Дата окончания</label>
                                <input type="text" name="${type}${counter.index}endDate" value="${position.endDate}"
                                       id="edit_org_pos_date2" size="10" placeholder="YYYY-MM-DD">
                            </div>
                            <label for="edit_org_pos_name" class="label_inline">Наименование позиции</label>
                            <input type="text" name="${type}${counter.index}posName" value="${position.title}"
                                   id="edit_org_pos_name"
                                   size="70">
                            <c:if test="<%=position.getDescription()!=null%>">
                                <div class="edit_org_pos_descript_container">
                                    <label for="edit_org_pos_description">Описание позиции</label>
                                    <textarea name="${type}${counter.index}posDescription" cols=100 rows=5
                                              class="edit_sections"
                                              id="edit_org_pos_description">
                                            ${position.description}</textarea>
                                </div>
                            </c:if>
                            <button class="btn_add" type="submit">+ Добавить</button>
                        </c:forEach>
                        <hr/>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit" class="btn">Сохранить</button>
        <button onclick="window.history.back()" class="btn_cancel">Отменить</button>
    </form>
</section>
</body>
</html>
