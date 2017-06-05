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
    <div class="top-panel">
        <h2>Все туры</h2>
        <a class="btn-primary add-new" href="<c:url value="/admin/createTour"/>"> Добавить новый тур</a>
    </div>
    <div class="container order-list">
        <table>
            <tr>
                <td>
                    Название тура
                </td>
                <td>
                    Город
                </td>
                <td>
                    Дата
                </td>
                <td>
                    Стоимость
                </td>
            </tr>
            <c:forEach var="tour" items="${tours}">
                <tr>
                    <td>
                        <a href="<c:url value="/admin/tour?id=${tour.getId()}"/>">
                                ${tour.getTitle()}
                        </a>

                    </td>
                    <td>
                            ${tour.getHotel().getCity().getName()}
                    </td>
                    <td>
                            ${tour.getDateFrom()} - ${tour.getDateTo()}
                    </td>
                    <td>
                            ${tour.getCost()} UAH
                    </td>
                </tr>
            </c:forEach>

        </table>
    </div>
</main>
</body>
</html>