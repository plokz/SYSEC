/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.TipoBean;
import Beans.UsuariosBean;
import Daos.DaoLogin;
import Daos.DaoPreferencias;
import Daos.DaoRegistro;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo
 */
public class ServletUsuario extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            List listaPerso = null;
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            Gson gson = new Gson();
            HttpSession session = request.getSession();
            String JSON = "";
            UsuariosBean usu = new UsuariosBean();
            DaoLogin daoLog = new DaoLogin();
            DaoRegistro daoReg = new DaoRegistro();
            DaoPreferencias daoPre = new DaoPreferencias();
            int idRegistroUsuario = Integer.parseInt(session.getAttribute("idU").toString());

            switch (opcion) {
                case 1:
                    usu = daoLog.consultarDatosUsuario(idRegistroUsuario);
                    JSON = gson.toJson(usu);
                    out.print(JSON);
                    break;
                case 2:
                    TipoBean beantipo = new TipoBean();
                    beantipo.setIdTipo(2);
                    String[] valuesCheck = null;

                    String email = request.getParameter("email");
                    String user = request.getParameter("user");
                    String estado = new String(request.getParameter("estado").getBytes("ISO8859-1"), "UTF-8");
                    String nombre = new String(request.getParameter("nombre").getBytes("ISO8859-1"), "UTF-8");
                    String cp = request.getParameter("cp");
                    String colonia = new String(request.getParameter("colonia").getBytes("ISO8859-1"), "UTF-8");
                    String password = request.getParameter("password");
                    String RFC = request.getParameter("RFC");

                    usu.setIdUsuario(idRegistroUsuario);
                    usu.setNombre(nombre);
                    usu.setRfc(RFC);
                    usu.setColonia(colonia);
                    usu.setCP(cp);
                    usu.setEmail(email);
                    usu.setUsuario(user);
                    usu.setPassword(password);
                    usu.setTipoBean(beantipo);
                    usu.setEstadoBean(daoReg.consultaridEstado(estado));

                    daoPre.eliminarPreferencias(idRegistroUsuario);

                    try {
                        valuesCheck = request.getParameterValues("checkbox");
                        if (valuesCheck.length > 0) {
                            daoPre.actualizarDatos(usu, valuesCheck);
                        }
                    } catch (Exception e) {
                    }

                    request.setAttribute("texto", "El registro se completo");
                    request.getRequestDispatcher("/modificarInfoUser.jsp").forward(request, response);
                    break;
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
