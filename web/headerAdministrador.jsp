<%
    //allow access only if session exists
    String user = null;
    if (session.getAttribute("user") == null || !session.getAttribute("tipo").equals("adm")) {
        response.sendRedirect("index.jsp");
    } else {
        user = (String) session.getAttribute("user");
    }
    String userName = null;
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) {
                userName = cookie.getValue();
            }
            if (cookie.getName().equals("JSESSIONID")) {
                sessionID = cookie.getValue();
            }
        }
    }
%>
<!-- Header -->
<div id="header-wrapper">
    <header id="header" class="container">

        <!-- Logo -->
        <div id="logo">
            <img src="<%=context%>/images/escudo.png" alt="">
        </div>

        <!-- Nav -->
        <nav id="nav">
            <ul>
                <li><a class="icon fa-home" href="<%=context%>/principalAdministrador.jsp"> Inicio</a></li>
                <li>
                    <a class="icon fa-slideshare" href="#"> Opciones administrador</a>
                    <ul>
                        <li><a class="icon fa-circle-o" href="<%=context%>/preferencias.jsp"> Preferencias</a></li>

                        <li><a class="icon fa-circle-o" href="<%=context%>/reportesAdministrador.jsp"> Reportes</a></li>

                    </ul>
                </li>
                <li><a class="icon fa-sign-out" href="<%=context%>/ServletLogout"> Salir</a></li>
            </ul> 
            <h4 class="col-md-12 col-lg-12 col-md-offset-12 col-lg-offset-2 text-center">Bienvenido <%=user%></h4>
        </nav>       

    </header>
</div>