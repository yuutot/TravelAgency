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
<fmt:message key="home.login" var="l_login"/>
<fmt:message key="orders.city" var="l_orders_city"/>
<fmt:message key="orders.cost" var="l_orders_cost"/>
<fmt:message key="orders.date" var="l_orders_date"/>
<fmt:message key="orders.name" var="l_orders_name"/>
<fmt:message key="orders.status" var="l_orders_status"/>
<fmt:message key="orders.title" var="l_orders_title"/>
<html>
<head>
    <h:head title="${l_orders_title}"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf"%>
<section class="top-banner">
    <div class="insurance-header-bg">
        <img src="img/3.jpg" alt="">
        <div class="large-bg"></div>
        <div class="flex-cont">
            <div class="block-title-cell">
                <h1>${l_orders_title}</h1>
            </div>
        </div>
    </div>
</section>
<div class="container content-tour order-list">
    <table>
        <tr>
            <td>
                ${l_orders_name}
            </td>
            <td>
                ${l_orders_city}
            </td>
            <td>
                ${l_orders_date}
            </td>
            <td>
                ${l_orders_cost}
            </td>
            <td>
                ${l_orders_status}
            </td>
        </tr>
        <c:forEach var="order" items="${orders}">
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.getTour().getDateFrom()}" var="dateTo" />
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.getTour().getDateTo()}" var="dateFrom" />
            <fmt:formatDate value="${dateTo}" var="pDateTo" pattern="dd.MM.yy HH:mm"/>
            <fmt:formatDate value="${dateFrom}" var="pDateFrom" pattern="dd.MM.yy HH:mm"/>
            <tr>
                <td>
                    <a href="<c:url value="/tours?id=${order.getTour().getId()}"/>">
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
                        ${order.getTour().getCost()} UAH
                </td>
                <td class="ok">
                        ${order.getOrderStatus()}
                </td>
            </tr>
        </c:forEach>

    </table>
</div>
<%@include file="/WEB-INF/jspf/UserFooter.jspf"%>
</body>
</html>