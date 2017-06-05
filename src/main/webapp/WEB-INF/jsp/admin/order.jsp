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

<fmt:message key="home.lan" var="l_language"/>
<html>
<head>
    <title>CNZ</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/font/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body class="admin">
<header>
    <div class="logo">
        <a href="/">_Travel<span>Agency</span></a>
    </div>
    <a href="/login?logout">Выйти</a>
</header>
<aside>
    <ul>
        <li><a href="<c:url value="/admin"/>"> Новые заказы</a></li>
        <li><a href="<c:url value="/admin?all=true"/>">Все заказы</a></li>
        <li><a href="<c:url value="/admin/tours"/>">Туры</a></li>
        <li><a href="<c:url value="/admin/city"/>">Города</a></li>
        <li><a href="<c:url value="/admin/hotels"/>">Гостиницы</a></li>
    </ul>
</aside>
<main>
    <div class="container">
        <h2>Заказ № ${order.getId()}</h2>
        <div class="row">
            <div class="col-md-6 order-details">
                <h2> ${order.getTour().getTitle()} </h2>
                <p>
                    <i class="fa fa-clock-o" aria-hidden="true"></i> ${order.getTour().getDateFrom()} - ${order.getTour().getDateTo()}
                </p>
                <p>
                    <i class="fa fa-map-marker" aria-hidden="true"></i> ${order.getTour().getHotel().getCity().getName()}
                </p>

                <p class="price-page-p"> <i class="fa fa-usd" aria-hidden="true"></i> ${order.getTour().getCost()} UAH</p>

                <h2> Статус заказа </h2>
                <form action="/execute?id=${order.getId()}" method="post">
                    <input type="hidden" name="command" value="edit_order_status">
                    <select name="status">
                        <option value="NEW">New</option>
                        <option value="PAID">Paid</option>
                        <option value="CANCELED">Cancel</option>
                    </select>
                    <input type="submit" value="Изменить" class="btn btn-sm btn-primary btn-new">
                </form>
            </div>
            <div class="col-md-6 order-details">
                <h2> О клиенте </h2>
                <p>
                    <i class="fa fa-user-circle" aria-hidden="true"></i> ${order.getUser().getName()} ${order.getUser().getSurname()}
                </p>
                <p>
                    <i class="fa fa-gift" aria-hidden="true"></i> Скидка ${order.getUser().getDiscount()} %
                </p>
                <p>
                    <i class="fa fa-envelope-o" aria-hidden="true"></i> ${order.getUser().getPhone()}
                </p>
                <a class="btn btn-sm btn-primary btn-new" href="<c:url value="/admin/user?id=${order.getUser().getId()}"/>">Подробнее</a>

            </div>
        </div>
    </div>
</main>
</body>
</html>