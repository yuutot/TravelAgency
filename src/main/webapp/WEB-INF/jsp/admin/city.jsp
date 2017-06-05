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
    <title>Create city</title>
</head>
<body>
<form action="/execute" method="post">
    <input type="hidden" name="command" value="create_city">
    <input type="text" name="name">
    <input type="submit" value="Create">
</form>
</body>
</html>
