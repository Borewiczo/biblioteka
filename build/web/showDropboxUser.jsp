<%-- 
    Document   : showDropboxUser
    Created on : 2017-02-25, 21:08:18
    Author     : Konrad
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %> 




   <h1>${sessionScope.msgDropbox}</h1>
   
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Parametr</th>
                <th>Wartość</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Imię</td>
                <td>${sessionScope.nameDropbox}</td>
            </tr>
            <tr>
                <td>Nazwisko</td>
                <td>${sessionScope.surnameDropbox}</td>
            </tr>
            <tr>
                <td>E-mail</td>
                <td>${sessionScope.emailDropbox}</td>
            </tr>
            <tr>
                <td>Kraj</td>
                <td>${sessionScope.countryDropbox}</td>
            </tr>
        </tbody>
    </table>
        
<a class="btn btn-primary btn-lg" href="./">POWRÓT</a>
                
                
<%@ include file="footer.jsp" %> 
