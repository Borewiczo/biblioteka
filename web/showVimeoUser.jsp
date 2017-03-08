<%-- 
    Document   : saveSessionParam
    Created on : 2017-02-23, 21:55:06
    Author     : Krystian
--%>

<%@ include file="header.jsp" %> 

            <a class="btn btn-primary btn-lg" href="/RSI-NetBeans">Home</a>
            <h1>Zostales zalogowany!</h1>
            <div class="col-md-6">
                User: ${sessionScope.nameVimeo}<br>
                Code: ${sessionScope.codeVimeo}<br>
            </div>
            <div class="col-md-6">
                <img src="${sessionScope.pictureLinkVimeo}" />
            </div>
            
<%@ include file="footer.jsp" %> 
