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
<fmt:message key="home.password" var="l_password"/>
<fmt:message key="home.lan" var="l_language"/>
<fmt:message key="login.no" var="l_login_no"/>
<fmt:message key="login.title" var="l_login_title"/>

<html>
<head>
    <h:head title="${l_login_title}"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf"%>
<section class="top-banner">
    <div class="insurance-header-bg">
        <img src="img/2.jpg" alt="">
        <div class="flex-cont">
            <div class="block-title-cell">
                <h1>${l_header_login}</h1>
            </div>
        </div>
    </div>
</section>
<section style="" class="container  content-tour">
    <form class="auth" action="/execute" method="post">
        <input type="hidden" name="command" value="login">
        <label for="login">${l_login}</label>
        <input id="login" type="text" placeholder="${l_login}" name="login">
        <label for="passw">${l_password}</label>
        <input id="passw" type="password" placeholder="${l_password}" name="password">
        <input class="btn btn-sm btn-primary" type="submit" value="${l_header_login}">

    </form>
    <a class="link-to-reg" href="/register">${l_login_no}</a>
</section>
<%@include file="/WEB-INF/jspf/UserFooter.jspf"%>
</body>
</html>