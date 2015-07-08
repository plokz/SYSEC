<%-- 
    Document   : Registro
    Created on : 31/05/2015, 05:09:08 PM
    Author     : Eduardo
--%>

<%@page import="Beans.EstadoBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="Daos.DaoRegistro"%>
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
        <%@include file="JSCSSjsp.jsp" %>
        
        <script type="text/javascript">
            $(document).ready(function() {
                cargaEstados();
                cargaPreferencias();
            });
            //Carga Estados
            function cargaEstados() {
                $.ajaxSetup({
                    async: false
                });
                var context = $('#contexto').val();
                $.getJSON(context + '/ServletRegistro?opcion=1', function(data) {
                    var opciones = "";
                    for (BuscadorBean in data.parametros) {
                        opciones += "<option value='" + data.parametros[BuscadorBean].nombre + "'>" + data.parametros[BuscadorBean].nombre + "</option>";
                    }
                    $('#lstEstados').html(opciones);
                });

                $.ajaxSetup({
                    async: true
                });
            }

            //Carga Preferencias
            function cargaPreferencias() {
                $.ajaxSetup({
                    async: false
                });
                var context = $('#contexto').val();
                $.getJSON(context + '/ServletRegistro?opcion=4', function(data) {
                    for (var i = 0; i < data.length; i++) {
                        var checkBox = "<input type='checkbox' data-idPreferencias='" + data[i].idPreferencias + "' name='checkbox' value='" + data[i].idPreferencias + "'/>" + data[i].preferencia + "<br/>";
                        $(checkBox).appendTo('#modifiersDiv');
                    }
                    $('#addModifiers').modal('show');
                });

                $.ajaxSetup({
                    async: true
                });
            }

            function existeCorreo() {
                $.ajaxSetup({
                    async: false
                });
                var context = $('#contexto').val();
                var textoCorreo = $('#email').val();

                $.getJSON(context + '/ServletRegistro?opcion=2&email=' + textoCorreo, function(data) {
                    for (BuscadorBean in data.parametros) {
                        if (data.parametros[BuscadorBean].estado === "false") {
                            $('#user').val(data.parametros[BuscadorBean].usuarioCreado);
                            $("#password").removeAttr("disabled");
                            $("#registro").removeAttr("disabled");
                        } else {
                            $('#user').val("");
                            $("#password").attr("disabled", "disabled");
                            $("#registro").attr("disabled", "disabled");
                            alert("El correo ya esta en uso");
                        }
                    }
                });

                $.ajaxSetup({
                    async: true
                });
            }
        </script>

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
        <input type="hidden" name="contexto" id="contexto" value="<%=context%>"/>
        <!-- Login -->
        <div class="container"> 

            <div id="loginbox" class="mainbox col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3"> 

                <div class="panel panel-default" >
                    <div class="panel-heading">
                        <div class="panel-title text-center">Regístrate</div>
                    </div>     

                    <div class="panel-body" >

                        <form action="<%=context%>/ServletRegistro?opcion=3" id="form" class="form-horizontal" method="post">

                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-child"></i></span>
                                <input id="nombre" name="nombre" type="text" class="form-control" placeholder="Nombre de la empresa" required>                                        
                            </div>
                            <br>
                            <div class="form-inline">
                                <div class="input-group col-sm-3 controls">
                                    <div class="input-group col-md-10">
                                        <span class="input-group-addon"><span class="fa fa-street-view"></span></span>
                                        <input id="estado" name="estado" type="text"  class="form-control" list="lstEstados"
                                               autocomplete="on" required placeholder="Estado" style="width: 300px" onChange="estadoEnLista();">
                                    </div>
                                    <datalist id="lstEstados"></datalist>
                                </div>

                                <div class="input-group col-sm-3 controls">
                                    <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
                                    <input id="cp" name="cp" type="text" class="form-control" placeholder="C.P"  required pattern="[0-9]*">
                                </div>
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-paper-plane-o"></i></span>
                                <input id="colonia" name="colonia" type="text" class="form-control" placeholder="Colonia" required>
                            </div>
                            <br>
                            <div class="input-group col-sm-5">
                                <span class="input-group-addon"><i class="fa fa-barcode"></i></span>
                                <input id="RFC" name="RFC" type="text" class="form-control" placeholder="R.F.C" required>
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                                <input id="email" name="email" type="email" class="form-control" placeholder="Correo electronico" 
                                       pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$" required onKeyUp="existeCorreo()" onChange="existeCorreo()">
                            </div>
                            <br>
                            <div class="form-inline"> 
                                <div class="input-group col-sm-6">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input id="user" name="user" type="text" class="form-control" placeholder="Usuario" readonly="readonly" required>
                                </div>

                                <div class="input-group col-sm-5">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                    <input id="password" name="password" type="password" class="form-control" placeholder="Contraseña" disabled required>
                                </div>
                            </div>
                            <br>
                            <div id="modifiersDiv"></div>
                            <br>
                            <div class="form-horizontal">
                                <a href="<%=context%>/index.jsp" class="col-sm-6 controls"> Cancelar</a>
                                <!-- Button --> 
                                <div class="col-sm-6 controls">
                                    <button id="registro" name="registro" disabled type="submit" class="btn btn-success pull-right"><i class="glyphicon glyphicon-log-in"></i> Aceptar</button>                          
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
