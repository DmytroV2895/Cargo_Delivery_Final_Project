<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.delivery_service"/></title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
</head>

<body>
    <c:import url="header.jsp"/>
<div class="container">
    <h5><fmt:message key="label.delivery_service"/></h5>
    <br>
    <div class="col-sm-10" style="color:red;">
        <h5>${message}</h5>
    </div>
    <br>
    <div class="row">
        <div class="col-md-5">
            <div class="form-inline">
                <form action="controller" method="get">
                    <button type="submit" class="btn btn-primary btn-block "><fmt:message
                            key="label.find_order_by_invoice_id_service_page"/></button>
                    <label>
                        <input type="text" name="id_invoice" class="form-control"
                               placeholder="<fmt:message key="label.enter_invoice_id_service_page"/>" required pattern="^[0-9]*$"/>
                    </label>
                    <input type="hidden" name="id_invoice" value="${invoiceId}">
                    <input type="hidden" name="command" value="to_order_info_page_guest">
                </form>
            </div>
            <br>
        </div>
    </div>

    <div class="row">
        <div class="col-md-5">
            <div class="form-inline">
                <form action="controller" method="get">
                    <input type="submit" value="<fmt:message key="label.calculate_delivery_price_service_page"/>"
                           class="btn btn-primary"/>
                    <input type="hidden" name="command" value="to_price_calculation_page">
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<tags:footer_default_locale/>
</html>


