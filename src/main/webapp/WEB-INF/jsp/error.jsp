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
<fmt:message key="error.title" var="l_error_title"/>
<fmt:message key="error.oops" var="l_error_oops"/>
<fmt:message key="error.description" var="l_error_desc"/>
<fmt:message key="error.contact" var="l_error_contact"/>
<html>
<head>
    <h:head title="${l_error_title}"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf" %>
<main class="page-404">
    <div class="flex-cont">
        <div class="block-title-cell">
            <h1 style="text-align: center;">${l_error_oops}</h1>
            <h5>${l_error_contact}</h5>
            <c:if test="${error != null}">
                <h6 onclick="showSpoiler()">${l_error_desc}</h6>
                <p id="problem">${error}</p>
            </c:if>
        </div>
    </div>

</main>
<script>
    function showSpoiler() {
        var text = document.getElementById("problem");
        text.style.display = 'block'
    }
</script>
</body>
</html>