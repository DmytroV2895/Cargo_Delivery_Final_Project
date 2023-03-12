<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title>Create invoice</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/order.css">
</head>
<body>
<header>
    <c:import url="../guest/header.jsp"/>
</header>

<div class="container register-form top-buffer-1" >
    <div class="form" >
        <div class="note">
            <p><fmt:message key="label.create_new_order"/></p>
        </div>
        <div class="col-sm-10" style="color:red;">
            <h5>${message}</h5>
        </div>
        <div class="form-content bk">
            <form action="controller" method="POST">

                <fieldset class="scheduler-border">
                    <legend class="scheduler-border"><fmt:message key="label.sender_information"/></legend>
                    <div class="row">
                        <div class="col-sm-4">
                            <label><fmt:message key="label.name"/></label>
                            <output type="text" class="form-control" id="name" name="name">${user.name}</output>
                        </div>
                        <div class="col-sm-4">
                            <label><fmt:message key="label.surname"/></label>
                            <output type="text" class="form-control" id="surname" name="surname">${user.surname}</output>
                        </div>
                        <div class="col-sm-4">
                            <label><fmt:message key="label.phone_number"/></label>
                            <output type="text" class="form-control" id="phone" name="phone">${user.phone}</output>
                        </div>
                    </div>
                    <div class="section-to-print" id="sender_address">
                        <div class="row top-buffer">
                            <div class="form-group col-sm-4">
                                <label for="first_city"><fmt:message key="label.sent_by"/></label>
                                <select id="first_city" class="form-control" name="first_city" required>
                                    <option selected disabled><fmt:message key="label.choose_city"/></option>
                                    <option value="SUMY"><fmt:message key="label.sumy"/></option>
                                    <option value="LVIV"><fmt:message key="label.lviv"/></option>
                                    <option value="CHARKIV"><fmt:message key="label.charkiv"/></option>
                                    <option value="ODESSA"><fmt:message key="label.odessa"/></option>
                                </select>
                            </div>
                            <div class="form-group col-sm-4">
                                <label for="first_street_name"><fmt:message key="label.street_name"/></label>
                                <input type="text" class="form-control" id="first_street_name" name="first_street_name" required  pattern="[A-ZА-ЯІ][a-z-а-яії]{1,39}">
                            </div>
                            <div class="form-group col-sm-2">
                                <label for="first_street_number"><fmt:message key="label.street_number"/></label>
                                <input type="text" class="form-control" id="first_street_number" name="first_street_number" required pattern="^[0-9]*$">
                            </div>
                            <div class="form-group col-md-2">
                                <label for="first_house_number"><fmt:message key="label.house_number"/></label>
                                <input type="text" class="form-control" id="first_house_number" name="first_house_number" required pattern="^[0-9]*$">
                            </div>
                        </div>
                    </div>
                </fieldset>

                <fieldset class="scheduler-border">
                    <legend class="scheduler-border"><fmt:message key="label.recipient_information"/></legend>
                    <div class="row">
                        <div class="col-sm-4">
                            <label for="recipient_name"><fmt:message key="label.name"/></label>
                            <input type="text" class="form-control" id="recipient_name" name="recipient_name" required
                                   pattern="[A-ZА-ЯІ][a-z-а-яії]{1,39}"
                                   title="<fmt:message key="label.pattern_title_user_name"/>">
                        </div>
                        <div class="col-sm-4">
                            <label for="recipient_surname"><fmt:message key="label.surname"/></label>
                            <input type="text" class="form-control" id="recipient_surname" name="recipient_surname" required
                                   pattern="[A-ZА-ЯІ][a-z-а-яії]{1,39}"
                                   title="<fmt:message key="label.pattern_title_user_surname"/>">
                        </div>
                        <div class="col-sm-4">
                            <label for="recipient_phone"><fmt:message key="label.phone_number"/></label>
                            <input type="text" class="form-control" id="recipient_phone" autocomplete="off" name="recipient_phone" placeholder="+380123651296" required pattern="^\+380\d{3}\d{2}\d{2}\d{2}$"/>
                        </div>
                    </div>
                    <div class="section-to-print" id="recipient_address">
                        <div class="row top-buffer">
                            <div class="form-group col-sm-4">
                                <label for="second_city"><fmt:message key="label.recipient_city"/></label>
                                <select id="second_city" class="form-control" name="second_city" required>
                                    <option selected disabled><fmt:message key="label.choose_city"/></option>
                                    <option value="SUMY"><fmt:message key="label.sumy"/></option>
                                    <option value="LVIV"><fmt:message key="label.lviv"/></option>
                                    <option value="CHARKIV"><fmt:message key="label.charkiv"/></option>
                                    <option value="ODESSA"><fmt:message key="label.odessa"/></option>
                                </select>
                            </div>
                            <div class="form-group col-sm-4">
                                <label for="second_street_name"><fmt:message key="label.street_name"/></label>
                                <input type="text" class="form-control" id="second_street_name" name ="second_street_name" required  pattern="[A-ZА-ЯІ][a-z-а-яії]{1,39}">
                            </div>
                            <div class="form-group col-sm-2">
                                <label for="second_street_number"><fmt:message key="label.street_number"/></label>
                                <input type="text" class="form-control" id="second_street_number" name ="second_street_number"  required pattern="^[0-9]*$" >
                            </div>
                            <div class="form-group col-md-2">
                                <label for="second_house_number"><fmt:message key="label.house_number"/></label>
                                <input type="text" class="form-control" id="second_house_number" name ="second_house_number"  required pattern="^[0-9]*$">
                            </div>
                        </div>
                    </div>
                </fieldset>


                <fieldset class="scheduler-border">
                    <legend class="scheduler-border"><fmt:message key="label.order_information"/></legend>
                    <div class="row">
                        <div class="col-sm-4">
                            <label for="order_name"><fmt:message key="label.order_name"/></label>
                            <input type="text" class="form-control" id="order_name" name ="order_name" required>
                        </div>

                        <div class="col-sm-2">
                            <label for="price"><fmt:message key="label.order_price"/></label>
                            <input type="text" class="form-control" id="price" name ="price" required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>
                        <div class="col-sm-2">
                            <label for="weight"><fmt:message key="label.order_weight"/></label>
                            <input type="text" class="form-control" id="weight" name ="weight"  required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>

                        <div class="col-sm-2">
                            <label for="order_type"><fmt:message key="label.choose_order_type"/></label>
                            <select class="form-control" id="order_type" name="order_type" required>
                                <option selected disabled></option>
                                <option value="DOCUMENT"><fmt:message key="label.order_type_document"/></option>
                                <option value="PARCEL"><fmt:message key="label.order_type_parcel"/></option>
                                <option value="CARGO"><fmt:message key="label.order_type_cargo"/></option>
                            </select>
                        </div>

                        <div class="col-sm-2">
                            <label for="delivery_type"><fmt:message key="label.delivery_type"/></label>
                            <select class="form-control" id="delivery_type" name="delivery_type" required>
                                <option selected disabled></option>
                                <option value="BY_TRUCK"><fmt:message key="label.by_truck"/></option>
                                <option value="COURIER"><fmt:message key="label.courier"/></option>
                            </select>
                        </div>

                    </div>

                    <div class="row top-buffer">
                        <div class="col-sm-2">
                            <label for="length"><fmt:message key="label.order_length"/></label>
                            <input type="text" class="form-control" id="length" name="length"  required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>
                        <div class="col-sm-2">
                            <label for="height"><fmt:message key="label.order_height"/></label>
                            <input type="text" class="form-control" id="height" name="height"  required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>
                        <div class="col-sm-2">
                            <label for="width"><fmt:message key="label.order_width"/></label>
                            <input type="text" class="form-control" id="width" name="width"  required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>
                        <div class="col-sm-5">
                            <label for="order_description"><fmt:message key="label.order_description"/></label>
                            <textarea class="form-control" aria-label="Order description:" id="order_description"
                                      name="order_description" required></textarea>
                        </div>
                    </div>
                </fieldset>

                <button type="submit" class="btn btn-success"  value="submit"><fmt:message key="label.create_order"/></button>
                    <input type="hidden" name="command" value="create_new_order">
            </form>

        </div>
    </div>
</div>
</body>
<tags:footer_default_locale/>
</html>


