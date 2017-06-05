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

<fmt:message key="home.login" var="l_login"/>
<fmt:message key="home.password" var="l_password"/>
<fmt:message key="home.lan" var="l_language"/>
<html>
<head>
    <h:head title="Orders"/>
</head>
<body>
<%@include file="/WEB-INF/jspf/UserHeader.jspf"%>
<section class="top-banner">
    <div class="insurance-header-bg">
        <img src="img/3.jpg" alt="">
        <div class="large-bg"></div>
        <div class="flex-cont">
            <div class="block-title-cell">
                <h1>Ваши заказы</h1>
            </div>
        </div>
    </div>
</section>
<div class="container content-tour order-list">
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
                Статус
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