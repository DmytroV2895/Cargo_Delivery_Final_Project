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


    <title>Registration</title>
    <link rel="stylesheet" href="css/registration.css">
</head>

<body>
<c:import url="header.jsp"/>
<div class="my-form">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><h4><fmt:message key="label.registration"/></h4></div>
                <form class="registration" method="post" action="controller">

                    <div class="form-group row">
                        <label for="name" class="col-md-4 col-form-label text-md-right"><fmt:message
                                key="label.name"/></label>
                        <div class="col-md-6 ">
                            <input type="text" id="name" class="form-control input js-input-name" name="name">

                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="surname" class="col-md-4 col-form-label text-md-right"><fmt:message
                                key="label.surname"/></label>
                        <div class="col-md-6">
                            <input type="text" id="surname" class="form-control input js-input-surname" name="surname"
                                   required
                                   pattern="[A-ZА-ЯІ][a-z-а-яії]{1,39}"
                                   title="User surname must contain only letters and start with uppercase letter">
                        </div>
                    </div>

                    <div class="js-input-email form-group row">
                        <label for="email" class="col-md-4 col-form-label text-md-right"><fmt:message
                                key="label.email"/></label>
                        <div class="col-md-6">
                            <input type="email" id="email" class="form-control input js-input-email" name="email"
                                   required pattern="[a-z0-9]+@[a-z]+\.[a-z]{2,3}">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="phone" class="col-md-4 col-form-label text-md-right"><fmt:message
                                key="label.phone_number"/></label>
                        <div class="col-md-6">
                            <input type="tel" id="phone" class="form-control input js-input-phone" name="phone"
                                   placeholder="+380123651296" required pattern="^\+380\d{3}\d{2}\d{2}\d{2}$">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="password" class="col-md-4 col-form-label text-md-right"><fmt:message
                                key="label.password"/></label>
                        <div class="col-md-6">
                            <input type="password" id="password" class="form-control input js-input-password"
                                   name="password"
                                   required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                   title="Password must contain at least one number and one uppercase and lowercase letter,
                                                                                               and at least 8 or more characters"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="confirmed_password" class="col-md-4 col-form-label text-md-right"><fmt:message
                                key="label.confirm_password"/></label>
                        <div class="col-md-6">
                            <input type="password" id="confirmed_password" class="form-control input js-input-password"
                                   name="confirmed_password" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                   title="Confirmation password does not match">
                        </div>
                    </div>
                    <div class="col-md-6 offset-md-4">
                        <div class="col-md-6">
                            <input type="hidden" name="command" value="sign_up">
                            <button type="submit" class="btn btn-primary"><fmt:message key="label.sign_up"/></button>
                        </div>
                    </div>
                    <div class="col-sm-10" style="color:red;">
                        <h6>${message}</h6>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<tags:footer_default_locale/>
</body>
</html>

















