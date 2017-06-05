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
<html>
<head>
    <h:head title="Page not found"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf"%>
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
