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
    <h:head title="Orders"/>
</head>
<body class="admin">
<%@include file="/WEB-INF/jspf/AdminHeader.jspf"%>
<main>
    <h2>Новые заказы</h2>
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
                <td>
                    Клиент
                </td>
                <td>
                    Скидка
                </td>
                <td>
                    Статус
                </td>
            </tr>
            <c:forEach var="order" items="${allOrders}">
                <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.getTour().getDateFrom()}" var="dateTo" />
                <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.getTour().getDateTo()}" var="dateFrom" />
                <fmt:formatDate value="${dateTo}" var="pDateTo" pattern="dd.MM.yy HH:mm"/>
                <fmt:formatDate value="${dateFrom}" var="pDateFrom" pattern="dd.MM.yy HH:mm"/>
                <tr>
                    <td>
                        <a href="<c:url value="/admin/order?id=${order.getTour().getId()}"/>">
                                ${order.getTour().getTitle()}
                        </a>

                    </td>
                    <td>
                            ${order.getTour().getHotel().getCity().getName()}
                    </td>
                    <td>
                            ${pDateFrom} - ${pDateTo}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${order.getUser().getDiscount() > 0}">
                                ${order.getTour().getCost()*(1.0-order.getUser().getDiscount()/100.0)} UAH
                            </c:when>
                            <c:otherwise>
                                ${order.getTour().getCost()} UAH
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                            ${order.getUser().getDiscount()}%
                    </td>
                    <td>
                        <a href="<c:url value="/admin/user?id=${order.getUser().getId()}"/>">
                                ${order.getUser().getName()} ${order.getUser().getSurname()}
                        </a>
                    </td>
                    <td>
                            ${order.getOrderStatus()}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</main>
</body>
</html>
