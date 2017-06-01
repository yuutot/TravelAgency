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
    <title>Login page</title>
</head>
<body>
    <form method="post" action="/execute">
        <input type="hidden" name="command" value="login">
        <input type="text" name="login">
        <input type="password" name="password">
        <input type="submit" value="Submit">
    </form>
    <c:if test="${error != null}">
        ${error.getMessage()}
        <c:remove var="error" scope="session"/>
    </c:if>
</body>
</html>