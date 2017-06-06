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
<fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.getTour().getDateFrom()}" var="dateTo" />
<fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.getTour().getDateTo()}" var="dateFrom" />
<fmt:formatDate value="${dateTo}" var="pDateTo" pattern="dd.MM.yy HH:mm"/>
<fmt:formatDate value="${dateFrom}" var="pDateFrom" pattern="dd.MM.yy HH:mm"/>
<fmt:message key="home.lan" var="l_language"/>
<fmt:message key="admin.order.title" var="l_order_title"/>
<fmt:message key="admin.order.status" var="l_order_status"/>
<fmt:message key="admin.order.status.new" var="l_order_status_new"/>
<fmt:message key="admin.order.status.paid" var="l_order_status_paid"/>
<fmt:message key="admin.order.status.canceled" var="l_order_status_canceled"/>
<fmt:message key="admin.order.status.change" var="l_order_status_change"/>
<fmt:message key="admin.order.client" var="l_order_client"/>
<fmt:message key="admin.order.discount" var="l_order_discount"/>
<fmt:message key="admin.order.details" var="l_order_details"/>
<html>
<head>
    <h:head title="${l_order_title}${order.getId()}"/>
</head>
<body class="admin">
<%@include file="/WEB-INF/jspf/AdminHeader.jspf" %>
<main>
    <div class="container">
        <h2>${l_order_title}${order.getId()}</h2>
        <div class="row">
            <div class="col-md-6 order-details">
                <h2> ${order.getTour().getTitle()} </h2>
                <p>
                    <i class="fa fa-clock-o" aria-hidden="true"></i> ${pDateFrom} - ${pDateTo}
                </p>
                <p>
                    <i class="fa fa-map-marker"
                       aria-hidden="true"></i> ${order.getTour().getHotel().getCity().getName()}
                </p>

                <p class="price-page-p"><i class="fa fa-usd" aria-hidden="true"></i>
                    <c:choose>
                        <c:when test="${order.getUser().getDiscount() > 0}">
                            ${order.getTour().getCost()*(1.0-order.getUser().getDiscount()/100.0)} UAH
                        </c:when>
                        <c:otherwise>
                            ${order.getTour().getCost()} UAH
                        </c:otherwise>
                    </c:choose>
                </p>

                <h2> ${l_order_status} </h2>
                <form action="/execute?id=${order.getId()}" method="post">
                    <input type="hidden" name="command" value="edit_order_status">
                    <select name="status">
                        <option value="NEW">${l_order_status_new}</option>
                        <option value="PAID">${l_order_status_paid}</option>
                        <option value="CANCELED">${l_order_status_canceled}</option>
                    </select>
                    <input type="submit" value="${l_order_status_change}" class="btn btn-sm btn-primary btn-new">
                </form>
            </div>
            <div class="col-md-6 order-details">
                <h2> ${l_order_client} </h2>
                <p>
                    <i class="fa fa-user-circle"
                       aria-hidden="true"></i> ${order.getUser().getName()} ${order.getUser().getSurname()}
                </p>
                <p>
                    <i class="fa fa-gift" aria-hidden="true"></i> ${l_order_discount} ${order.getUser().getDiscount()} %
                </p>
                <p>
                    <i class="fa fa-envelope-o" aria-hidden="true"></i> ${order.getUser().getPhone()}
                </p>
                <a class="btn btn-sm btn-primary btn-new"
                   href="<c:url value="/admin/user?id=${order.getUser().getId()}"/>">${l_order_details}</a>

            </div>
        </div>
    </div>
</main>
</body>
</html>