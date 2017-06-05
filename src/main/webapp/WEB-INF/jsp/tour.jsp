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
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="home.login" var="l_login"/>
<fmt:message key="home.password" var="l_password"/>
<fmt:message key="home.lan" var="l_language"/>
<html>
<head>
    <title>CNZ</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="../../font/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
<header class="container header-page">
    <div class="logo">
        <a href="/">_Travel<span>Agency</span></a>
    </div>
    <nav class="client-menu">
        <ul>
            <li><a href="/tours">Туры</a></li>
            <c:choose>
                <c:when test="${user != null}">
                    <li><a href="/order">Заказы</a></li>
                    <li><a href="/login?logout">Выход</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/login">Авторизация</a></li>
                </c:otherwise>
            </c:choose>
            <li><a href="?lan=${l_language}">${l_language}</a></li>
        </ul>
    </nav>
</header>
<section class="top-banner">
    <div class="insurance-header-bg">
        <img src="img/2.jpg" alt="">
        <div class="large-bg"></div>
        <div class="flex-cont">
            <div class="block-title-cell">
                <h1>${tour.getTitle()}</h1>
            </div>
        </div>
    </div>
</section>
<div class="container content-tour">
    <div class="row">
        <div class="col-md-6 tour_options">
            <p class="title-page">
                ${tour.getHotel().getName()}
            </p>
            <div class="foto">
                <img src="<c:url value="/photo/${tour.getPhoto()}"/>" alt="">
            </div>
        </div>
        <div class="col-md-6 tour_options">
            <div class="about-tour">
                <p class="descr-page">
                    ${tour.getDateFrom()} - ${tour.getDateTo()}
                </p>
                <div class="line-page"></div>
                <p><i class="fa fa-map-marker" aria-hidden="true"></i> ${tour.getHotel().getCity().getName()}</p>
                <c:choose>
                    <c:when test="${tour.getTransportType().toString().equals('BUS')}">
                        <p><i class="fa fa-bus" aria-hidden="true"></i> Автобус</p>
                    </c:when>
                    <c:when test="${tour.getTransportType().toString().equals('TRAIN')}">
                        <p><i class="fa fa-train" aria-hidden="true"></i> Поезд</p>
                    </c:when>
                    <c:when test="${tour.getTransportType().toString().equals('PLANE')}">
                        <p><i class="fa fa-plane" aria-hidden="true"></i> Авиаперелет</p>
                    </c:when>
                    <c:when test="${tour.getTransportType().toString().equals('SHIP')}">
                        <p><i class="fa fa-ship" aria-hidden="true"></i> Корабль</p>
                    </c:when>
                </c:choose>
                <c:if test="${user != null}">
                    <p>
                        <i class="fa fa-gift" aria-hidden="true"></i> Скидка ${user.getDiscount()} %
                    </p>
                </c:if>
                <p><i class="fa fa-bed" aria-hidden="true"></i> Проживание в отеле ${tour.getHotel().getStar()}*</p>
                <div style="margin-bottom: 15px;" class="line-page"></div>
                <span>
                    ${tour.getDescription()}
                </span>
            </div>
            <div class="price-page">
                <div class="price-page-l">
                    <p>
                        Цена за тур:
                    </p>
                    <p class="price-page-p">${tour.getCost()} UAH</p>
                </div>
                <c:if test="${user != null}">
                    <a href="/order?id=${tour.getId()}" class="btn btn-sm btn-primary">
                        Забронировать
                    </a>
                </c:if>
            </div>

        </div>
    </div>

</div>
<footer class="container">
    <div class="col-md-4">
        <div class="logo">
            <a href="/">_Travel<span>Agency</span></a>
        </div>
        <div class="copyright">
            © <span id="copyright-year">2017</span> |
            All rights reserved

        </div>
    </div>
</footer>
</body>
</html>

