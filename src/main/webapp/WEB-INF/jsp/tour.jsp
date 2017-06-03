<%--
  Created by IntelliJ IDEA.
  User: yuuto
  Date: 5/30/17
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home page</title>
</head>
<body>
<c:choose>
    <c:when test="${user != null}">
        Добро пожаловать, ${user.getName()}!
        <button type="button">
            <a href="/login?logout">Выход</a>
        </button>
    </c:when>
    <c:otherwise>
        <button type="button">
            <a href="/login">Авторизация</a>
        </button>
        <button type="button">
            <a href="/register">Регистрация</a>
        </button>
    </c:otherwise>
</c:choose>
<br/>
${tour}
<c:if test="${user != null}">
    <button type="button">
        <a href="/order?id=${tour.getId()}">Заказ</a>
    </button>
    <c:if test="${user.getUserType().getType().equals(\"admin\")}">
        <button type="button">
            <a href="/admin/editTour?id=${tour.getId()}&type=delete">Delete</a>
        </button>
        <button type="button">
            <a href="/admin/editTour?id=${tour.getId()}&type=hot">Change hot status</a>
        </button>
    </c:if>
</c:if>
</body>
</html>
