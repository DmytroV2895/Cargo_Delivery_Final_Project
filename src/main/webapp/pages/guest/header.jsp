<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
            crossorigin="anonymous"></script>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
          crossorigin="anonymous">

    <title>Cargo Transportation</title>
</head>
<body>

<div class="container d-flex justify-content-between align-items-center">
    <div class="logo"><h3><a>CARGO<br>TRANSPORTATION</a></h3>
    </div>
    <div class="container-fluid">
        <div class="btn-toolbar justify-content-between" role="toolbar"
             aria-label="Toolbar with button groups">
            <div class="btn-group" role="group" aria-label="First group">

                <form action="controller" method="get">
                    <input type="submit" value="<fmt:message key="label.home"/>" class="btn btn-light"/>
                    <input type="hidden" name="command" value="to_main"/>
                </form>

                <form action="controller" method="get">
                    <input type="submit" value="<fmt:message key="label.delivery_service"/>" class="btn btn-light"/>
                    <input type="hidden" name="command" value="to_delivery_service_page"/>
                </form>
                <form action="controller" method="get">
                    <input type="submit" value="<fmt:message key="label.tariffs"/>" class="btn btn-light"/>
                    <input type="hidden" name="command" value="to_tariffs_page"/>
                </form>

                <c:if test="${user.getRole().toString() ne null}">
                    <form action="controller" method="get">
                        <input type="submit" value="<fmt:message key="label.personal_page"/>" class="btn btn-light"/>
                        <input type="hidden" name="command" value="to_personal_page">
                    </form>
                </c:if>
            </div>

            <custom:helloTag role="${user.role}"/>

            <c:if test="${user.getRole().toString() eq 'USER'}">
                <c:forEach var="user" items="${user_by_id}">
                    <div class="col-sm-2">
                        <b><label><fmt:message key="label.balance"/></label></b><br>
                        <label><c:out value="${user.paymentAccount}"/></label>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${user.getRole().toString() eq null}">

                <form action="controller" method="get">
                    <input type="submit" value="<fmt:message key="label.login"/>" class="btn btn-primary"/>
                    <input type="hidden" name="command" value="to_sign_in">
                </form>
            </c:if>
            <c:if test="${user.getRole().toString() ne null}">

                <div class="col-md-2">
                    <form action="controller" method="get">
                        <input type="submit" value="<fmt:message key="label.logout"/>" class="btn btn-primary"/>
                        <input type="hidden" name="command" value="log_out">
                    </form>
                </div>
            </c:if>

        </div>
    </div>
</div>
</body>
<br>
</html>

