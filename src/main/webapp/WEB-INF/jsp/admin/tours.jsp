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
<%@ taglib prefix="h" uri="/tld/head-tag.tld" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="locale"/>

<fmt:message key="home.lan" var="l_language"/>
<html>
<head>
    <h:head title="Tours"/>
</head>
<body class="admin">
<%@include file="/WEB-INF/jspf/AdminHeader.jspf"%>
<main>
    <div class="top-panel">
        <h2>Все туры</h2>
        <a class="btn-primary add-new" href="<c:url value="/admin/createTour"/>"> Добавить новый тур</a>
    </div>
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
            </tr>
            <c:forEach var="tour" items="${tours}">
                <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${tour.getDateFrom()}" var="dateTo" />
                <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${tour.getDateTo()}" var="dateFrom" />
                <fmt:formatDate value="${dateTo}" var="pDateTo" pattern="dd.MM.yy HH:mm"/>
                <fmt:formatDate value="${dateFrom}" var="pDateFrom" pattern="dd.MM.yy HH:mm"/>
                <tr>
                    <td>
                        <a href="<c:url value="/admin/tour?id=${tour.getId()}"/>">
                                ${tour.getTitle()}
                        </a>

                    </td>
                    <td>
                            ${tour.getHotel().getCity().getName()}
                    </td>
                    <td>
                            ${pDateFrom} - ${pDateTo}
                    </td>
                    <td>
                            ${tour.getCost()} UAH
                    </td>
                </tr>
            </c:forEach>

        </table>
    </div>
</main>
</body>
</html>