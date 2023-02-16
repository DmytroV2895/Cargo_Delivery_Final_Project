<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>


<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<style>
    .footer {
        position: relative;
        left: 0;
        bottom: 0;
        background-color: rgb(44, 41, 41);
        color: #eee;
        text-align: center;
        width: 100%;
        padding-right: 15px;
        padding-left: 15px;
        margin-right: auto;
        margin-left: auto;
        margin-top:100px;
    }
</style>

<body>
<div class="footer">
    <div class="container">
        <br>
        <div class="row">
            <div class="col-md text-center">
                <form action="controller" method="get">
                    <div class="dropdown">
                        <button class="btn btn-light dropdown-toggle" type="button"
                                id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
                                aria-expanded="false"><fmt:message key="label.language"/>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <button class="dropdown-item" type="submit" name="language" value="uk_UA">UA</button>
                            <button class="dropdown-item" type="submit" name="language" value="en_US">EN</button>
                            <input type="hidden" name="command" value="set_locale">
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-md text-center">
                <h6>EPAM Java Summer Program 2023 <br>
                    &#169; Cargo Transportation</h6>
            </div>
        </div>
        <br>
    </div>
</div>

</body>
</html>
