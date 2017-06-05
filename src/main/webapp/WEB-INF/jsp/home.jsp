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
    <h:head title="Main page"/>
</head>
<%@include file="/WEB-INF/jspf/UserHeader.jspf" %>
<body class="main__body">
<section class="top-screen">
    <div class="container">
        <form action="/tours" method="get" class="tr-form">
            <fieldset>
                <h4 class="text-bold"> Найти путевку
                    <p>По пункту прибытия</p>
                </h4>
                <input type="hidden" name="command" value="tours">
                <select name="city">
                    <c:forEach var="city" items="${cities}">
                        <option value="${city.getId()}"> ${city.getName()}</option>
                    </c:forEach>
                </select>
                <p>По типу</p>
                <select name="type">
                    <option value="REST">Rest</option>
                    <option value="EXCURSION">Excursion</option>
                    <option value="SHOPPING">Shopping</option>
                </select>
                <p>Минимальная цена</p>
                <input type="number" name="min_cost">
                <p>Максимальная цена</p>
                <input type="number" name="max_cost">
                <input class="btn btn-sm btn-primary" type="submit" value="Найти">
            </fieldset>
        </form>
    </div>
</section>
<section class="container well-md">
    <div class="row">
        <div class="col-md-4">
            <h2>Welcome</h2>
            <h4>
                Arrange an unforgettable
                adventure for your family.
            </h4>
            <p>

                There is nothing better than spending your free time with family or friends while traveling. Book one of
                our tours and save more than $200 on each member. Our turnkey travel solutions include full insurance,
                guided and escorted tour vacations, accommodation in best hotels and custom features that you can choose
                yourself.
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

                <li><a href="<c:url value="/tours?command=tours&city=&type=REST&min_cost=&max_cost="/>">— Rest</a></li>
                <li><a href="<c:url value="/tours?command=tours&city=&type=EXCURSION&min_cost=&max_cost="/>">—
                    Excursion</a></li>
                <li><a href="<c:url value="/tours?command=tours&city=&type=SHOPPING&min_cost=&max_cost="/>">—
                    Shopping</a></li>
            </ul>
        </div>
    </div>

</section>

<section class="container well-sm">
    <h2>Лучшие предложения</h2>
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
<%@include file="/WEB-INF/jspf/UserFooter.jspf" %>
</body>
</html>