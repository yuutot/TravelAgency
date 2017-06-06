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

<fmt:message key="home.lan" var="l_language"/>
<fmt:message key="home.login" var="l_login"/>
<fmt:message key="home.password" var="l_password"/>
<fmt:message key="register.name" var="l_register_name"/>
<fmt:message key="register.phone" var="l_register_phone"/>
<fmt:message key="register.submit" var="l_register_submit"/>
<fmt:message key="register.surname" var="l_register_surname"/>
<fmt:message key="register.title" var="l_resiter_title"/>
<html>
<head>
    <h:head title="${l_resiter_title}"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf"%>
<section class="top-banner">
    <div class="insurance-header-bg">
        <img src="/img/2.jpg" alt="">
        <div class="flex-cont">
            <div class="block-title-cell">
                <h1>${l_resiter_title}</h1>
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
        <label for="name">${l_register_name}</label>
        <input id="name" type="text" placeholder="${l_register_name}" name="name">
        <label for="surname">${l_register_surname}</label>
        <input id="surname" type="text" placeholder="${l_register_surname}" name="surname">
        <label for="login">${l_login}</label>
        <input id="login" type="text" placeholder="${l_login}" name="login">
        <label for="pass">${l_password}</label>
        <input id="pass" type="password" placeholder="${l_password}" name="password">
        <label for="tel">${l_register_phone}</label>
        <input id="tel" type="tel" placeholder="${l_register_phone}" name="phone">
        <input class="btn btn-sm btn-primary" type="submit" value="${l_register_submit}">
    </form>
</section>
<%@include file="/WEB-INF/jspf/UserFooter.jspf"%>
</body>
</html>