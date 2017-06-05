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
    <div class="top-panel">
        <h2>Все гостницы</h2>
    </div>


    <div class="container order-list hotel-list">
        <form class="add-city add-hotel" action="/execute" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="create_hotel">
            <input type="text" placeholder="Название гостиницы" name="name">
            <input type="text" placeholder="Звезды" name="star">
            <select name="city">
                <c:forEach var="city" items="${cities}">
                    <option value="${city.getId()}">${city.getName()}</option>
                </c:forEach>
            </select>
            <input style="width: 23%;" type="file" name="photo">
            <input type="submit" value="Добавить" class="btn-primary add-new">
        </form>
        <table>
            <tr>
                <td>
                    Название гостиницы
                </td>
                <td>
                    Кол-во звезд
                </td>

                <td>
                    Город
                </td>
            </tr>
            <c:forEach var="hotel" items="${hotels}">
            <tr>
                <td>${hotel.getName()}</td>
                <td>${hotel.getStar()}</td>
                <td>${hotel.getCity().getName()}</td>
            </tr>
            </c:forEach>
        </table>
    </div>
</main>
</body>
</html>
