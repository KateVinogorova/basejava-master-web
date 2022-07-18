<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" class="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>

<%--Full Name--%>
<ul class="section_header">
    <li class="section_header_name">Имя</li>
    <li><a href="resume?uuid=${resume.uuid}&action=edit" class="view_edit">Edit</a></li>
</ul>
<section class="section_container">
    <h2 class="view_full_name">${resume.fullName}&nbsp;</h2>
</section>

<%--Contacts--%>
<ul class="section_header">
    <li class="section_header_name">Контакты</li>
    <li><a href="resume?uuid=${resume.uuid}&action=edit" class="view_edit">Edit</a></li>
</ul>
<section class="section_container">
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
        <li class="view_contact_item">
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </li>
    </c:forEach>
    </ul>
</section>

<%--Sections--%>
<c:forEach var="sectionEntry" items="${resume.sections}">
    <jsp:useBean id="sectionEntry"
                 type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
    <ul class="section_header">
        <li class="section_header_name"><%=sectionEntry.getKey().getTitle()%>
        </li>
        <li><a href="resume?uuid=${resume.uuid}&action=edit" class="view_edit">Edit</a></li>
    </ul>
    <section class="section_container">
        <p><%=sectionEntry.getValue().toString().trim() %>
        </p>
    </section>

</c:forEach>

<jsp:include page="footer.jsp"/>
</body>
</html>
