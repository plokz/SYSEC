<%-- 
    Document   : index
    Created on : 6/05/2015, 10:22:07 AM
    Author     : CSD-HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String mensaje = (String) request.getAttribute("texto") != null ? (String) request.getAttribute("texto") : "";
%>
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
        <script src="<%=context%>/js/jquery.min.js"></script>
        <script src="<%=context%>/js/jquery.dropotron.min.js"></script>
        <script src="<%=context%>/js/skel.min.js"></script>
        <script src="<%=context%>/js/skel-layers.min.js"></script>
        <script src="<%=context%>/js/init.js"></script>
        <noscript>
        <link rel="stylesheet" href="<%=context%>/css/skel.css" />
        <link rel="stylesheet" href="<%=context%>/css/style.css" />
        <link rel="stylesheet" href="<%=context%>/css/style-desktop.css" />
        </noscript>
        <!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
        <!-- Latest compiled and minified CSS -->
        <script src="<%=context%>/js/login.js"></script>
        <link rel="stylesheet" href="<%=context%>/css/login.css" />

        <link rel="stylesheet" href="<%=context%>/css/font-awesome.min.css">
        <link rel="stylesheet" href="<%=context%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=context%>/css/bootstrap-theme.min.css">
        <script src="<%=context%>/js/bootstrap.min.js"></script>

    </head>
    <body>
        <!-- header -->
        <!-- Header -->
        <div id="header-wrapper">
            <header id="header" class="container">

                <!-- Logo -->
                <div id="logo">
                    <img src="<%=context%>/images/escudo.png" alt="">
                </div>

            </header>
        </div>
        <!-- Login -->
        <div class="container">    
            <%if (mensaje.length() > 0) {%>
            <div class="alert alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong><%=mensaje%></strong>
            </div>
            <%}%>
            <div id="loginbox" class="mainbox col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3"> 

                <div class="panel panel-default" >
                    <div class="panel-heading">
                        <div class="panel-title text-center">Iniciar sesión</div>
                    </div>     

                    <div class="panel-body" >

                        <form action="ServletLogin" id="form" class="form-horizontal" method="POST">

                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input id="user" type="text" class="form-control" name="user" placeholder="Usuario" value="SysecAdm">                                        
                            </div>

                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                <input id="password" type="password" class="form-control" name="password" placeholder="Contraseña" value="SysecAdm">
                            </div>                                                                  

                            <div class="form-horizontal">
                                <a href="<%=context%>/Registro.jsp" class="col-sm-6 controls"> Regístrate</a>
                                <!-- Button --> 
                                <div class="col-sm-6 controls">
                                    <button type="submit" class="btn btn-primary pull-right"><i class="glyphicon glyphicon-log-in"></i> Ingresar</button>                          
                                </div>
                            </div>

                        </form>     

                    </div>                     
                </div>  
            </div>
        </div>

        <div id="particles"></div>


        <!-- footer -->
        <%@include file="footer.jsp" %>
    </body>
</html>
