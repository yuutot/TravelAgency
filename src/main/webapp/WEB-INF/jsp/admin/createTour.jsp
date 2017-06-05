<%--
  Created by IntelliJ IDEA.
  User: yuuto
  Date: 6/4/17
  Time: 12:13 PM
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
        <form class="row" action="/execute" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="create_tour">
            <div class="col-md-10 order-details add-new-item" style="margin-top: 0;">
                <h2>Добавить тур</h2>
                <input style="margin-top: 20px;" type="text" placeholder="Название тура" name="title">
                <p>
                    Цена(грн)
                </p>
                <input type="number" name="cost">
                <div class="date" style="width: 49%; margin-right: 2%;">
                    <p>
                        Дата начала
                    </p>
                    <input type="datetime-local" name="date_from">

                </div>
                <div class="date" style="width: 49%;">
                    <p>
                        Дата окончания
                    </p>
                    <input type="datetime-local" name="date_to">
                </div>

                <p>
                    Тип тура
                </p>
                <select name="tour_type">
                    <option value="REST">Rest</option>
                    <option value="EXCURSION">Excursion</option>
                    <option value="SHOPPING">Shopping</option>
                </select>
                <p>
                    Трансфер
                </p>
                <select name="transport_type">
                    <option value="BUS">Bus</option>
                    <option value="PLANE">Plane</option>
                    <option value="TRAIN">Train</option>
                    <option value="SHIP">Ship</option>
                </select>
                <p>
                    Гостиница
                </p>
                <select name="hotel">
                    <c:forEach var="hotel" items="${hotels}">
                        <option value="${hotel.getId()}">${hotel.getName()}</option>
                    </c:forEach>
                </select>
                <p>
                    Описание тура
                </p>
                <input type="text" name="description">
                <p style="width: 100%">
                    Фото тура
                </p>
                <input style="width: 100%;" type="file" name="photo">
                <div class="check">
                    <input style="width: auto" type="checkbox" name="is_hot" value="true">   <p>Горячее предложение</p>
                </div>
                <hr>
                <input style="width: 20%;" class="btn-primary btn-new" type="submit" value="Сохранить">
            </div>
        </form>
    </div>
</main>
</body>
</html>