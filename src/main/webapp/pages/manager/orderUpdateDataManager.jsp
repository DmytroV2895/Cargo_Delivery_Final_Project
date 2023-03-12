<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.order_info"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/order.css">
</head>

<body>
<header>
    <c:import url="../guest/header.jsp"/>
</header>

<div class="container register-form top-buffer-1">
    <div class="form">
        <div class="note">
            <p><fmt:message key="label.order_info"/></p>
        </div>
        <div class="form-content bk">
            <c:forEach var="data" items="${invoice_by_id}">


                <div class="row top-buffer">
                    <div class="col-sm-4">
                        <h4><label><fmt:message key="label.invoice_number"/>${data.invoiceId}</label></h4><br>
                    </div>
                </div>


                <fieldset class="scheduler-border">
                    <legend class="scheduler-border"><fmt:message key="label.sender_information"/></legend>
                    <div class="row">
                        <div class="col-sm-4">
                            <label><fmt:message key="label.name"/></label><br>
                            <output type="text" class="form-control" id="name" name="name">${data.user.name}</output>
                        </div>

                        <div class="col-sm-4">
                            <label><fmt:message key="label.surname"/></label><br>
                            <output type="text" class="form-control" id="surname"
                                    name="surname">${data.user.surname}</output>
                        </div>
                        <div class="col-sm-4">
                            <label><fmt:message key="label.phone_number"/></label><br>
                            <output type="text" class="form-control" id="phone" name="phone">${data.user.phone}</output>
                        </div>
                    </div>

                    <div class="section-to-print" id="sender_address">
                        <div class="row top-buffer">
                            <div class="form-group col-sm-4">
                                <label><fmt:message key="label.sent_by"/></label><br>
                                <output type="text" class="form-control" id="first_city" name="first_city">
                                    <c:if test="${data.addressFirst.firstCity eq 'SUMY'}"><fmt:message
                                            key="label.sumy"/></c:if>
                                    <c:if test="${data.addressFirst.firstCity eq 'LVIV'}"><fmt:message
                                            key="label.lviv"/></c:if>
                                    <c:if test="${data.addressFirst.firstCity eq 'CHARKIV'}"><fmt:message
                                            key="label.charkiv"/></c:if>
                                    <c:if test="${data.addressFirst.firstCity eq 'ODESSA'}"><fmt:message
                                            key="label.odessa"/></c:if>
                                </output>

                            </div>
                            <div class="form-group col-sm-4">
                                <label for="first_street_name"><fmt:message key="label.street_name"/></label>
                                <output type="text" class="form-control" id="first_street_name"
                                        name="first_street_name">${data.addressFirst.firstStreetName}</output>
                            </div>
                            <div class="form-group col-sm-2">
                                <label for="first_street_number"><fmt:message key="label.street_number"/></label>
                                <output type="text" class="form-control" id="first_street_number"
                                        name="first_street_number">${data.addressFirst.firstStreetNumber}</output>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="first_house_number"><fmt:message key="label.house_number"/></label>
                                <output type="text" class="form-control" id="first_house_number"
                                        name="first_house_number">${data.addressFirst.firstHouseNumber}</output>
                            </div>
                        </div>
                    </div>

                </fieldset>

                <fieldset class="scheduler-border">
                    <legend class="scheduler-border"><fmt:message key="label.recipient_information"/></legend>
                    <div class="row">
                        <div class="col-sm-4">
                            <label for="recipient_name"><fmt:message key="label.name"/></label>
                            <output type="text" class="form-control" id="recipient_name"
                                    name="recipient_name">${data.delivery.recipientName}</output>
                        </div>
                        <div class="col-sm-4">
                            <label for="recipient_surname"><fmt:message key="label.surname"/></label>
                            <output type="text" class="form-control" id="recipient_surname"
                                    name="recipient_surname">${data.delivery.recipientSurname}</output>
                        </div>
                        <div class="col-sm-4">
                            <label for="recipient_phone"><fmt:message key="label.phone_number"/></label>
                            <output type="text" class="form-control" id="recipient_phone"
                                    name="recipient_phone">${data.delivery.recipientPhone}</output>
                        </div>
                    </div>

                    <div class="section-to-print" id="recipient_address">
                        <div class="row top-buffer">
                            <div class="form-group col-sm-4">
                                <label for="second_city"><fmt:message key="label.recipient_city"/></label>
                                <output type="text" class="form-control" id="second_city" name="second_city">
                                    <c:if test="${data.addressSecond.secondCity eq 'SUMY'}"><fmt:message
                                            key="label.sumy"/></c:if>
                                    <c:if test="${data.addressSecond.secondCity eq 'LVIV'}"><fmt:message
                                            key="label.lviv"/></c:if>
                                    <c:if test="${data.addressSecond.secondCity eq 'CHARKIV'}"><fmt:message
                                            key="label.charkiv"/></c:if>
                                    <c:if test="${data.addressSecond.secondCity eq 'ODESSA'}"><fmt:message
                                            key="label.odessa"/></c:if>
                                </output>
                            </div>
                            <div class="form-group col-sm-4">
                                <label for="second_street_name"><fmt:message key="label.street_name"/></label>
                                <output type="text" class="form-control" id="second_street_name"
                                        name="second_street_name">${data.addressSecond.secondStreetName}</output>
                            </div>
                            <div class="form-group col-sm-2">
                                <label for="second_street_number"><fmt:message key="label.street_number"/></label>
                                <output type="text" class="form-control" id="second_street_number"
                                        name="second_street_number">${data.addressSecond.secondStreetNumber}</output>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="second_house_number"><fmt:message key="label.house_number"/></label>
                                <output type="text" class="form-control" id="second_house_number"
                                        name="second_house_number">${data.addressSecond.secondHouseNumber}</output>
                            </div>
                        </div>
                    </div>
                </fieldset>

                <fieldset class="scheduler-border">
                    <legend class="scheduler-border"><fmt:message key="label.order_information"/></legend>
                    <div class="row">

                        <div class="col-sm-4">
                            <label for="order_name"><fmt:message key="label.order_name"/></label>
                            <output type="text" class="form-control" id="order_name"
                                    name="order_name">${data.order.orderName}</output>
                        </div>

                        <div class="col-sm-2">
                            <label for="price"><fmt:message key="label.order_price"/></label>
                            <output type="text" class="form-control" id="price"
                                    name="price">${data.order.price}</output>
                        </div>
                        <div class="col-sm-2">
                            <label for="weight"><fmt:message key="label.order_weight"/></label>
                            <output type="text" class="form-control" id="weight"
                                    name="weight">${data.order.weight}</output>
                        </div>

                        <div class="col-sm-2">
                            <label for="order_type"><fmt:message key="label.choose_order_type"/></label>
                            <output type="text" class="form-control" id="order_type" name="order_type">
                                <c:if test="${data.order.type eq 'DOCUMENT'}"><fmt:message
                                        key="label.order_type_document"/></c:if>
                                <c:if test="${data.order.type eq 'PARCEL'}"><fmt:message
                                        key="label.order_type_parcel"/></c:if>
                                <c:if test="${data.order.type eq 'CARGO'}"><fmt:message
                                        key="label.order_type_cargo"/></c:if>
                            </output>
                        </div>


                        <div class="col-sm-2">
                            <label for="delivery_type"><fmt:message key="label.delivery_type"/></label>
                            <output type="text" class="form-control" id="delivery_type" name="delivery_type">
                                <c:if test="${data.delivery.type eq 'BY_TRUCK'}"><fmt:message
                                        key="label.by_truck"/></c:if>
                                <c:if test="${data.delivery.type eq 'COURIER'}"><fmt:message
                                        key="label.courier"/></c:if>
                            </output>
                        </div>
                    </div>
                    <div class="row top-buffer">
                        <div class="col-sm-2">
                            <label for="distance"><fmt:message key="label.delivery_distance"/></label>
                            <output type="text" class="form-control" id="distance"
                                    name="distance">${data.delivery.deliveryDistance}</output>
                        </div>


                        <div class="col-sm-2">
                            <label><fmt:message key="label.order_length"/></label>
                            <output type="text" class="form-control" id="length"
                                    name="length">${data.order.length}</output>
                        </div>
                        <div class="col-sm-2">
                            <label for="height"><fmt:message key="label.order_height"/></label>
                            <output type="text" class="form-control" id="height"
                                    name="height">${data.order.height}</output>
                        </div>
                        <div class="col-sm-2">
                            <label for="width"><fmt:message key="label.order_width"/></label>
                            <output type="text" class="form-control" id="width"
                                    name="width">${data.order.width}</output>
                        </div>
                        <div class="col-sm-12">
                            <label for="order_description"><fmt:message key="label.order_description"/></label>
                            <output type="text" class="form-control" id="order_description"
                                    name="order_description">${data.order.orderDescription}</output>
                        </div>
                    </div>
                </fieldset>

                <br>
                <fieldset class="scheduler-border">
                    <form action="controller" method="post">
                        <div class="row top-buffer">
                            <div class="col-sm-4">
                                <h4><label for="delivery_date"><fmt:message key="label.delivery_date"/></label></h4>
                                <h4>
                                    <input type="date" class="form-control" id="delivery_date"
                                           name="delivery_date">
                                </h4>
                            </div>
                            <div class="col-sm-4">
                                <h4><label for="order_status"><fmt:message key="label.order_status"/></label></h4>

                                <select id="order_status" class="form-control" name="order_status">
                                    <option selected disabled><fmt:message key="label.order_not_processed"/></option>
                                    <option value="NOT_PROCESSED"><fmt:message
                                            key="label.order_not_processed"/></option>
                                    <option value="IN_PROCESSING"><fmt:message
                                            key="label.order_in_processing"/></option>
                                    <option value="ON_THE_WAY"><fmt:message key="label.order_on_the_way"/></option>
                                    <option value="RECEIVED"><fmt:message key="label.order_received"/></option>
                                </select>
                            </div>
                        </div>

                        <br>
                        <div class="col-sm-2">
                            <button type="submit" class="btn btn-primary ">
                            <i class="material-icons"><fmt:message key="label.update_delivery_info"/></i></button>
                            <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                            <input type="hidden" name="command" value="invoice_update_manager_command">
                        </div>
                    </form>
                </fieldset>
            </c:forEach>
        </div>
    </div>
</div>
</body>
<tags:footer_locale_invoice_by_id/>
</html>




















