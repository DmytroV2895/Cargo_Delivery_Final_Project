<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="label.orders_title_page_user"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>
<c:import url="../guest/header.jsp"/>
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
    </div>


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
                                <input type="hidden" name="command"
                                       value="get_all_orders_pagination_command_user">
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </nav>
    </c:if>

    <tags:invoice_table/>

</div>
</body>
<tags:footer_default_locale/>
</html>






