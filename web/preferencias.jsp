<%-- 
    Document   : preferencias
    Created on : 5/07/2015, 01:39:40 AM
    Author     : aya
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

        <link rel="stylesheet" href="<%=context%>/css/font-awesome.min.css">
        <link rel="stylesheet" href="<%=context%>/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=context%>/css/bootstrap-theme.min.css">
        <script src="<%=context%>/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="<%=context%>/css/dataTables.bootstrap.css" />
        <script src="<%=context%>/js/jquery-1.11.1.min.js"></script>
        <script src="<%=context%>/js/jquery.dataTables.min.js"></script>
        <script src="<%=context%>/js/dataTables.bootstrap.js"></script>
        <script>
            $(document).ready(function () {

                obtenerPost();

                $('#addP').click(function () {
                    $('#agregarPost').fadeIn();
                    $('#addP').hide();
                    $('#listaPost').hide();
                });

                $('#cancelarP').click(function () {
                    $('#agregarPost').hide();
                    $('#addP').fadeIn();
                    $('#listaPost').fadeIn();
                });
            });


            function obtenerPost() {
                var context = $('#contexto').val();

                $.ajax({
                    url: context + '/ServletPreferencias',
                    type: 'POST',
                    success: function (data) {
                        var tableBody = "<tbody id='cuerpotablaPricipal'>";

                        for (var i in data) {
                            tableBody += "<tr><td>" + data[i].preferencia.toUpperCase() + "</td>"
                            tableBody += "<td><a data-toggle='modal' data-id=" + data[i].idPreferencias + " title='Actualizar' class='open-Modal btn btn-primary glyphicon glyphicon-search' data-target='#ActualizarInfo'></a></td>"
                        }

                        tableBody += '</tbody>'
                        $('#tablaPricipal').append(tableBody);
                        $('#tablaPricipal').dataTable();
                    },
                    error: function () {
                        alert("Error de conexión");
                    }
                });

            }

            $(document).on("click", ".open-Modal", function () {
                var context = $('#contexto').val();
                var cadena = $(this).data('id');

                $.ajax({
                    url: context + '/ServletPreferencias',
                    type: 'POST',
                    data: {
                        "opcion": 1,
                        "id": cadena
                    },
                    success: function (data) {
                        console.log(data.preferencia.toUpperCase());
                        $('#nombre').val(data.idPreferencias);
                        $('#descripcion').val(data.preferencia.toUpperCase());
                    },
                    error: function () {
                        alert("Error de conexión");
                    }
                });
            });
        </script>
    </head>
    <body>
        <!-- header -->
        <%@include file="headerUsuario.jsp" %>
        <input type="hidden" name="contexto" id="contexto" value="<%=context%>"/>

        <!-- Banner -->
        <div id="agregarPost" style="display: none">
            <div class="box container">
                <div class="col-xs-12">
                    <h3 class="text-center">Publicar Post!</h3></br>
                    <div class="greybox col-md-5 section text-center">
                        <form id="enviar" method="post" action="<%=context%>/ServletPost?opcion=3" class="form-horizontal">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input id="descripcion" name="nombrePost" type="text" class="form-control" placeholder="Titulo de Post">                                        
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-money"></i></span>
                                <input id="presupuestoPost" name="presupuestoPost" type="text" class="form-control" placeholder="Presupuesto">
                            </div>
                            <br>
                            <div id="setGroupGrade">
                                <br>
                                <button data-toggle="modal" class="open-Modal btn btn-success" type="submit">Aceptar</button>
                                <button id="cancelarP" class="btn btn-danger" type="reset">Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Table -->
        <div id="listaPost" class="box container">
            <%if (mensaje.length() > 0) {%>
            <div class="alert alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <strong><%=mensaje%>!</strong>
            </div>
            <%}%>
            <div class="greybox col-xs-12">

                <button id="addP" class="btn btn-warning icon fa-newspaper-o" type="submit"> Publica un Post</button>

                <div class="col-md-12 section text-center">
                    <h3>Listado de Post's</h3>
                    <table id="tablaPricipal" class="table table-bordered table-striped table-hover" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th>Descripción</th>
                                <th></th>
                            </tr>
                        </thead>

                        <tfoot>
                            <tr>
                                <th>Descripción</th>
                                <th></th>
                            </tr>
                        </tfoot>

                        <tbody id="cuerpotablaPricipal"></tbody>

                    </table>

                </div>
            </div>
        </div>

        <div id="ActualizarInfo" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Datos de empresa</h4>
                    </div>
                    <form id="enviar" method="post" action="<%=context%>/ServletPost?opcion=4">                        
                        <div class="modal-body text-center form-inline">
                            <!-- contenido tabla consulta --> 
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input id="descripcion" name="descripcion" type="text" required class="form-control">                                        
                            </div>
                            <br><br>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Enviar</button>
                            <button type="reset" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <!-- footer -->
        <%@include file="footer.jsp" %>
    </body>
</html>