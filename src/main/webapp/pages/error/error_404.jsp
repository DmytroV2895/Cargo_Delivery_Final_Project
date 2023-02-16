

<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" language="java"%>


<!DOCTYPE html>
<html lang="en">


<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Oops, Error!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>


<body>
<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h1 class="display-1 fw-bold">404</h1>
        <p class="fs-3"> <span class="text-danger">Opps!</span> Something wrong!</p>

        <form action="controller" method="get">
            <input type="submit" value="Go Home Page" class="btn btn-primary"/>
            <input type="hidden" name="command" value="to_main">
        </form>
    </div>
</div>
</body>


</html>


