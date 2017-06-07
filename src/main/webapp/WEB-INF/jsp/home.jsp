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
<fmt:message key="home.intro" var="l_home_intro"/>
<fmt:message key="home.title" var="l_home_title"/>
<fmt:message key="home.searchTour" var="l_home_searchTour"/>
<fmt:message key="home.city" var="l_home_city"/>
<fmt:message key="home.type" var="l_home_type"/>
<fmt:message key="home.rest" var="l_home_rest"/>
<fmt:message key="home.excursion" var="l_home_excursion"/>
<fmt:message key="home.shopping" var="l_home_shopping"/>
<fmt:message key="home.min" var="l_home_min"/>
<fmt:message key="home.max" var="l_home_max"/>
<fmt:message key="home.search" var="l_home_search"/>
<fmt:message key="home.welcome" var="l_home_welcome"/>
<fmt:message key="home.fIntro" var="l_home_fIntro"/>
<fmt:message key="home.best" var="l_home_best"/>
<fmt:message key="home.discount" var="l_home_discount"/>
<html>
<head>
    <h:head title="${l_home_title}"/>
</head>
<%@include file="/WEB-INF/jspf/UserHeader.jspf" %>
<body class="main__body">
<section class="top-screen">
    <div class="container">
        <form action="/tours" method="get" class="tr-form">
            <fieldset>
                <h4 class="text-bold"> ${l_home_searchTour}
                    <p>${l_home_city}</p>
                </h4>
                <input type="hidden" name="command" value="tours">
                <select name="city">
                    <c:forEach var="city" items="${cities}">
                        <option value="${city.getId()}"> ${city.getName()}</option>
                    </c:forEach>
                </select>
                <p>${l_home_type}</p>
                <select name="type">
                    <option value="REST">${l_home_rest}</option>
                    <option value="EXCURSION">${l_home_excursion}</option>
                    <option value="SHOPPING">${l_home_shopping}</option>
                </select>
                <p>${l_home_min}</p>
                <input type="number" name="min_cost">
                <p>${l_home_max}</p>
                <input type="number" name="max_cost">
                <input class="btn btn-sm btn-primary" type="submit" value="${l_home_search}">
            </fieldset>
        </form>
    </div>
</section>
<section class="container well-md">
    <div class="row">
        <div class="col-md-4">
            <h2>${l_home_welcome}</h2>
            <h4>
                ${l_home_fIntro}
            </h4>
            <p>
                ${l_home_intro}
            </p>
        </div>
        <div class="col-md-3 col-xs-6 col-md-preffix-1">
            <ul class="marked-list">
                <c:forEach var="city" items="${cities}">
                    <li>
                        <a href="<c:url value="/tours?command=tours&city=${city.getId()}&type=REST&min_cost=&max_cost="/>">— ${city.getName()}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-3 col-xs-6 col-md-preffix-1">
            <ul class="marked-list">

                <li><a href="<c:url value="/tours?command=tours&city=&type=REST&min_cost=&max_cost="/>">— ${l_home_rest}</a></li>
                <li><a href="<c:url value="/tours?command=tours&city=&type=EXCURSION&min_cost=&max_cost="/>">—
                    ${l_home_excursion}</a></li>
                <li><a href="<c:url value="/tours?command=tours&city=&type=SHOPPING&min_cost=&max_cost="/>">—
                    ${l_home_shopping}</a></li>
            </ul>
        </div>
    </div>

</section>

<section class="container well-sm">
    <h2>${l_home_best}</h2>
    <div class="row">
        <c:forEach var="tour" items="${tours}">
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${tour.getDateFrom()}" var="dateFrom"/>
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${tour.getDateTo()}" var="dateTo"/>
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
                            <i class="fa fa-gift" aria-hidden="true"></i> ${l_home_discount} ${user.getDiscount()} %
                        </p>
                    </c:if>
                </a>
            </div>
        </c:forEach>
    </div>
</section>
<%@include file="/WEB-INF/jspf/UserFooter.jspf" %>
</body>
</html>