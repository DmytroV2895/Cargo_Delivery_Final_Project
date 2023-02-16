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
    <title><fmt:message key="label.orders_title_page_user"/></title>

</head>


<body>
<c:import url="header.jsp"/>
<div class="container">
    <div class="title">
        <h3><fmt:message key="label.orders_list_user"/></h3>
    </div>
    <br>
    <div class="col-sm-10" style="color:red;">
        <h5>${message}</h5>
    </div>
    <br>

    <div class="row top-buffer">

        <div class="form-group col-sm-3">
            <form action="controller" method="get">
                <label><fmt:message key="label.filter_by_delivery_city_user_order_page"/></label>
                <select id="second_city" class="form-control" name="second_city" onchange='submit();'>
                    <option selected disabled><fmt:message key="label.choose_city"/></option>
                    <option value="SUMY"><fmt:message key="label.sumy"/></option>
                    <option value="LVIV"><fmt:message key="label.lviv"/></option>
                    <option value="CHARKIV"><fmt:message key="label.charkiv"/></option>
                    <option value="ODESSA"><fmt:message key="label.odessa"/></option>
                </select>
                <input type="hidden" name="command" value="to_sorted_user_orders_list_by_delivery_city">

            </form>
        </div>


        <div class="form-group col-sm-3">
            <form action="controller" method="get">
                <button type="submit" class="btn btn-primary waves-effect waves-light"><fmt:message
                        key="label.find_all_orders"/></button>
                <input type="hidden" name="command" value="to_orders_page_user">
            </form>
        </div>
    </div>



