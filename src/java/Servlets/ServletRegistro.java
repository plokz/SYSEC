/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.EstadoBean;
import Beans.PreferenciasBean;
import Beans.TipoBean;
import Beans.UsuariosBean;
import Daos.DaoRegistro;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eduardo
 */
public class ServletRegistro extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            List<EstadoBean> listEstados = null;
            List<PreferenciasBean> listPreferencias = null;
            Gson gson = new Gson();
            HttpSession session = request.getSession();
//            int idRegistroUsuario = Integer.parseInt(session.getAttribute("idU").toString());
            String JSON = "";
            DaoRegistro dao = new DaoRegistro();
            UsuariosBean bean = new UsuariosBean();
            switch (opcion) {
                case 1:
                    listEstados = dao.consultarListado();
                    JSON = "{\"parametros\": " + gson.toJson(listEstados) + "}";
                    out.print(JSON);
                    break;
                case 2:
                    String email = request.getParameter("email");
                    String cadena[] = email.split("@");
                    String alphabet = "!#$%&/@?*¿";
                    Random rd = new Random();
                    char sb = 0;
                    for (int i = 0; i <= alphabet.length(); i++) {
                        sb = (alphabet.charAt(rd.nextInt(alphabet.length())));
                    }
                    boolean correoEsta = dao.consultarCorreoUsuario(email);
                    JSON = "{\"parametros\": [{\"estado\": \"" + gson.toJson(correoEsta) + "\",\"usuarioCreado\":\"" + cadena[0] + sb + "\"}]}";
                    out.print(JSON);
                    break;
                case 3:                   
                    
                    String[] values = request.getParameterValues("checkbox");
                    for (String value : values) {
                        System.out.println("Valores enviados " + value);
                    }
                    
                    TipoBean beantipo = new TipoBean();
                    beantipo.setIdTipo(2);

                    email = request.getParameter("email");
                    String user = request.getParameter("user");
                    String estado = new String(request.getParameter("estado").getBytes("ISO8859-1"), "UTF-8");
                    String nombre = new String(request.getParameter("nombre").getBytes("ISO8859-1"), "UTF-8");
                    String cp = request.getParameter("cp");
                    String colonia = new String(request.getParameter("colonia").getBytes("ISO8859-1"), "UTF-8");
                    String password = request.getParameter("password");
                    String RFC = request.getParameter("RFC");

                    bean.setNombre(nombre);
                    bean.setRfc(RFC);
                    bean.setColonia(colonia);
                    bean.setCP(cp);
                    bean.setEmail(email);
                    bean.setUsuario(user);
                    bean.setPassword(password);
                    bean.setTipoBean(beantipo);
                    bean.setEstadoBean(dao.consultaridEstado(estado));

                    dao.altaUsuario(bean);

                    request.setAttribute("texto", "El registro se completo");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    break;
                case 4:
                    listPreferencias = dao.consultarListadoPreferencias();
                    JSON = gson.toJson(listPreferencias);
                    out.print(JSON);
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
