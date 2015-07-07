<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% String context = request.getContextPath();%>
    <head>
        <title>SYSEC</title>
        <link rel="shortcut icon" href="<%=context%>/images/icono.ico">
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <meta name="description" content="" />
        <meta name="keywords" content="" />
        <!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
        <%@include file="JSCSSjsp.jsp" %>

    </head>
    <body>
        <!-- header -->
        <%@include file="headerAdministrador.jsp" %>

        <!-- Login -->
        <div class="container">  
            <div id="loginbox" class="mainbox col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3"> 

            </div>
        </div>

        <!-- footer -->
        <%@include file="footer.jsp" %>
    </body>
</html>
