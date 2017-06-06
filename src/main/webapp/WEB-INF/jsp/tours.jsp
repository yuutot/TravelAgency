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
<fmt:message key="tours.title" var="l_tours_title"/>
<fmt:message key="tours.fIntro" var="l_tours_fIntro"/>
<fmt:message key="tours.sIntro" var="l_tours_sIntro"/>
<fmt:message key="tours.city" var="l_tours_city"/>
<fmt:message key="tours.type" var="l_tours_type"/>
<fmt:message key="tours.rest" var="l_tours_rest"/>
<fmt:message key="tours.excursion" var="l_tours_excursion"/>
<fmt:message key="tours.shopping" var="l_tours_shopping"/>
<fmt:message key="tours.from" var="l_tours_from"/>
<fmt:message key="tours.to" var="l_tours_to"/>
<fmt:message key="tours.search" var="l_tours_search"/>
<fmt:message key="tours.our" var="l_tours_our"/>
<fmt:message key="tours.discount" var="l_tours_discount"/>
<html>
<head>
    <h:head title="${l_tours_title}"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf"%>
<section class="top-banner">
    <div class="insurance-header-bg">
        <img src="img/2.jpg" alt="">
        <div class="flex-cont">
            <div class="block-title-cell">
                <h1>${l_tours_fIntro}</h1>
                <h2>${l_tours_sIntro}</h2>
            </div>
        </div>
    </div>
</section>
<section class="container">
    <c:if test="${error != null}">
        ${error}
    </c:if>
    <form class="hr-form" action="/tours" method="get">
        <input type="hidden" name="command" value="tours">
        <div class="block-input">
            <p>${l_tours_city}: </p>
            <select name="city">
                <c:forEach var="city" items="${cities}">
                    <option value="${city.getId()}"> ${city.getName()}</option>
                </c:forEach>
            </select>
        </div>
        <div class="block-input">
            <p>${l_tours_type}: </p>
            <select name="type">
                <option value="REST">${l_tours_rest}</option>
                <option value="EXCURSION">${l_tours_excursion}</option>
                <option value="SHOPPING">${l_tours_shopping}</option>
            </select>
        </div>

        <div class="block-input">
            <p>${l_tours_from}: </p>
            <input type="number" placeholder="0" name="min_cost">
        </div>
        <div class="block-input">
            <p>${l_tours_to}: </p>
            <input type="number" placeholder="1000" name="max_cost">
        </div>
        <input class="btn btn-sm btn-primary" type="submit" value="${l_tours_search}">
    </form>
</section>
<section class="container well-sm">
    <h2>${l_tours_our}</h2>
    <div class="row">
        <c:forEach var="tour" items="${tours}">
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${tour.getDateFrom()}" var="dateTo" />
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${tour.getDateTo()}" var="dateFrom" />
            <fmt:formatDate value="${dateTo}" var="pDateTo" pattern="dd.MM.yy HH:mm"/>
            <fmt:formatDate value="${dateFrom}" var="pDateFrom" pattern="dd.MM.yy HH:mm"/>
            <div class="col-md-4 item__tour">
                <img src="/photo/${tour.getPhoto()}" alt="">
                <a href="/tours?id=${tour.getId()}" class="tour__details">
                    <p class="title">
                            ${tour.getTitle()}
                    </p>
                    <p></p>
                    <p>
                        <i class="fa fa-map-marker" aria-hidden="true"></i> ${tour.getHotel().getCity().getName()}
                    </p>
                    <p>
                        <i class="fa fa-clock-o" aria-hidden="true"></i> ${pDateFrom} - ${pDateTo}
                    </p>
                    <div class="btn-link">
                        <c:choose>
                            <c:when test="${user != null && user.getDiscount() > 0}">
                                ${tour.getCost()*(1.0-user.getDiscount()/100.0)} UAH
                            </c:when>
                            <c:otherwise>
                                ${tour.getCost()} UAH
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${user != null}">
                        <p>
                            <i class="fa fa-gift" aria-hidden="true"></i> ${l_tours_discount} ${user.getDiscount()} %
                        </p>
                    </c:if>
                </a>
            </div>
        </c:forEach>
    </div>

</section>
<%@include file="/WEB-INF/jspf/UserFooter.jspf"%>
</body>
</html>