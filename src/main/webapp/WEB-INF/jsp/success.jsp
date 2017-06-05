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
    <h:head title="Succes"/>
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
                Ваш заказ принят. Номер заказа: ${success}
            </p>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jspf/UserFooter.jspf"%>
</body>
</html>

