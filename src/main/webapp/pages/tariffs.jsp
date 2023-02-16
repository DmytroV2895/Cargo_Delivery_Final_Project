<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.tariffs_page_title"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/tariffs.css">

</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>


<div class="container">
    <h4><fmt:message key="label.tariffs_page_title"/></h4>
    <div class="row col-md-10  custyle">
        <table class="table table-dark custab">
            <br>
            <thead>
            <h5><fmt:message key="label.order_name_document"/></h5>
            <tr>
                <th class="td-actions text-center"><fmt:message key="label.head_tariff_table_weight"/></th>
                <th class="td-actions text-center"><fmt:message key="label.head_tariff_table_price"/></th>
                <th class="td-actions text-center"><fmt:message key="label.head_tariff_table_add_inf"/></th>
            </tr>
            </thead>
            <tr>
                <td><fmt:message key="label.tariff_weight_document_up_to_2"/></td>
                <td class="td-actions text-center">45 ₴</td>
                <td><fmt:message key="label.acceptable_dimensions_document"/></td>
            </tr>
        </table>
        <br>

        <table class="table table-dark custab">
            <thead>
            <h5><fmt:message key="label.order_name_parcel"/></h5>
            <tr>
                <th class="td-actions text-center"><fmt:message key="label.head_tariff_table_weight"/></th>
                <th class="td-actions text-center"><fmt:message key="label.head_tariff_table_price"/></th>
                <th class="td-actions text-center"><fmt:message key="label.head_tariff_table_add_inf"/></th>
            </tr>
            </thead>
            <tr>
                <td><fmt:message key="label.tariff_weight_parcel_up_to_2_kg"/></td>
                <td class="td-actions text-center">60 ₴</td>
                <td></td>
            </tr>
            <tr>
                <td><fmt:message key="label.tariff_weight_parcel_2_10_kg"/></td>
                <td class="td-actions text-center">80 ₴</td>
                <td></td>
            </tr>
            <tr>
                <td><fmt:message key="label.tariff_weight_parcel_10_30_kg"/></td>
                <td class="td-actions text-center">120 ₴</td>
                <td></td>
            </tr>
        </table>
        <br>

        <table class="table table-dark custab">
            <thead>
            <h5><fmt:message key="label.order_name_cargo"/></h5>
            <tr>
                <th class="td-actions text-center"><fmt:message key="label.head_tariff_table_weight"/></th>
                <th class="td-actions text-center"><fmt:message key="label.head_tariff_table_price"/></th>
                <th class="td-actions text-center"><fmt:message key="label.head_tariff_table_add_inf"/></th>
            </tr>
            </thead>
            <tr>
                <td><fmt:message key="label.tariff_weight_cargo_30_250_kg"/></td>
                <td class="td-actions text-center">130 ₴</td>
                <td></td>
            </tr>
            <tr>
                <td><fmt:message key="label.tariff_weight_cargo_250_600_kg"/></td>
                <td class="td-actions text-center">150 ₴</td>
                <td></td>
            </tr>
            <tr>
                <td><fmt:message key="label.tariff_weight_cargo_600_1000_kg"/></td>
                <td class="td-actions text-center">180 ₴</td>
                <td><fmt:message key="label.acceptable_weight_cargo"/></td>
            </tr>
        </table>

        <table class="table table-dark custab" style="width: 200px">
            <thead>
            <h5><fmt:message key="label.delivery_directions"/></h5>
            <tr>
                <th class="td-actions text-center"><fmt:message key="label.city_name"/></th>

            </tr>
            </thead>
            <tr>
                <td class="text-center"><fmt:message key="label.delivery_city_sumy"/></td>
            </tr>
            <tr>
                <td class="text-center"><fmt:message key="label.delivery_city_lviv"/></td>
            </tr>
            <tr>
                <td class="text-center"><fmt:message key="label.delivery_city_charkiv"/></td>
            </tr>
            <tr>
                <td class="text-center"><fmt:message key="label.delivery_city_odessa"/></td>
            </tr>
        </table>
    </div>
</div>
</body>
<tags:footer_default_locale/>
</html>


