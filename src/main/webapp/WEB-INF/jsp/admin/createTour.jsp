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
<fmt:message key="admin.createTour.title" var="l_ct_title"/>
<fmt:message key="admin.createTour.add" var="l_ct_add"/>
<fmt:message key="admin.createTour.name" var="l_ct_name"/>
<fmt:message key="admin.createTour.cost" var="l_ct_cost"/>
<fmt:message key="admin.createTour.startDate" var="l_ct_startDate"/>
<fmt:message key="admin.createTour.finishDate" var="l_ct_finishDate"/>
<fmt:message key="admin.createTour.type" var="l_ct_type"/>
<fmt:message key="admin.createTour.rest" var="l_ct_type_rest"/>
<fmt:message key="admin.createTour.excursion" var="l_ct_type_excursion"/>
<fmt:message key="admin.createTour.shopping" var="l_ct_type_shopping"/>
<fmt:message key="admin.createTour.transfer" var="l_ct_transfer"/>
<fmt:message key="admin.createTour.bus" var="l_ct_transfer_bus"/>
<fmt:message key="admin.createTour.plane" var="l_ct_transfer_plane"/>
<fmt:message key="admin.createTour.ship" var="l_ct_transfer_ship"/>
<fmt:message key="admin.createTour.train" var="l_ct_transfer_train"/>
<fmt:message key="admin.createTour.hotel" var="l_ct_hotel"/>
<fmt:message key="admin.createTour.description" var="l_ct_desc"/>
<fmt:message key="admin.createTour.photo" var="l_ct_photo"/>
<fmt:message key="admin.createTour.hot" var="l_ct_hot"/>
<fmt:message key="admin.createTour.save" var="l_ct_save"/>
<html>
<head>
    <h:head title="${l_ct_title}"/>
</head>
<body class="admin">
<%@include file="/WEB-INF/jspf/AdminHeader.jspf"%>
<main>
    <div class="container">
        <form class="row" action="/execute" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="create_tour">
            <div class="col-md-10 order-details add-new-item" style="margin-top: 0;">
                <h2>${l_ct_add}</h2>
                <input style="margin-top: 20px;" type="text" placeholder="${l_ct_name}" name="title">
                <p>
                    ${l_ct_cost}
                </p>
                <input type="number" name="cost">
                <div class="date" style="width: 49%; margin-right: 2%;">
                    <p>
                        ${l_ct_startDate}
                    </p>
                    <input type="datetime-local" name="date_from">

                </div>
                <div class="date" style="width: 49%;">
                    <p>
                        ${l_ct_finishDate}
                    </p>
                    <input type="datetime-local" name="date_to">
                </div>

                <p>
                    ${l_ct_type}
                </p>
                <select name="tour_type">
                    <option value="REST">${l_ct_type_rest}</option>
                    <option value="EXCURSION">${l_ct_type_excursion}</option>
                    <option value="SHOPPING">${l_ct_type_shopping}</option>
                </select>
                <p>
                    ${l_ct_transfer}
                </p>
                <select name="transport_type">
                    <option value="BUS">${l_ct_transfer_bus}</option>
                    <option value="PLANE">${l_ct_transfer_plane}</option>
                    <option value="TRAIN">${l_ct_transfer_train}</option>
                    <option value="SHIP">${l_ct_transfer_ship}</option>
                </select>
                <p>
                    ${l_ct_hotel}
                </p>
                <select name="hotel">
                    <c:forEach var="hotel" items="${hotels}">
                        <option value="${hotel.getId()}">${hotel.getName()}</option>
                    </c:forEach>
                </select>
                <p>
                    ${l_ct_desc}
                </p>
                <input type="text" name="description">
                <p style="width: 100%">
                    ${l_ct_photo}
                </p>
                <input style="width: 100%;" type="file" name="photo">
                <div class="check">
                    <input style="width: auto" type="checkbox" name="is_hot" value="true">   <p>${l_ct_hot}</p>
                </div>
                <hr>
                <input style="width: 20%;" class="btn-primary btn-new" type="submit" value="${l_ct_save}">
            </div>
        </form>
    </div>
</main>
</body>
</html>