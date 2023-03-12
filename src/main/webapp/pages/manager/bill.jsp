<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="label.payment_bill_title_bill_page"/></title>
</head>
<c:import url="../guest/header.jsp"/>
<body>
<div class="container">
    <div class="row">
        <c:forEach var="data" items="${invoice_by_id}">
            <div class="well col-xs-10 col-sm-10 col-md-6 col-xs-offset-1 col-sm-offset-1 col-md-offset-3">
                <div class="row">
                    <div class="col-xs-6 col-sm-6 col-md-6">

                        <address>
                            <strong><fmt:message key="label.user_name_bill_page"/></strong>
                            <br>
                            <c:out value="${data.user.name}"/>
                            <br>
                            <strong><fmt:message key="label.user_surname_bill_page"/></strong>
                            <br>
                            <c:out value="${data.user.surname}"/>
                            <br>
                            <strong><fmt:message key="label.user_email_bill_page"/></strong>
                            <br>
                            <c:out value="${data.user.email}"/>
                            <br>
                            <strong><fmt:message key="label.user_phone_bill_page"/></strong>
                            <br>
                            <c:out value="${data.user.phone}"/>
                            <br>
                        </address>
                    </div>
                </div>
                <div class="row">
                    <div class="text-center">
                        <h3><fmt:message key="label.user_payment_bill_bill_page"/></h3>
                    </div>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th><fmt:message key="label.order_name_bill_page"/></th>
                            <th><fmt:message key="label.order_description_bill_page"/></th>
                            <th><fmt:message key="label.order_number_bill_page"/></th>
                            <th class="text-center"><fmt:message key="label.order_price_bill_page"/></th>
                            <th class="text-center"><fmt:message key="label.delivery_price_bill_page"/></th>
                            <th class="text-center"><fmt:message key="label.order_total_price_bill_page"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="col-md-4"><em><c:out value="${data.order.orderName}"/></em></td>
                            <td class="col-md-5"><em><c:out value="${data.order.orderDescription}"/></em></td>
                            <td class="col-md-2" style="text-align: center"><c:out value="${data.invoiceId}"/></td>
                            <td class="col-md-1 text-center"><c:out value="${data.order.price}"/></td>
                            <td class="col-md-1 text-center"><c:out value="${data.deliveryPrice}"/></td>
                            <td class="col-md-1 text-center"><c:out value="${data.totalPrice}"/></td>
                        </tr>
                        </tbody>


                    </table>
                    <form action="controller" method="post">
                        <button type="submit" class="btn btn-primary btn-lg btn-block"><fmt:message
                                key="label.create_bill_user_bill_page"/>
                        </button>
                        <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                        <input type="hidden" name="name" value="${data.user.name}">
                        <input type="hidden" name="surname" value="${data.user.surname}">
                        <input type="hidden" name="phone" value="${data.user.phone}">
                        <input type="hidden" name="email" value="${data.user.email}">
                        <input type="hidden" name="locale" value="${locale}">
                        <input type="hidden" name="type_of_report" value="${"PaymentBill"}">
                        <input type="hidden" name="command" value="create_payment_bill_pdf">
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
<tags:footer_locale_invoice_by_id/>
</html>

