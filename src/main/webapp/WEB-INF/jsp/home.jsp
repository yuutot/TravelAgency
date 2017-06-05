<%--
  Created by IntelliJ IDEA.
  User: yuuto
  Date: 5/30/17
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" pageEncoding="utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="home.lan" var="l_language"/>
<html>
<head>
    <title>CNZ</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="../../font/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
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
                    <li><a href="<c:url value="/tours?command=tours&city=${city.getId()}&type=REST&min_cost=&max_cost="/>">— ${city.getName()}</a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-3 col-xs-6 col-md-preffix-1">
            <ul class="marked-list">

                <li><a href="<c:url value="/tours?command=tours&city=&type=REST&min_cost=&max_cost="/>">— Rest</a></li>
                <li><a href="<c:url value="/tours?command=tours&city=&type=EXCURSION&min_cost=&max_cost="/>">— Excursion</a></li>
                <li><a href="<c:url value="/tours?command=tours&city=&type=SHOPPING&min_cost=&max_cost="/>">— Shopping</a></li>
            </ul>
        </div>
    </div>

</section>

<section class="container well-sm">
    <h2>Лучшие предложения</h2>
    <div class="row">
        <div class="col-md-4 item__tour">
            <img src="img/1.jpg" alt="">
            <a href="#" class="tour__details">
                <p class="title">
                    4-Days Tour of Amsterdam and Zaanse Schans
                </p>
                <p></p>
                <p>
                    <i class="fa fa-map-marker" aria-hidden="true"></i> Amsterdam / The Netherlands
                </p>
                <p>
                    <i class="fa fa-clock-o" aria-hidden="true"></i> 03/12/12 - 04/22/22
                </p>
                <div class="btn-link">
                    1000 UAN
                </div>
            </a>
        </div>
        <div class="col-md-4 item__tour">
            <img src="img/1.jpg" alt="">
            <a href="#" class="tour__details">
                <p class="title">
                    4-Days Tour of Amsterdam and Zaanse Schans
                </p>
                <p></p>
                <p>
                    <i class="fa fa-map-marker" aria-hidden="true"></i> Amsterdam / The Netherlands
                </p>
                <p>
                    <i class="fa fa-clock-o" aria-hidden="true"></i> 03/12/12 - 04/22/22
                </p>
                <div class="btn-link">
                    1000 UAN
                </div>
            </a>
        </div>
        <div class="col-md-4 item__tour">
            <img src="img/1.jpg" alt="">
            <a href="#" class="tour__details">
                <p class="title">
                    4-Days Tour of Amsterdam and Zaanse Schans
                </p>
                <p></p>
                <p>
                    <i class="fa fa-map-marker" aria-hidden="true"></i> Amsterdam / The Netherlands
                </p>
                <p>
                    <i class="fa fa-clock-o" aria-hidden="true"></i> 03/12/12 - 04/22/22
                </p>
                <div class="btn-link">
                    1000 UAN
                </div>
            </a>
        </div>
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