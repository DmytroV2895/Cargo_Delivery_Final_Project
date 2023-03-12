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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="css/logIn.css">
    <title><fmt:message key="label.registration"/></title>
</head>

<body>
<c:import url="header.jsp"/>
<div class="container">
</div>
<main class="my-form">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header"><h4><fmt:message key="label.login"/></h4></div>
                    <div class="card-body">
                        <form method="POST" action="controller">
                            <div class="form-group row">
                                <label for="email" class="col-md-4 col-form-label text-md-right"><fmt:message
                                        key="label.email"/></label>
                                <div class="col-md-6">
                                    <input type="email" id="email" class="form-control" name="email" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="password" class="col-md-4 col-form-label text-md-right"><fmt:message
                                        key="label.password"/></label>
                                <div class="col-md-6">
                                    <input type="password" id="password" class="form-control" name="password" required>
                                </div>
                            </div>
                            <div class="col-md-6 offset-md-4">
                                <div class="form-group row col-md">
                                    <input type="hidden" name="command" value="log_in">
                                    <button type="submit" class="btn btn-primary"><fmt:message
                                            key="label.submit"/></button>
                                </div>
                                <div class="col-sm-10" style="color:red;">
                                    <h6>${message}</h6>
                                </div>
                            </div>
                        </form>
                        <div class="col-md-6 offset-md-4">
                        <div class="form-group row col-md">
                            <form action="controller" method="get">
                                <button type="submit" class="btn btn-primary"><fmt:message
                                        key="label.registration"/></button>
                                <input type="hidden" name="command" value="to_sign_up">
                            </form>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</main>
<tags:footer_default_locale/>
</body>
</html>

