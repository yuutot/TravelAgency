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
    <h:head title="User id: ${userProfile.getId()}"/>
</head>
<body class="admin">
<%@include file="/WEB-INF/jspf/AdminHeader.jspf"%>
<main>
    <div class="container">
        <h2>${userProfile.getName()} ${userProfile.getSurname()}</h2>
        <div class="row">
            <div class="col-md-12 order-details">
                <p>
                    <i class="fa fa-phone" aria-hidden="true"></i> ${userProfile.getPhone()}
                </p>
            </div>
            <div class="col-md-12 order-list">
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
                                    ${order.getTour().getCost()} UAH
                            </td>
                            <td>
                                    ${order.getOrderStatus()}
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>
    </div>
</main>
</body>
</html>
