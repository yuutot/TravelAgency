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
<c:forEach var="order" items="${newOrders}">
    ${order}
    <br/>
</c:forEach>
<c:choose>
    <c:when test="${allOrders != null}">
        <details>
            <summary>Посмотреть информацию про все заказы</summary>
            <c:forEach var="order" items="${allOrders}">
                ${order}
                <br/>
            </c:forEach>
        </details>
    </c:when>
    <c:otherwise>
        <button type="button">
            <a href="/admin?all=true">Показать все заказы</a>
        </button>
    </c:otherwise>
</c:choose>
</body>
</html>
