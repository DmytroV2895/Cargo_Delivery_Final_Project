<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.manager_personal_room"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <c:import url="../guest/header.jsp"/>
<body>

<div class="container">
    <div class="text-center">
        <div class="form-group"></div>
        <h3><fmt:message key="label.manager_personal_room"/></h3>
        <div class="form-group"></div>
        <hr/>
        <div class="text-left col-sm-10" style="color:red;">
            <h5>${message}</h5>
        </div>
        <form action="controller" method="get">
            <button type="submit" class="btn btn-primary waves-effect waves-light"><fmt:message
                    key="label.calculate_delivery_price"/></button>
            <input type="hidden" name="command" value="to_price_calculation_page">
        </form>
        <br>
        <form action="controller" method="get">
            <button type="submit" class="btn btn-primary waves-effect waves-light"><fmt:message
                    key="label.find_all_user_orders_manager_personal_page"/></button>
            <input type="hidden" name="command" value="to_order_page_manager">
        </form>
        <br>

    </div>
</div>
</body>
<tags:footer_default_locale/>
</html>

