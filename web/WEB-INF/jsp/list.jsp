<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список резюме</title>
</head>
<body>
<header>
    <a href="resume">Управление резюме</a>
</header>
<section>
    <table>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="resume" items="${resumes}">
            <jsp:useBean id="resume" class="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>${resume.getContactType(ContactType.MAIL)}</td>

                <td><a href="resume?uuid=${resume.uuid}&action=edit" class="btn">Edit</a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete" class="btn_cancel">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="footer.jsp"/>
</body>
</html>
