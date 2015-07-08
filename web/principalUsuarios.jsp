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
        <%@include file="JSCSSjsp.jsp" %>

        <script>

            $(document).ready(function() {

                obtenerPost();
                obtenerPostHechos();

                $('#addP').click(function() {
                    $('#agregarPost').fadeIn();
                    $('#addP').hide();
                    $('#listaPost').hide();
                });

                $('#cancelarP').click(function() {
                    $('#agregarPost').hide();
                    $('#addP').fadeIn();
                    $('#listaPost').fadeIn();
                });
            });

            function obtenerPost() {

                $.ajaxSetup({
                    async: false
                });

                var context = $('#contexto').val();

                $.getJSON(context + '/ServletPost?opcion=1', function(data) {

                    $('#cuerpotablaPricipal').remove();

                    if (data.parametros.length > 0) {

                        var cuerpoT = '<tbody id="cuerpotablaPricipal">';
                        for (BuscadorBean in data.parametros) {
                            cuerpoT += "<tr><td>" + data.parametros[BuscadorBean].nombrePublicacion.toUpperCase() + "</td>"
                                    + "<td>" + data.parametros[BuscadorBean].presupuesto.toUpperCase() + "</td>"
                                    + "<td>" + data.parametros[BuscadorBean].descripcion.toUpperCase() + "</td>"
                                    + "<td><a data-toggle='modal' data-id=" + data.parametros[BuscadorBean].idUserFK + " title='Enviar mensaje' class='open-Modal btn btn-success icon fa-comments-o' data-target='#EnviarMensaje'></a></td>"
                                    + "<td><a data-toggle='modal' data-id=" + data.parametros[BuscadorBean].idUserFK + " title='Enviar correo' class='open-Modal btn btn-primary glyphicon glyphicon-envelope' data-target='#EnviarCorreo'></a></td></tr>"
                        }
                        $('#tablaPricipal').append(cuerpoT + "</tbody>");
                        $('#tablaPricipal').dataTable();
                    }
                });

                $.ajaxSetup({
                    async: true
                });
            }

            function obtenerPostHechos() {

                $.ajaxSetup({
                    async: false
                });

                var context = $('#contexto').val();

                $.getJSON(context + '/ServletPost?opcion=5', function(data) {

                    $('#cuerpotablaPricipalHechos').remove();

                    if (data.parametros.length > 0) {

                        var cuerpoT = '<tbody id="cuerpotablaPricipalHechos">';
                        for (BuscadorBean in data.parametros) {
                            cuerpoT += "<tr><td>" + data.parametros[BuscadorBean].nombrePublicacion.toUpperCase() + "</td>"
                                    + "<td>" + data.parametros[BuscadorBean].presupuesto.toUpperCase() + "</td>"
                                    + "<td>" + data.parametros[BuscadorBean].descripcion.toUpperCase() + "</td>"
                                    + "<td><a data-toggle='modal' data-id=" + data.parametros[BuscadorBean].idPost + " title='Editar' class='open-Modal btn btn-primary glyphicon glyphicon-edit' data-target='#editarPost' onclick='editarPost(" + data.parametros[BuscadorBean].idPost + ")'></a></td>"
                                    + "<td><a data-toggle='modal' data-id=" + data.parametros[BuscadorBean].idPost + " title='Eliminar' class='open-Modal btn btn-danger glyphicon glyphicon-erase' data-target='#eliminarPost' onclick='elminarPost(" + data.parametros[BuscadorBean].idPost + ")'></a></td></tr>";
                        }
                        $('#tablaPricipalHechos').append(cuerpoT + "</tbody>");
                        $('#tablaPricipalHechos').dataTable();
                    }
                });

                $.ajaxSetup({
                    async: true
                });
            }

            $(document).on("click", ".open-Modal", function() {
                var idBusq = $(this).data('id');

                $.ajaxSetup({
                    async: false
                });

                var context = $('#contexto').val();
                $.getJSON(context + '/ServletPost?opcion=2&buscarId=' + idBusq, function(data) {

                    if (data.parametros.length > 0) {
                        for (BuscadorBean in data.parametros) {
                            $('#nombre').val(data.parametros[BuscadorBean].nombre.toUpperCase());
                            $('#email').val(data.parametros[BuscadorBean].email.toUpperCase());

                            $('#idUsermensaje').val(data.parametros[BuscadorBean].idUsuario);
                            $('#nombremensaje').val(data.parametros[BuscadorBean].nombre.toUpperCase());
                        }
                    }
                });

                $.ajaxSetup({
                    async: true
                });

            });

            function elminarPost(idpostEliminar) {
                $('#idpostBorrar').val(idpostEliminar);
            }

            function editarPost(idpostEditar) {
                var context = $('#contexto').val();
                var id = idpostEditar;

                $.ajax({
                    url: context + '/ServletPost',
                    type: 'POST',
                    data: {
                        "opcion": 7,
                        "id": id
                    },
                    success: function(data) {
                        $('#idpostEditar').val(data.idPost);
                        $('#nombrePublicacionEditar').val(data.nombrePublicacion).toUpperCase();
                        $('#presupuestoEditar').val(data.presupuesto).toUpperCase();
                        $('#descripcionEditar').val(data.descripcion).toUpperCase();
                    },
                    error: function() {
                        alert("Error de conexión");
                    }
                });
            }
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
                                <input id="nombrePost" name="nombrePost" type="text" class="form-control" placeholder="Titulo de Post">                                        
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-money"></i></span>
                                <input id="presupuestoPost" name="presupuestoPost" type="text" class="form-control" placeholder="Presupuesto">
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                                <textarea id="descripcionPost" name="descripcionPost" class="form-control" required placeholder="Descripción"></textarea>
                            </div>
                            <div id="setGroupGrade">
                                <br>
                                <button data-toggle="modal" class="open-Modal btn btn-success" type="submit">Aceptar</button>
                                <button id="cancelarP" class="btn btn-danger" type="reset">Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Table post echos-->
            <div id="listaPostHechos" class="box container">
                <div class="greybox col-xs-12">
                    <div class="col-md-12 section text-center">
                        <h3>Listado de Post's Hechos</h3>
                        <table id="tablaPricipalHechos" class="table table-bordered table-striped table-hover" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>Titulo</th>
                                    <th>Presupuesto</th>
                                    <th>Descripcion</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>

                            <tfoot>
                                <tr>
                                    <th>Titulo</th>
                                    <th>Presupuesto</th>
                                    <th>Descripcion</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </tfoot>

                            <tbody id="cuerpotablaPricipalHechos"></tbody>

                        </table>

                    </div>
                </div>
            </div>
        </div>

        <!-- Table Post disponibles-->
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
                                <th>Titulo</th>
                                <th>Presupuesto</th>
                                <th>Descripcion</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>

                        <tfoot>
                            <tr>
                                <th>Titulo</th>
                                <th>Presupuesto</th>
                                <th>Descripcion</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </tfoot>

                        <tbody id="cuerpotablaPricipal"></tbody>

                    </table>

                </div>
            </div>
        </div>

        <!-- Modal enviar Correo -->

        <div id="EnviarCorreo" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Enviar Correo</h4>
                    </div>
                    <form id="enviar" method="post" action="<%=context%>/ServletPost?opcion=4">                        
                        <div class="modal-body text-center form-inline">
                            <!-- contenido tabla consulta --> 
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input id="nombre" name="nombre" type="text" required class="form-control" readonly="">                                        
                            </div>
                            <br><br>

                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
                                <input type="text"  name="email" id="email" required class="form-control" readonly="">
                            </div>
                            <br><br>

                            <div class="input-group  col-md-11">
                                <span class="input-group-addon"><span class="icon fa-pencil-square-o"></span></span>
                                <textarea name="mensaje" id="mensaje" class="form-control" required placeholder="Mensaje"></textarea>
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

        <!-- Modal enviar Mensaje -->

        <div id="EnviarMensaje" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Enviar Mensaje</h4>
                    </div>
                    <form id="enviar" method="post" action="<%=context%>/ServletPost?opcion=9">                        
                        <div class="modal-body text-center form-inline">
                            <!-- contenido tabla consulta --> 
                            <input id="idUsermensaje" name="idUsermensaje" type="hidden" required>                                        

                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input id="nombremensaje" name="nombremensaje" type="text" required class="form-control" readonly="">                                        
                            </div>
                            <br><br>

                            <div class="input-group">
                                <span class="input-group-addon"><span class="icon fa-question-circle"></span></span>
                                <input type="text"  name="asuntomensaje" id="asuntomensaje" required class="form-control" placeholder="Asunto">
                            </div>
                            <br><br>

                            <div class="input-group  col-md-11">
                                <span class="input-group-addon"><span class="icon fa-pencil-square-o"></span></span>
                                <textarea name="mensajemensaje" id="mensajemensaje" required class="form-control" placeholder="Mensaje"></textarea>
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

        <!-- Modal eliminar post -->

        <div id="eliminarPost" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">¿Deseas eliminar el post?</h4>
                    </div>
                    <form id="enviar" method="post" action="<%=context%>/ServletPost?opcion=6">  
                        <input type="hidden" name="idpostBorrar" id="idpostBorrar"/>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Aceptar</button>
                            <button type="reset" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>     

        <!-- Modal editar post -->

        <div id="editarPost" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Editar post</h4>
                    </div>
                    <form id="enviar" method="post" action="<%=context%>/ServletPost?opcion=8"> 
                        <div class="modal-body">
                            <input type="hidden" name="idpostEditar" id="idpostEditar"/>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input id="nombrePublicacionEditar" name="nombrePublicacionEditar" type="text" class="form-control" placeholder="Titulo de Post">                                        
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-money"></i></span>
                                <input id="presupuestoEditar" name="presupuestoEditar" type="text" class="form-control" placeholder="Presupuesto">
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                                <textarea id="descripcionEditar" name="descripcionEditar" class="form-control" required placeholder="Descripción"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Aceptar</button>
                            <button type="reset" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div> 


        <!-- footer -->
        <%@include file="footer.jsp" %>
    </body>
</html>