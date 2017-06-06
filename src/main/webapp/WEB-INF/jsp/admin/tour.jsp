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
<fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${tour.getDateFrom()}" var="dateTo" />
<fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${tour.getDateTo()}" var="dateFrom" />
<fmt:formatDate value="${dateTo}" var="pDateTo" pattern="dd.MM.yy HH:mm"/>
<fmt:formatDate value="${dateFrom}" var="pDateFrom" pattern="dd.MM.yy HH:mm"/>

<fmt:message key="home.lan" var="l_language"/>
<fmt:message key="admin.tour.title" var="l_tour_title"/>
<fmt:message key="admin.tour.delete" var="l_tour_delete"/>
<fmt:message key="admin.tour.change" var="l_tour_change"/>
<fmt:message key="admin.tour.hot" var="l_tour_hot"/>
<fmt:message key="admin.tour.bus" var="l_tour_bus"/>
<fmt:message key="admin.tour.plane" var="l_tour_plane"/>
<fmt:message key="admin.tour.ship" var="l_tour_ship"/>
<fmt:message key="admin.tour.train" var="l_tour_train"/>
<html>
<head>
    <h:head title="${l_tour_title}${tour.getId()}"/>
</head>
<body class="admin">
<%@include file="/WEB-INF/jspf/AdminHeader.jspf"%>
<main>
    <div class="container">
        <div class="top-panel">
            <h2>${tour.getTitle()}</h2>
            <a class="btn-primary add-new" href="/admin/editTour?id=${tour.getId()}&type=delete">${l_tour_delete}</a>
        </div>
        <div class="row">
            <div class="col-md-6 order-details">
                <p class="price-page-p"> <i class="fa fa-fire" aria-hidden="true"></i> ${l_tour_hot}: ${tour.getHot()}
                    <a href="/admin/editTour?id=${tour.getId()}&type=hot" class="btn-primary">${l_tour_change}</a>
                </p>
                <p>
                    <i class="fa fa-clock-o" aria-hidden="true"></i> ${pDateFrom} - ${pDateTo}
                </p>
                <p>
                    <i class="fa fa-map-marker" aria-hidden="true"></i> ${tour.getHotel().getCity().getName()}
                </p>
                <p class="price-page-p"> <i class="fa fa-usd" aria-hidden="true"></i> ${tour.getCost()} UAH</p>
                <p class="price-page-p"> <i class="fa fa-check-circle-o" aria-hidden="true"></i> ${tour.getTourType()}</p>

                <p>
                    <i class="fa fa-h-square" aria-hidden="true"></i> ${tour.getHotel().getName()}
                </p>
                <c:choose>
                    <c:when test="${tour.getTransportType().toString().equals('BUS')}">
                        <p><i class="fa fa-bus" aria-hidden="true"></i> ${l_tour_bus}</p>
                    </c:when>
                    <c:when test="${tour.getTransportType().toString().equals('TRAIN')}">
                        <p><i class="fa fa-train" aria-hidden="true"></i> ${l_tour_train}</p>
                    </c:when>
                    <c:when test="${tour.getTransportType().toString().equals('PLANE')}">
                        <p><i class="fa fa-plane" aria-hidden="true"></i> ${l_tour_plane}</p>
                    </c:when>
                    <c:when test="${tour.getTransportType().toString().equals('SHIP')}">
                        <p><i class="fa fa-ship" aria-hidden="true"></i> ${l_tour_ship}</p>
                    </c:when>
                </c:choose>

            </div>

        </div>
    </div>
</main>
</body>
</html>