<c:if test="${invoice_list ne null}">
    <div class="row">
        <div class="table-responsive" style="width: 1100px">
            <table class="table">
                <thead>
                <tr>
                    <th class="td-actions text-center"><b><fmt:message
                            key="label.order_number_user_order_page"/></b>
                    </th>
                    <th class="td-actions text-center"><b><fmt:message key="label.order_name_user_order_page"/></b>
                    </th>
                    <th class="td-actions text-center"><b><fmt:message key="label.order_type_user_order_page"/></b>
                    </th>
                    <th class="td-actions text-center"><b><fmt:message
                            key="label.order_description_user_order_page"/></b></th>
                    <th class="td-actions text-center"><b><fmt:message
                            key="label.order_volume_user_order_page"/></b>
                    </th>
                    <th class="td-actions text-center"><b><fmt:message
                            key="label.order_weight_user_order_page"/></b>
                    </th>

                    <th class="td-actions text-center"><b><fmt:message key="label.first_city_user_order_page"/></b>
                    </th>
                    <th class="td-actions text-center"><b><fmt:message key="label.second_city_user_order_page"/></b>
                    </th>

                    <th class="td-actions text-center"><b><fmt:message
                            key="label.delivery_paid_status_user_order_page"/></b></th>
                    <th class="td-actions text-center" style="width: 150px"><b><fmt:message
                            key="label.order_delivery_date_user_order_page"/></b></th>
                    <th class="td-actions text-center"><b><fmt:message
                            key="label.order_status_user_order_page"/></b>
                    </th>
                    <th class="td-actions text-center"><b><fmt:message key="label.actions_user_order_page"/></b>
                    </th>
                </tr>
                </thead>
                </c:if>
                <tbody>
                <c:forEach var="data" items="${invoice_list}">
                    <tr>
                        <td class="td-actions text-center"><c:out value="${data.invoiceId}"/></td>
                        <td class="td-actions text-center"><c:out value="${data.order.getOrderName()}"/></td>
                        <td class="td-actions text-center">
                            <c:if test="${data.order.type eq 'DOCUMENT'}"><fmt:message
                                    key="label.order_type_document"/></c:if>
                            <c:if test="${data.order.type eq 'PARCEL'}"><fmt:message
                                    key="label.order_type_parcel"/></c:if>
                            <c:if test="${data.order.type eq 'CARGO'}"><fmt:message
                                    key="label.order_type_cargo"/></c:if>
                        </td>
                        <td class="td-actions text-center"><c:out value="${data.order.orderDescription}"/></td>
                        <td class="td-actions text-center"><c:out value="${data.order.volume}"/></td>
                        <td class="td-actions text-center"><c:out value="${data.order.weight}"/></td>
                        <td class="td-actions text-center">
                            <c:if test="${data.addressFirst.firstCity eq 'SUMY'}"><fmt:message
                                    key="label.sumy"/></c:if>
                            <c:if test="${data.addressFirst.firstCity eq 'LVIV'}"><fmt:message
                                    key="label.lviv"/></c:if>
                            <c:if test="${data.addressFirst.firstCity eq 'CHARKIV'}"><fmt:message
                                    key="label.charkiv"/></c:if>
                            <c:if test="${data.addressFirst.firstCity eq 'ODESSA'}"><fmt:message
                                    key="label.odessa"/></c:if>
                        </td>
                        <td class="td-actions text-center">
                            <c:if test="${data.addressSecond.secondCity eq 'SUMY'}"><fmt:message
                                    key="label.sumy"/></c:if>
                            <c:if test="${data.addressSecond.secondCity eq 'LVIV'}"><fmt:message
                                    key="label.lviv"/></c:if>
                            <c:if test="${data.addressSecond.secondCity eq 'CHARKIV'}"><fmt:message
                                    key="label.charkiv"/></c:if>
                            <c:if test="${data.addressSecond.secondCity eq 'ODESSA'}"><fmt:message
                                    key="label.odessa"/></c:if>
                        </td>
                        <td class="td-actions text-center">
                            <c:if test="${data.isDeliveryPaid eq 'true'}"><fmt:message
                                    key="label.delivery_paid_status_true_user_order_page"/></c:if>
                            <c:if test="${data.isDeliveryPaid eq 'false'}"><fmt:message
                                    key="label.delivery_paid_status_false_user_order_page"/></c:if>
                        </td>
                        <td class="td-actions text-center"><c:out value="${data.deliveryDate}"/></td>
                        <td class="td-actions text-center">
                            <c:if test="${data.orderStatus eq 'NOT_PROCESSED'}"><fmt:message
                                    key="label.order_not_processed"/></c:if>
                            <c:if test="${data.orderStatus eq 'IN_PROCESSING'}"><fmt:message
                                    key="label.order_in_processing"/></c:if>
                            <c:if test="${data.orderStatus eq 'ON_THE_WAY'}"><fmt:message
                                    key="label.order_on_the_way"/></c:if>
                            <c:if test="${data.orderStatus eq 'RECEIVED'}"><fmt:message
                                    key="label.order_received"/></c:if>
                        </td>
                        <td class="td-actions text-right">
                            <div class="form-group col-sm-2">
                            <c:if test="${data.orderStatus eq 'NOT_PROCESSED'}">
                                <form action="controller" method="get">
                                    <button
                                            class="btn btn-success btn-primary btn-sm" data-original-title=""
                                            title="">
                                        <i class="material-icons"><fmt:message
                                                key="label.order_actions_update_user_order_page"/></i>
                                        <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                                        <input type="hidden" name="command" value="to_order_update_page">
                                    </button>
                                </form>
                            </c:if>
                            <form action="controller" method="get">
                                <button type="submit" class="btn btn-primary btn-primary btn-sm"
                                        data-original-title=""
                                        title="">
                                    <i class="material-icons"><fmt:message
                                            key="label.order_actions_info_user_order_page"/></i>
                                </button>
                                <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                                <input type="hidden" name="command" value="to_order_info_page_user">
                            </form>

                            <c:if test="${data.isDeliveryPaid ne 'TRUE'}">
                                <form action="controller" method="get">
                                    <button type="submit" class="btn btn-success btn-primary btn-sm"
                                            data-original-title="" title="">
                                        <i class="material-icons"><fmt:message
                                                key="label.order_actions_delivery_payment_user_order_page"/></i>
                                    </button>
                                    <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                                    <input type="hidden" name="command" value="to_delivery_payment_page">
                                </form>
                            </c:if>

                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            <c:if test="${invoice_list ne null}">
                <nav aria-label="Page navigation example">
                    <c:if test="${number_of_pages > 1}">
                        <ul class="pagination">
                            <c:forEach begin="1" end="${number_of_pages}" var="i">
                                <li class="page-item">
                                    <form action="controller" method="get">
                                        <button type="submit" class="page-link" value="${i}">
                                            <c:out value="${i}"/>
                                        </button>
                                        <input type="hidden" name="start_from" value="${i}">
                                        <c:forEach var="data" items="${invoice_list}">
                                        <input type="hidden" name="id_second_address" value="${data.addressSecond.secondAddressId}">
                                        </c:forEach>
                                        <input type="hidden" name="command" value="sort_user_orders_list_by_delivery_city">
                                    </form>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </nav>
            </c:if>
            </table>
        </div>
    </div>
</div>
</body>
<tags:footer_default_locale/>
</html>






