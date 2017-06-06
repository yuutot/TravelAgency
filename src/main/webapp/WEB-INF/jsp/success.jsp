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
<fmt:message key="success.mes" var="l_success_mes"/>
<fmt:message key="success.title" var="l_success_title"/>
<html>
<head>
    <h:head title="${l_success_title}"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf"%>
<section class="top-banner">
    <div class="insurance-header-bg">
        <img src="img/2.jpg" alt="">
        <div class="large-bg"></div>
    </div>
</section>
<div class="container content-tour">
    <div class="row">
        <div class="col-md-12 tour_options">
            <p class="title-page">
                ${l_success_mes} ${success}
            </p>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jspf/UserFooter.jspf"%>
</body>
</html>

