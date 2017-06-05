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
<%@ taglib prefix="h" uri="/tld/head-tag.tld" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="home.login" var="l_login"/>
<html>
<head>
    <h:head title="Register page"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf"%>
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
<%@include file="/WEB-INF/jspf/UserFooter.jspf"%>
</body>
</html>