<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%    String mensaje = (String) request.getAttribute("texto") != null ? (String) request.getAttribute("texto") : "";
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

        <link rel="stylesheet" href="<%=context%>/css/font-awesome.min.css">
        <link rel="stylesheet" href="<%=context%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=context%>/css/bootstrap-theme.min.css">
        <script src="<%=context%>/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="<%=context%>/css/dataTables.bootstrap.css" />
        <script src="<%=context%>/js/jquery-1.11.1.min.js"></script>
        <script src="<%=context%>/js/jquery.dataTables.min.js"></script>
        <script src="<%=context%>/js/dataTables.bootstrap.js"></script>

        <script>
            $(document).ready(function() {
                obtenerDatosUser();
                cargaEstados();
            });


            function obtenerDatosUser() {
                var context = $('#contexto').val();

                $.ajax({
                    url: context + '/ServletUsuario?opcion=1',
                    type: 'POST',
                    success: function(data) {
                        $('#nombre').val(data.nombre.toUpperCase());
                        $('#estado').val(data.estadoBean.nombre.toUpperCase());
                        $('#cp').val(data.CP.toUpperCase());
                        $('#colonia').val(data.colonia.toUpperCase());
                        $('#RFC').val(data.rfc.toUpperCase());
                        $('#email').val(data.email.toUpperCase());
                        $('#user').val(data.usuario.toUpperCase());
                        $('#password').val(data.password.toUpperCase());

                        var checkBox;
                        for (BuscadorBean in data.preferenciasBean) {
                            if (data.preferenciasBean[BuscadorBean].tiene === true) {
                                checkBox = "<input type='checkbox' checked data-idPreferencias='" + data.preferenciasBean[BuscadorBean].idPreferencias + "' name='checkbox' value='" + data.preferenciasBean[BuscadorBean].idPreferencias + "'/>" + data.preferenciasBean[BuscadorBean].preferencia + "<br/>";
                            } else {
                                checkBox = "<input type='checkbox' data-idPreferencias='" + data.preferenciasBean[BuscadorBean].idPreferencias + "' name='checkbox' value='" + data.preferenciasBean[BuscadorBean].idPreferencias + "'/>" + data.preferenciasBean[BuscadorBean].preferencia + "<br/>";
                            }
                            $(checkBox).appendTo('#modifiersDiv');
                        }
                        $('#addModifiers').modal('show');

                    },
                    error: function() {
                        alert("Error de conexión");
                    }
                });
            }

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
                            $("#password").removeAttr("readonly");
                        } else {
                            $('#user').val("");
                            $("#password").attr("readonly", "readonly");
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
        <%@include file="headerUsuario.jsp" %>
        <input type="hidden" name="contexto" id="contexto" value="<%=context%>"/>

        <!-- Actualiza informacion -->
        <div class="container"> 

            <div id="loginbox" class="mainbox col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3"> 

                <div class="panel panel-default" >
                    <div class="panel-heading">
                        <div class="panel-title text-center">Actualizar Información</div>
                    </div>     

                    <div class="panel-body" >

                        <form action="<%=context%>/ServletUsuario?opcion=2" id="form" class="form-horizontal" method="post">

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
                                    <input id="password" name="password" type="password" class="form-control" placeholder="Contraseña" readonly="" required>
                                </div>
                            </div>
                            <br>
                            <div id="modifiersDiv"></div>
                            <br>
                            <div class="form-horizontal">
                                <!-- Button --> 
                                <div class="col-sm-6 controls">
                                    <button id="registro" name="registro" type="submit" class="btn btn-success pull-right">
                                        Aceptar
                                    </button>                          
                                </div>
                                <a href="<%=context%>/principalUsuarios.jsp" class="col-sm-6 controls"> 
                                    <button type="button" class="btn btn-danger" data-dismiss="modal"> Cancelar</button>
                                </a>
                            </div>

                        </form>     

                    </div>                     
                </div>  
            </div>
        </div>


        <!-- footer -->
        <%@include file="footer.jsp" %>
    </body>
</html>