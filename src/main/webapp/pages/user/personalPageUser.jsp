<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.user_personal_room"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<header>
    <c:import url="../guest/header.jsp"/>
</header>

<div class="container">

    <div class="text-center">
        <h3><fmt:message key="label.user_personal_room"/></h3>
        <hr/>
        <div class="col-sm-10" style="color:red;">
            <h5>${message}</h5>
        </div>
        <div class="control-group text-left">
            <label class="control-label"><b><fmt:message key="label.user_personal_page_name"/> </b>${user.name}</label>
        </div>
        <div class="control-group text-left">
            <label class="control-label"><b><fmt:message key="label.user_personal_page_surname"/> </b>${user.surname}
            </label>
        </div>
        <div class="control-group text-left">
            <label class="control-label"><b><fmt:message key="label.user_personal_page_email"/> </b>${user.email}
            </label>
        </div>
        <div class="control-group text-left">
            <label class="control-label"><b><fmt:message key="label.user_personal_page_phone_number"/> </b>${user.phone}
            </label>
        </div>
        <div class="control-group text-left">
            <b><fmt:message key="label.user_personal_page_user_role"/></b> <fmt:message
                key="label.user_personal_page_user_role_user"/>
        </div>
        <br>
        <div class="form-inline">
            <form action="controller" method="post">
                <button type="submit" class="btn btn-primary btn-block "><fmt:message
                        key="label.top_up_the_balance"/></button>
                <input type="text" name="user_account_add_money" class="form-control" required
                       pattern="^(0|[1-9]\d*)([.,]\d+)?"
                       title="<fmt:message key="label.input_only_positive_numbers"/>">
                <input type="hidden" name="command" value="top_up_the_balance">
            </form>
        </div>

        <div class="form-group">
            <hr/>

            <form action="controller" method="get">
                <button type="submit" class="btn btn-primary waves-effect waves-light"><fmt:message
                        key="label.calculate_delivery_price"/></button>
                <input type="hidden" name="command" value="to_price_calculation_page">
            </form>
            <br>

            <form action="controller" method="get">
                <button type="submit" class="btn btn-primary waves-effect waves-light"><fmt:message
                        key="label.create_order"/></button>
                <input type="hidden" name="command" value="to_create_order_page">
            </form>
            <br>
            <form action="controller" method="get">
                <button type="submit" class="btn btn-primary waves-effect waves-light"><fmt:message
                        key="label.find_all_orders"/></button>
                <input type="hidden" name="command" value="to_orders_page_user">
            </form>

        </div>
    </div>
</div>
<tags:footer_default_locale/>
</body>
</html>

