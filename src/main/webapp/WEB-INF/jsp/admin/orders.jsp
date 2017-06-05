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
    <h2>Новые заказы</h2>
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
                <td>
                    Клиент
                </td>
                <td>
                    Скидка
                </td>
                <td>
                    Статус
                </td>
            </tr>
            <c:forEach var="order" items="${allOrders}">
                <tr>
                    <td>
                        <a href="<c:url value="/admin/order?id=${order.getTour().getId()}"/>">
                                ${order.getTour().getTitle()}
                        </a>

                    </td>
                    <td>
                            ${order.getTour().getHotel().getCity().getName()}
                    </td>
                    <td>
                            ${order.getTour().getDateFrom()} - ${order.getTour().getDateTo()}
                    </td>
                    <td>
                            ${order.getTour().getCost()} UAH
                    </td>
                    <td>
                            ${order.getUser().getDiscount()} %
                    </td>
                    <td>
                        <a href="<c:url value="/admin/user?id=${order.getUser().getId()}"/>">
                                ${order.getUser().getName()} ${order.getUser().getSurname()}
                        </a>
                    </td>
                    <td>
                            ${order.getOrderStatus()}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>
</body>
</html>
