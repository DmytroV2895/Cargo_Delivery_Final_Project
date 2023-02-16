<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.edit_order"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="css/order.css">
</head>

<body>
<c:import url="header.jsp"/>
<div class="container register-form top-buffer-1">
    <div class="form">
        <div class="note">
            <p><fmt:message key="label.edit_order"/></p>
        </div>
        <div class="form-content bk">
            <c:forEach var="data" items="${invoice_by_id}">
            <form action="controller" method="post">

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
                            <output type="text" class="form-control" id="name"
                                    name="name">${data.user.name}</output>
                        </div>

                        <div class="col-sm-4">
                            <label><fmt:message key="label.surname"/></label><br>
                            <output type="text" class="form-control" id="surname"
                                    name="surname">${data.user.surname}</output>
                        </div>
                        <div class="col-sm-4">
                            <label><fmt:message key="label.phone_number"/></label><br>
                            <output type="text" class="form-control" id="phone"
                                    name="phone">${data.user.phone}</output>
                        </div>
                    </div>

                    <div class="section-to-print" id="sender_address">
                        <div class="row top-buffer">
                            <div class="form-group col-sm-4">
                                <label><fmt:message key="label.sent_by"/></label><br>
                                <select class="form-control" id="first_city" name="first_city" required>
                                    <c:if test="${data.addressFirst.firstCity eq 'SUMY'}">
                                        <option><fmt:message key="label.sumy"/></option>
                                    </c:if>
                                    <c:if test="${data.addressFirst.firstCity eq 'LVIV'}">
                                        <option><fmt:message key="label.lviv"/></option>
                                    </c:if>
                                    <c:if test="${data.addressFirst.firstCity eq 'CHARKIV'}">
                                        <option><fmt:message key="label.charkiv"/></option>
                                    </c:if>
                                    <c:if test="${data.addressFirst.firstCity eq 'ODESSA'}">
                                        <option><fmt:message key="label.odessa"/></option>
                                    </c:if>
                                    <option value="SUMY"><fmt:message key="label.sumy"/></option>
                                    <option value="LVIV"><fmt:message key="label.lviv"/></option>
                                    <option value="CHARKIV"><fmt:message key="label.charkiv"/></option>
                                    <option value="ODESSA"><fmt:message key="label.odessa"/></option>
                                </select>
                            </div>
                            <div class="form-group col-sm-4">
                                <label for="first_street_name"><fmt:message key="label.street_name"/></label>
                                <input type="text" class="form-control" id="first_street_name"
                                       name="first_street_name"
                                       value="${data.addressFirst.firstStreetName}" required>
                            </div>
                            <div class="form-group col-sm-2">
                                <label for="first_street_number"><fmt:message key="label.street_number"/></label>
                                <input type="text" class="form-control" id="first_street_number"
                                       name="first_street_number"
                                       value="${data.addressFirst.firstStreetNumber}" required>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="first_house_number"><fmt:message key="label.house_number"/></label>
                                <input type="text" class="form-control" id="first_house_number"
                                       name="first_house_number"
                                       value="${data.addressFirst.firstHouseNumber}" required>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset class="scheduler-border">
                    <legend class="scheduler-border"><fmt:message key="label.recipient_information"/></legend>
                    <div class="row">
                        <div class="col-sm-4">
                            <label for="recipient_name"><fmt:message key="label.name"/></label>
                            <input type="text" class="form-control" id="recipient_name" name="recipient_name"
                                   value="${data.delivery.recipientName}" required>
                        </div>
                        <div class="col-sm-4">
                            <label for="recipient_surname"><fmt:message key="label.surname"/></label>
                            <input type="text" class="form-control" id="recipient_surname" name="recipient_surname"
                                   value="${data.delivery.recipientSurname}" required>
                        </div>
                        <div class="col-sm-4">
                            <label for="recipient_phone"><fmt:message key="label.phone_number"/></label>
                            <input type="text" class="form-control" id="recipient_phone" name="recipient_phone"
                                   value="${data.delivery.recipientPhone}" required>
                        </div>
                    </div>
                    <div class="section-to-print" id="recipient_address">
                        <div class="row top-buffer">
                            <div class="form-group col-sm-4">
                                <label for="second_city"><fmt:message key="label.recipient_city"/></label>
                                <select class="form-control" id="second_city" name="second_city" required>
                                    <c:if test="${data.addressSecond.secondCity eq 'SUMY'}">
                                        <option><fmt:message key="label.sumy"/></option>
                                    </c:if>
                                    <c:if test="${data.addressSecond.secondCity eq 'LVIV'}">
                                        <option><fmt:message key="label.lviv"/></option>
                                    </c:if>
                                    <c:if test="${data.addressSecond.secondCity eq 'CHARKIV'}">
                                        <option><fmt:message key="label.charkiv"/></option>
                                    </c:if>
                                    <c:if test="${data.addressSecond.secondCity eq 'ODESSA'}">
                                        <option><fmt:message key="label.odessa"/></option>
                                    </c:if>
                                    <option value="SUMY"><fmt:message key="label.sumy"/></option>
                                    <option value="LVIV"><fmt:message key="label.lviv"/></option>
                                    <option value="CHARKIV"><fmt:message key="label.charkiv"/></option>
                                    <option value="ODESSA"><fmt:message key="label.odessa"/></option>
                                </select>
                            </div>
                            <div class="form-group col-sm-4">
                                <label for="second_street_name"><fmt:message key="label.street_name"/></label>
                                <input type="text" class="form-control" id="second_street_name"
                                       name="second_street_name"
                                       value="${data.addressSecond.secondStreetName}" required>
                            </div>
                            <div class="form-group col-sm-2">
                                <label for="second_street_number"><fmt:message key="label.street_number"/></label>
                                <input type="text" class="form-control" id="second_street_number"
                                       name="second_street_number"
                                       value="${data.addressSecond.secondStreetNumber}" required>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="second_house_number"><fmt:message key="label.house_number"/></label>
                                <input type="text" class="form-control" id="second_house_number"
                                       name="second_house_number"
                                       value="${data.addressSecond.secondHouseNumber}" required>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset class="scheduler-border">
                    <legend class="scheduler-border"><fmt:message key="label.order_information"/></legend>
                    <div class="row">

                        <div class="col-sm-4">
                            <label for="order_name"><fmt:message key="label.order_name"/></label>
                            <input type="text" class="form-control" id="order_name" name="order_name"
                                   value="${data.order.orderName}" required>
                        </div>

                        <div class="col-sm-2">
                            <label for="price"><fmt:message key="label.order_price"/></label>
                            <input type="text" class="form-control" id="price" name="price"
                                   value="${data.order.price}" required>
                        </div>
                        <div class="col-sm-2">
                            <label for="weight"><fmt:message key="label.order_weight"/></label>
                            <input type="text" class="form-control" id="weight" name="weight"
                                   value="${data.order.weight}" required>
                        </div>

                        <div class="col-sm-2">
                            <label for="order_type"><fmt:message key="label.choose_order_type"/></label>
                            <select class="form-control" id="order_type" name="order_type" required>
                                <c:if test="${data.order.type eq 'DOCUMENT'}">
                                    <option value="DOCUMENT"><fmt:message key="label.order_type_document"/></option>
                                </c:if>
                                <c:if test="${data.order.type eq 'PARCEL'}">
                                    <option value="PARCEL"><fmt:message key="label.order_type_parcel"/></option>
                                </c:if>
                                <c:if test="${data.order.type eq 'CARGO'}">
                                    <option value="CARGO"><fmt:message key="label.order_type_cargo"/></option>
                                </c:if>
                                <option value="DOCUMENT"><fmt:message key="label.order_type_document"/></option>
                                <option value="PARCEL"><fmt:message key="label.order_type_parcel"/></option>
                                <option value="CARGO"><fmt:message key="label.order_type_cargo"/></option>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <label for="delivery_type"><fmt:message key="label.delivery_type"/></label>
                            <select class="form-control" id="delivery_type" name="delivery_type" required>
                                <c:if test="${data.delivery.type eq 'BY_TRUCK'}">
                                    <option value="BY_TRUCK"><fmt:message key="label.by_truck"/></option>
                                </c:if>
                                <c:if test="${data.delivery.type eq 'COURIER'}">
                                    <option value="COURIER"><fmt:message key="label.courier"/></option>
                                </c:if>
                                <option value="BY_TRUCK"><fmt:message key="label.by_truck"/></option>
                                <option value="COURIER"><fmt:message key="label.courier"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="row top-buffer">
                        <div class="col-sm-2">
                            <label for="distance"><fmt:message key="label.delivery_distance"/></label>
                            <input type="text" class="form-control" id="distance" name="distance"
                                   value="${data.delivery.deliveryDistance}" required>
                        </div>
                        <div class="col-sm-2">
                            <label><fmt:message key="label.order_length"/></label>
                            <label>
                                <input type="text" class="form-control" id="length" name="length"
                                       value="${data.order.length}" required>
                            </label>
                        </div>
                        <div class="col-sm-2">
                            <label for="height"><fmt:message key="label.order_height"/></label>
                            <input type="text" class="form-control" id="height" name="height"
                                   value="${data.order.height}" required>
                        </div>
                        <div class="col-sm-2">
                            <label for="width"><fmt:message key="label.order_width"/></label>
                            <input type="text" class="form-control" id="width" name="width"
                                   value="${data.order.width}" required>
                        </div>
                        <div class="col-sm-12">
                            <label for="order_description"><fmt:message key="label.order_description"/></label>
                            <input class="form-control" id="order_description"
                                   name="order_description" value="${data.order.orderDescription}" required>
                        </div>
                    </div>
                </fieldset>

                <c:if test="${user.getRole().toString() eq 'USER' and data.orderStatus eq 'NOT_PROCESSED'}">
                <button type="submit" class="btn btn-success" value="submit"><fmt:message
                        key="label.update_delivery_info"/></button>
                <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                <input type="hidden" name="order_id" value="${data.order.orderId}">
                <input type="hidden" name="delivery_id" value="${data.delivery.deliveryId}">
                <input type="hidden" name="id_first_address" value="${data.addressFirst.firstAddressId}">
                <input type="hidden" name="id_second_address" value="${data.addressSecond.secondAddressId}">
                <input type="hidden" name="command" value="update_order_info">
                </c:if>
                </c:forEach>
        </div>
    </div>
</div>
</body>
<tags:footer_locale_invoice_by_id/>
</html>


