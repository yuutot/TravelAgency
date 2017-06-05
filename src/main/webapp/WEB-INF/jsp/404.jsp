<%--
  Created by IntelliJ IDEA.
  User: yuuto
  Date: 5/30/17
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="home.lan" var="l_language"/>
<html>
<head>
    <title>CNZ</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="../../font/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
<header class="container header-page">
    <div class="logo">
        <a href="/">_Travel<span>Agency</span></a>
    </div>
    <nav class="client-menu">
        <ul>
            <li><a href="/tours">Туры</a></li>
            <c:choose>
                <c:when test="${user != null}">
                    <li><a href="/order">Заказы</a></li>
                    <li><a href="/login?logout">Выход</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/login">Авторизация</a></li>
                </c:otherwise>
            </c:choose>
            <li><a href="?lan=${l_language}">${l_language}</a></li>
        </ul>
    </nav>
</header>
<main class="page-404">
    <div class="flex-cont">
        <div class="block-title-cell">
            <img src="img/404.png" alt="">
            <h5>${error}</h5>
        </div>
    </div>
</main>
</body>
</html>
