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
    <h:head title="Tours"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf"%>
<section class="top-banner">
    <div class="insurance-header-bg">
        <img src="img/2.jpg" alt="">
        <div class="flex-cont">
            <div class="block-title-cell">
                <h1>Лучший отдых</h1>
                <h2>Путешевствия украшают</h2>
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
            <p>Город: </p>
            <select name="city">
                <c:forEach var="city" items="${cities}">
                    <option value="${city.getId()}"> ${city.getName()}</option>
                </c:forEach>
            </select>
        </div>
        <div class="block-input">
            <p>Тип отдыха: </p>
            <select name="type">
                <option value="REST">Rest</option>
                <option value="EXCURSION">Excursion</option>
                <option value="SHOPPING">Shopping</option>
            </select>
        </div>

        <div class="block-input">
            <p>От: </p>
            <input type="number" placeholder="0" name="min_cost">
        </div>
        <div class="block-input">
            <p>До: </p>
            <input type="number" placeholder="1000" name="max_cost">
        </div>
        <input class="btn btn-sm btn-primary" type="submit" value="Найти">
    </form>
</section>
<section class="container well-sm">
    <h2>Наши туры</h2>
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
                            <i class="fa fa-gift" aria-hidden="true"></i> Скидка ${user.getDiscount()} %
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