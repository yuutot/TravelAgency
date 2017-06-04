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

</body>
</html>
