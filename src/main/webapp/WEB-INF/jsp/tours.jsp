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
                        <i class="fa fa-clock-o" aria-hidden="true"></i> ${tour.getDateFrom()} - ${tour.getDateTo()}
                    </p>
                    <div class="btn-link">
                            ${tour.getCost()} UAH
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