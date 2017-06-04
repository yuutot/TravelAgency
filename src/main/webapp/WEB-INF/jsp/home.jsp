<%--
  Created by IntelliJ IDEA.
  User: yuuto
  Date: 5/30/17
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" pageEncoding="utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>

<fmt:message key = "home.login" var =  "l_login"/>
<fmt:message key = "home.password" var =  "l_password"/>
<html>
<head>
    <title>Home page</title>
</head>
<body>
${l_login}
${l_password}
<c:choose>
    <c:when test="${user != null}">
        Добро пожаловать, ${user.getName()}!
        <button type="button">
            <a href="/login?logout">Выход</a>
        </button>
        <a href="/order">Заказы</a>
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
<a href="/tours">Туры</a>
<form action="/tours" method="get">
    <input type="hidden" name="command" value="tours">
    <c:forEach var="city" items="${cities}">
        <input type="radio" name="city" value="${city.getId()}"> ${city.getName()}
        <br/>
    </c:forEach>
    <select name="type">
        <option value="REST">Rest</option>
        <option value="EXCURSION">Excursion</option>
        <option value="SHOPPING">Shopping</option>
    </select>
    <input type="number" name="min_cost">
    <input type="number" name="max_cost">
    <input type="submit" value="Submit">
</form>
</body>
</html>
