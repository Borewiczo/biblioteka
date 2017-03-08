<%-- 
    Document   : saveSessionParam
    Created on : 2017-02-23, 21:55:06
    Author     : Krystian
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" crossorigin="anonymous"></script>
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="http://fontawesome.io/assets/font-awesome/css/font-awesome.css">
    </head>
    <body>
        <div class="container">
            <a class="btn btn-primary btn-lg" href="/RSI-NetBeans">Home</a>
            <h1>Zostales zalogowany!</h1>
            <div class="col-md-6">
                User: ${sessionScope.nameVimeo}<br>
                Code: ${sessionScope.codeVimeo}<br>
            </div>
            <div class="col-md-6">
                <img src=${sessionScope.pictureLinkVimeo} />
            </div>
        </div>

    <j2></j2>

</body>
</html>
