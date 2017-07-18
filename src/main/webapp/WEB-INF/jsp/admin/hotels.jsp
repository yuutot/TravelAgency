<%--
  Created by IntelliJ IDEA.
  User: yuuto
  Date: 6/4/17
  Time: 12:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="h" uri="/tld/head-tag.tld" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="home.lan" var="l_language"/>
<fmt:message key="admin.hotels.title" var="l_hotels_title"/>
<fmt:message key="admin.hotels.name" var="l_hotels_name"/>
<fmt:message key="admin.hotels.star" var="l_hotels_star"/>
<fmt:message key="admin.hotels.city" var="l_hotels_city"/>
<fmt:message key="admin.city.form.submit" var="l_form_submit"/>

<html>
<head>
    <h:head title="${l_hotels_title}"/>
</head>
<body class="admin">
<%@include file="/WEB-INF/jspf/AdminHeader.jspf"%>
<main>
    <div class="top-panel">
        <h2>${l_hotels_title}</h2>
    </div>


    <div class="container order-list hotel-list">
        <form class="add-city add-hotel" action="/execute" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="create_hotel">
            <input type="text" placeholder="${l_hotels_name}" name="name">
            <input type="text" placeholder="${l_hotels_star}" name="star">
            <select name="city">
                <c:forEach var="city" items="${cities}">
                    <option value="${city.getId()}">${city.getName()}</option>
                </c:forEach>
            </select>
            <input style="width: 23%;" type="file" name="photo">
            <input type="submit" value="${l_form_submit}" class="btn-primary add-new">
        </form>
        <table>
            <tr>
                <td>
                    ${l_hotels_name}
                </td>
                <td>
                    ${l_hotels_star}
                </td>

                <td>
                    ${l_hotels_city}
                </td>
            </tr>
            <c:forEach var="hotel" items="${hotels}">
            <tr>
                <td>${hotel.getName()}</td>
                <td>${hotel.getStar()}</td>
                <td>${hotel.getCity().getName()}</td>
            </tr>
            </c:forEach>
        </table>
    </div>
</main>
</body>
</html>
