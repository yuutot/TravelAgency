<%--
  Created by IntelliJ IDEA.
  User: yuuto
  Date: 5/30/17
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="home.login" var="l_login"/>
<fmt:message key="home.password" var="l_password"/>
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
<section class="top-banner">
    <div class="insurance-header-bg">
        <img src="/img/2.jpg" alt="">
        <div class="flex-cont">
            <div class="block-title-cell">
                <h1>Регистрация</h1>
            </div>
        </div>
    </div>
</section>
<section class="container content-tour">
    <c:if test="${error != null}">
        ${error.getMessage()}
    </c:if>
    <form class="auth" action="/execute" method="post">
        <input type="hidden" name="command" value="register">
        <label for="name">Имя</label>
        <input id="name" type="text" placeholder="Имя" name="name">
        <label for="surname">Фамилия</label>
        <input id="surname" type="text" placeholder="Фамилия" name="surname">
        <label for="login">Логин</label>
        <input id="login" type="text" placeholder="Логин" name="login">
        <label for="pass">Пароль</label>
        <input id="pass" type="password" placeholder="Пароль" name="password">
        <label for="tel">Телефон</label>
        <input id="tel" type="tel" placeholder="Телефон" name="phone">
        <input class="btn btn-sm btn-primary" type="submit" value="Регистрация">
    </form>
</section>
<footer class="container">
    <div class="col-md-4">
        <div class="logo">
            <a href="/">_Travel<span>Agency</span></a>
        </div>
        <div class="copyright">
            © <span id="copyright-year">2017</span> |
            All rights reserved

        </div>
    </div>
</footer>
</body>
</html>