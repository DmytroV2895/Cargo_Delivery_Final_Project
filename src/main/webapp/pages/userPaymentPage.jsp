<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.delivery_payment"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<c:import url="header.jsp"/>
<div class="container">
    <div class="row">
        <div class="span12">
            <form class="form-horizontal span6">
                <fieldset>
                    <legend><fmt:message key="label.delivery_service_payment"/></legend>
                    <br>
                    <c:forEach var="data" items="${invoice_by_id}">

                        <div class="control-group">
                            <label class="control-label"><fmt:message key="label.delivery_payer_name"/></label>
                            <div class="controls">
                                <output type="text" class="form-control" id="name"
                                        name="name">${data.user.name} ${data.user.surname}</output>
                            </div>
                        </div>
                        <br>

                        <div class="control-group">
                            <label class="control-label"><fmt:message key="label.order_number_payment"/></label>
                            <div class="controls">
                                <output type="text" class="form-control" id="id_invoice"
                                        name="id_invoice">${data.invoiceId}</output>
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label"><fmt:message key="label.order_name_payment"/></label>
                            <div class="controls">
                                <output type="text" class="form-control" id="order_name"
                                        name="order_name">${data.order.orderName}</output>
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label"><fmt:message
                                    key="label.order_description_payment"/></label>
                            <div class="controls">
                                <output type="text" class="form-control" id="order_description"
                                        name="order_description">${data.order.orderDescription}</output>
                            </div>
                        </div>
                        <br>

                        <div class="control-group">
                            <label class="control-label"><fmt:message key="label.company_account_number"/></label>
                            <div class="controls">
                                <output type="text" class="form-control">UA 65 258947 00000 58963512478936</output>
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label"><fmt:message key="label.service_type"/></label>
                            <div class="controls">
                                <output type="text" class="form-control"><b><fmt:message
                                        key="label.service_type_delivery"/></b></output>
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label"><fmt:message key="label.order_price_payment_page"/></label>
                            <div class="controls">
                                <output type="text" class="form-control" id="order_price"
                                        name="order_price">${data.order.price}</output>
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label"><fmt:message key="label.delivery_sum"/></label>
                            <div class="controls">
                                <output type="text" class="form-control" id="delivery_price"
                                        name="delivery_price">${data.deliveryPrice}</output>
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label"><fmt:message key="label.total_sum"/></label>
                            <div class="controls">
                                <output type="text" class="form-control" id="total_price"
                                        name="total_price">${data.totalPrice}</output>
                            </div>
                        </div>
                        <br>
                        <c:if test="${data.user.paymentAccount ge data.totalPrice}">
                            <form action="controller" method="post">
                                <div class="form">
                                    <button type="submit" class="btn btn-primary"><fmt:message
                                            key="label.paid"/></button>
                                    <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                                    <input type="hidden" name="total_price" value="${data.totalPrice}">
                                    <input type="hidden" name="command" value="delivery_payment">
                                </div>
                            </form>
                        </c:if>
                        <br>
                            <form action="controller" method="get">
                                <div class="form">
                                    <button type="submit" class="btn btn-success"><fmt:message key="label.top_up_the_balance"/></button>
                                    <input type="hidden" name="command" value="to_personal_page">
                                </div>
                            </form>
                    </c:forEach>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
<tags:footer_locale_invoice_by_id/>
</html>
