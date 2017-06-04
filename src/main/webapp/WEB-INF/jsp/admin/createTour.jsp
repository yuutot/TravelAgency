<%--
  Created by IntelliJ IDEA.
  User: yuuto
  Date: 6/4/17
  Time: 12:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create tour</title>
</head>
<body>
<form action="/execute" method="post">
    <input type="hidden" name="command" value="create_tour">
    <select name="tour_type">
        <option value="REST">Rest</option>
        <option value="EXCURSION">Excursion</option>
        <option value="SHOPPING">Shopping</option>
    </select>
    <input type="datetime-local" name="date_from">
    <input type="datetime-local" name="date_to">
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
    <input type="text" name="photo">
    <input type="submit" value="Create">
</form>
</body>
</html>
