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
    <title>Error page</title>
</head>
<body>
Произошла ошибка! Обратить в тех. поддержку.
<c:if test="${error != null}">
    Возможно вас попросят указать содержание под спойлером.
    <details>
        <summary>Посмотреть информацию об ошибке</summary>
            ${error}
    </details>
</c:if>
</body>
</html>
