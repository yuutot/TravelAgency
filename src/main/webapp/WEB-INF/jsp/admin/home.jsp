<%--
  Created by IntelliJ IDEA.
  User: yuuto
  Date: 5/30/17
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home page</title>
</head>
<body>
Добро пожаловать в админку, ${user.getName()}!
<br/>
<c:choose>
    <c:when test="${allOrders != null}">
        <c:forEach var="order" items="${allOrders}">
            ${order}
            <form action="/execute?id=${order.getId()}" method="post">
                <input type="hidden" name="command" value="edit_order_status">
                <select name="status">
                    <option value="NEW">New</option>
                    <option value="PAID">Paid</option>
                    <option value="CANCELED">Cancel</option>
                </select>
                <input type="submit" value="Submit">
            </form>
            <br/>
        </c:forEach>
    </c:when>
    <c:when test="${newOrders != null}">
        <c:forEach var="order" items="${newOrders}">
            ${order}
            <form action="/execute?id=${order.getId()}" method="post">
                <input type="hidden" name="command" value="edit_order_status">
                <select name="status">
                    <option value="NEW">New</option>
                    <option value="PAID">Paid</option>
                    <option value="CANCELED">Cancel</option>
                </select>
                <input type="submit" value="Submit">
            </form>
            <br/>
        </c:forEach>
        <button type="button">
            <a href="/admin?all=true">Показать все заказы</a>
        </button>
    </c:when>
</c:choose>

<form action="/execute" method="post">
    <input type="hidden" name="command" value="create_city">
    <input type="text" name="name">
    <input type="submit" value="Create">
</form>

<form action="/execute" method="post">
    <input type="hidden" name="command" value="create_hotel">
    <select name="city">
        <c:forEach var="city" items="${cities}">
            <option value="${city.getId()}">${city.getName()}</option>
        </c:forEach>
    </select>
    <input type="text" name="name">
    <input type="number" name="star">
    <input type="text" name="photo">
    <input type="submit" value="Create">
</form>

<form action="/execute" method="post">
    <input type="hidden" name="command" value="create_tour">
    <select name="tour_type">
        <option value="REST">Rest</option>
        <option value="EXCURSION">Excursion</option>
        <option value="SHOPPING">Shopping</option>
    </select>
    <input type="datetime" name="date_to">
    <input type="datetime" name="date_from">
    <input type="number" name="cost">
    <input type="text" name="description">
    <select name="transport_type">
        <option value="BUS">Bus</option>
        <option value="PLANE">Plane</option>
        <option value="TRAIN">Train</option>
        <option value="SHIP">Ship</option>
    </select>
    <select name="hotel">
        <c:forEach var="hotel" items="${hotels}">
            <option value="${hotel.getId()}">${hotel.getName()}</option>
        </c:forEach>
    </select>
    <input type="checkbox" name="is_hot" value="true">
    <input type="submit" value="Create">
</form>

</body>
</html>
