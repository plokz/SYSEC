<%--
    Document   : preferencias
    Created on : 5/07/2015, 01:39:40 AM
    Author     : aya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%    String mensaje = (String) request.getAttribute("mensaje") != null ? (String) request.getAttribute("mensaje") : "";
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
        <%@include file="JSCSSjsp.jsp" %>

    </head>
    <body>
        <!-- header -->
        <%@include file="headerAdministrador.jsp" %>
        <input type="hidden" name="contexto" id="contexto" value="<%=context%>"/>

        <!-- Reporte post hechos-->
        <div id="agregarNuevoProfe" style="display: none">
            <div class="box container">
                <div class="col-xs-12">
                    <h3 class="text-center">Reporte de post realizados</h3></br>
                    <!--<div class="col-md-6 col-lg-6 col-md-offset-6 col-lg-offset-3">-->
                    <!-- Manda a ese servlet en esa opcion 4-->
                    <form method="post" action="<%=context%>/ServletPreferencias?opcion=4" class="form-inline">

                        <div class="input-group date">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            <input type="text"  name="fechaIP" id="fechaIP" class="form-control" placeholder="Fecha de inicio" required>
                        </div>
                        <div class="input-group date">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            <input type="text"  name="fechaFP" id="fechaFP" class="form-control" placeholder="Fecha de fin" required>
                        </div>

                        <div id="setGroupGrade">
                            <br>
                            <button class="btn btn-danger icon fa-file-pdf-o" type="submit">Generar</button>
                        </div>
                    </form>
                    <!--</div>-->
                </div>
            </div>
        </div>

        <!-- Reporte usuarios agregados -->
        <div id="agregarNuevoProfe" style="display: none">
            <div class="box container">
                <div class="col-xs-12">
                    <h3 class="text-center">Reporte de post realizados</h3></br>
                    <!--<div class="col-md-6 col-lg-6 col-md-offset-6 col-lg-offset-3">-->
                    <!-- Manda a ese servlet en esa opcion 5-->
                    <form method="post" action="<%=context%>/ServletPreferencias?opcion=5" class="form-inline">

                        <div class="input-group date">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            <input type="text"  name="fechaIU" id="fechaIU" class="form-control" placeholder="Fecha de inicio" required>
                        </div>
                        <div class="input-group date">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            <input type="text"  name="fechaFU" id="fechaFU" class="form-control" placeholder="Fecha de fin" required>
                        </div>

                        <div id="setGroupGrade">
                            <br>
                            <button class="btn btn-danger icon fa-file-pdf-o" type="submit">Generar</button>
                        </div>
                    </form>
                    <!--</div>-->
                </div>
            </div>
        </div>

        <!-- footer -->
        <%@include file="footer.jsp" %>
    </body>
</html>