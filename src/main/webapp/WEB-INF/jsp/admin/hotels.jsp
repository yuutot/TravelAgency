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
    <title>Create hotel</title>
</head>
<body>
<form action="/execute" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="create_hotel">
    <select name="city">
        <c:forEach var="city" items="${cities}">
            <option value="${city.getId()}">${city.getName()}</option>
        </c:forEach>
    </select>
    <input type="text" name="name">
    <input type="number" name="star">
    <input type="file" name="photo">
    <input type="submit" value="Create">
</form>
</body>
</html>
