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

                case 2:  // modificacion de una preferencia con base en su id
                    id = Integer.parseInt(request.getParameter("id"));
                    String descripcion = request.getParameter("descripcion");

                    boolean status = daoPreferencias.modificar(id, descripcion);
                    out.println(status);
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
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/principalUsuarios.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
