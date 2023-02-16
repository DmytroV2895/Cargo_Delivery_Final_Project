<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title>Cargo delivery</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link rel="stylesheet" href="css/main.css">
</head>
<header>
    <c:import url="header.jsp"/>
</header>
<body>
<div class="container">
<img src="images/slide_main.jpg" style="width: 100%" alt="">
</div>
    <tags:footer_default_locale/>
</body>
</html>


