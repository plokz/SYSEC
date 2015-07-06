package Servlets;

import Beans.PreferenciasBean;
import Daos.DaoPreferencias;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aya
 */
public class ServletPreferencias extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        int opcion = request.getParameter("opcion") != null ? Integer.parseInt(request.getParameter("opcion")) : 0;
        DaoPreferencias daoPreferencias = new DaoPreferencias();
        Gson gson = new Gson();

        PrintWriter out = response.getWriter();
        try {
            switch (opcion) {
                case 1:  // Consulta invidual de una preferencia
                    int id = Integer.parseInt(request.getParameter("id"));
                    PreferenciasBean preferencia = daoPreferencias.consultar(id);

                    out.println(gson.toJson(preferencia));
                    break;

                case 2:  // Modificacion de una preferencia con base en su id
                    id = Integer.parseInt(request.getParameter("id"));
                    String descripcion = new String(request.getParameter("descripcion").getBytes("ISO8859-1"), "UTF-8");

                    daoPreferencias.modificar(id, descripcion);

                    request.setAttribute("mensaje", "Información modificada exitosamente");
                    this.doGet(request, response);
                    break;

                case 3:  // Agregar una preferencia
                    descripcion = new String(request.getParameter("descripcion").getBytes("ISO8859-1"), "UTF-8");

                    daoPreferencias.agregar(descripcion);

                    request.setAttribute("mensaje", "Preferencia agregada exitosamente");
                    this.doGet(request, response);
                    break;

                default:  // Consulta general de las preferencias
                    List<PreferenciasBean> preferencias = daoPreferencias.consultar();

                    out.println(gson.toJson(preferencias));
            }
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/preferencias.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
