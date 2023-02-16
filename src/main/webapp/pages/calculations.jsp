<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.calculate_delivery_price"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/order.css">
</head>
<body>
<header>
    <c:import url="header.jsp"/>
</header>
<div class="container register-form top-buffer-1">
    <div class="form">
        <div class="note">
            <p><fmt:message key="label.calculate_delivery_price"/></p>
        </div>

        <div class="col-sm-10" style="color:red;">
        <h5>${message}</h5>
        </div>

        <div class="form-content bk">
            <form action="controller" method="get">

                <fieldset class="scheduler-border">
                    <div class="row">
                        <div class="col-sm-3">
                            <label for="order_type"><fmt:message key="label.choose_order_type"/></label>
                            <select class="form-control" id="order_type" name="order_type" required>
                                <option selected disabled></option>
                                <option value="DOCUMENT"><fmt:message key="label.order_type_document"/></option>
                                <option value="PARCEL"><fmt:message key="label.order_type_parcel"/></option>
                                <option value="CARGO"><fmt:message key="label.order_type_cargo"/></option>
                            </select>
                        </div>

                        <div class="col-sm-3">
                            <label for="weight"><fmt:message key="label.order_weight"/></label>
                            <input type="text" class="form-control" id="weight" name="weight" max="1000" required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>

                        <div class="col-sm-3">
                            <label for="distance"><fmt:message key="label.delivery_distance"/></label>
                            <input type="text" class="form-control" id="distance" name="distance" required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>
                    </div>

                    <div class="row top-buffer">
                        <div class="col-sm-3">
                            <label for="length"><fmt:message key="label.order_length"/></label>
                            <input type="text" class="form-control" id="length" name="length" required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>
                        <div class="col-sm-3">
                            <label for="height"><fmt:message key="label.order_height"/></label>
                            <input type="text" class="form-control" id="height" name="height" required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>
                        <div class="col-sm-3">
                            <label for="width"><fmt:message key="label.order_width"/></label>
                            <input type="text" class="form-control" id="width" name="width" required pattern="^(0|[1-9]\d*)([.,]\d+)?">
                        </div>
                        <div class="col-sm-3">
                            <b><label for="total_price"><fmt:message key="label.delivery_price"/></label></b>
                            <output type="text" class="form-control" id="total_price" name="total_price" style="color:red;">
                                <c:out value="${calculateDeliveryPrice}"/>

                            </output
                        </div>
                    </div>
                </fieldset>

                <button type="submit" class="btn btn-success" value="submit"><fmt:message key="label.calculate_delivery_price"/></button>
                <input type="hidden" name="command" value="calculate_delivery_price">
            </form>

            <br>
            <div class="col-md-6">
                <div class="form-group">
                    <a href="https://www.google.com.ua/maps/" target="_blank"><fmt:message
                            key="label.google_distance_service_page"/></a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<tags:footer_default_locale/>
</html>



