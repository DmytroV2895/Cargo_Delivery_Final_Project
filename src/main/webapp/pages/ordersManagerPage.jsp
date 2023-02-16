<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.orders_title_user_page_manager"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">


</head>

<body>
<c:import url="header.jsp"/>
<div class="container">
    <div class="title">
        <h3><fmt:message key="label.orders_list_user_page_manager"/></h3>
    </div>
    <br>
    <div class="col-sm-10" style="color:red;">
        <h5>${message}</h5>
    </div>

    <form>
        <div class="row top-buffer">
            <div class="form-group col-md-3">
                <div class="form-inline">
                    <form action="controller" method="get">
                        <button type="submit" class="btn btn-primary btn-block "><fmt:message
                                key="label.report_by_days_manager_personal_page"/></button>
                        <input type="date" name="delivery_date" class="form-control" required/>
                        <input type="hidden" name="locale" value="${locale}"/>
                        <input type="hidden" name="command" value="get_delivery_report_by_days_pdf"/>
                    </form>
                </div>
            </div>

            <div class="form-group col-md-5">
                <div class="form-inline">
                    <form action="controller" method="get">
                        <button type="submit" class="btn btn-primary btn-block "><fmt:message
                                key="label.report_by_destination_manager_personal_page"/></button>
                        <select id="first_city" class="form-control" name="first_city" required>
                            <option selected disabled><fmt:message key="label.choose_city"/></option>
                            <option value="SUMY"><fmt:message key="label.sumy"/></option>
                            <option value="LVIV"><fmt:message key="label.lviv"/></option>
                            <option value="CHARKIV"><fmt:message key="label.charkiv"/></option>
                            <option value="ODESSA"><fmt:message key="label.odessa"/></option>
                        </select>
                        <select id="second_city" class="form-control" name="second_city" required>
                            <option selected disabled><fmt:message key="label.choose_city"/></option>
                            <option value="SUMY"><fmt:message key="label.sumy"/></option>
                            <option value="LVIV"><fmt:message key="label.lviv"/></option>
                            <option value="CHARKIV"><fmt:message key="label.charkiv"/></option>
                            <option value="ODESSA"><fmt:message key="label.odessa"/></option>
                        </select>
                        <input type="hidden" name="locale" value="${locale}">
                        <input type="hidden" name="command" value="get_delivery_report_by_destination_pdf">
                    </form>
                </div>
            </div>
        </div>
    </form>
    <br>
    <div class="table-responsive" style="width: 100%">
        <table class="table">
            <thead>
            <tr>
                <th class="td-actions text-center"><b><fmt:message key="label.invoice_number_manager_order_page"/></b>
                </th>
                <th class="td-actions text-center"><b><fmt:message key="label.user_name_manager_order_page"/></b></th>
                <th class="td-actions text-center"><b><fmt:message key="label.order_name_manager_order_page"/></b></th>
                <th class="td-actions text-center"><b><fmt:message key="label.order_type_manager_order_page"/></b></th>
                <th class="td-actions text-center"><b><fmt:message key="label.first_city_user_order_page"/></b></th>
                <th class="td-actions text-center"><b><fmt:message key="label.second_city_user_order_page"/></b></th>
                <th class="td-actions text-center"><b><fmt:message key="label.delivery_price_manager_order_page"/></b>
                </th>
                <th class="td-actions text-center" style="width: 150px"><b><fmt:message key="label.delivery_date_manager_order_page"/></b>
                </th>
                <th class="td-actions text-center"><b><fmt:message
                        key="label.delivery_paid_status_manager_order_page"/></b></th>
                <th class="td-actions text-center"><b><fmt:message
                        key="label.invoice_creation_date_manager_order_page"/></b></th>
                <th class="td-actions text-center"><b><fmt:message key="label.actions_user_manager_order_page"/></b>
                </th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="data" items="${invoice_list_manager}">
                <tr>
                    <td class="td-actions text-center"><c:out value="${data.invoiceId}"/></td>
                    <td class="td-actions text-center"><c:out value="${data.user.name} ${data.user.surname}"/></td>
                    <td class="td-actions text-center"><c:out value="${data.order.orderName}"/></td>
                    <td class="td-actions text-center">
                        <c:if test="${data.order.type eq 'DOCUMENT'}"><fmt:message
                                key="label.order_type_document"/></c:if>
                        <c:if test="${data.order.type eq 'PARCEL'}"><fmt:message
                                key="label.order_type_parcel"/></c:if>
                        <c:if test="${data.order.type eq 'CARGO'}"><fmt:message
                                key="label.order_type_cargo"/></c:if></td>
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
                    <td class="td-actions text-center"><c:out value="${data.deliveryPrice}"/></td>
                    <td class="td-actions text-center"><c:out value="${data.deliveryDate}"/></td>
                    <td class="td-actions text-center">
                        <c:if test="${data.isDeliveryPaid eq 'true'}"><fmt:message
                                key="label.delivery_paid_status_true_manager_order_page"/></c:if>
                        <c:if test="${data.isDeliveryPaid eq 'false'}"><fmt:message
                                key="label.delivery_paid_status_false_manager_order_page"/></c:if></td>
                    <td class="td-actions text-center"><c:out value="${data.creationDateTime}"/></td>
                    <td class="td-actions text-right">
                        <form action="controller" method="get">
                            <button type="submit" class="btn btn-primary btn-primary btn-sm" data-original-title=""
                                    title="">
                                <i class="material-icons"><fmt:message
                                        key="label.order_actions_info_user_order_page"/></i>
                            </button>
                            <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                            <input type="hidden" name="command" value="to_order_info_page_guest">
                        </form>

                        <form action="controller" method="get">
                            <button type="submit" class="btn btn-danger btn-primary btn-sm" data-original-title=""
                                    title="">
                                <i class="material-icons"><fmt:message
                                        key="label.order_actions_update_manager_order_page"/></i>
                            </button>
                            <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                            <input type="hidden" name="command" value="to_order_update_page_manager">
                        </form>

                        <c:if test="${data.isDeliveryPaid eq 'FALSE'}">
                            <form action="controller" method="get">
                                <button type="submit" class="btn btn-success btn-sm" data-original-title="" title="">
                                    <i class="material-icons"><fmt:message
                                            key="label.order_actions_send_bill_manager_order_page"/></i>
                                </button>
                                <input type="hidden" name="id_invoice" value="${data.invoiceId}">
                                <input type="hidden" name="command" value="to_bill_page">
                            </form>
                        </c:if>

                    </td>
                </tr>
            </c:forEach>
            </tbody>

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
                                    <input type="hidden" name="command"
                                           value="get_all_orders_pagination_command_manager">
                                </form>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </nav>
        </table>
    </div>
</div>
</div>
</body>
<tags:footer_default_locale/>
</html>






