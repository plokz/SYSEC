/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.UsuariosBean;
import Daos.DaoLogin;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author CSD-HOME
 */
public class ServletLogin extends HttpServlet {

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

        String user = request.getParameter("user");
        String pwd = request.getParameter("password");

        DaoLogin dao = new DaoLogin();
        UsuariosBean ingresoUsu = dao.consultarUsuario(user, pwd);
        if (ingresoUsu.getTipoBean().getIdTipo() != 0) {
            if (ingresoUsu.getTipoBean().getIdTipo() == 1) {
                HttpSession session = request.getSession();
                session.setAttribute("user", ingresoUsu.getNombre());
                session.setAttribute("idU", ingresoUsu.getIdUsuario());
                //setting session to expiry in 30 mins (30*60)
                session.setMaxInactiveInterval(30 * 60);
                Cookie userName = new Cookie("user", user);
                userName.setMaxAge(30 * 60);
                response.addCookie(userName);
                session.setAttribute("tipo", "adm");

                String encodedURL = response.encodeRedirectURL("principalAdministrador.jsp");
                response.sendRedirect(encodedURL);
            } else if (ingresoUsu.getTipoBean().getIdTipo() == 2) {
                //PENDIENTE LLAMAR SUS MENSAJES Y POST
                HttpSession session = request.getSession();
                session.setAttribute("user", ingresoUsu.getNombre());
                session.setAttribute("idU", ingresoUsu.getIdUsuario());
                //setting session to expiry in 30 mins (30*60)
                session.setMaxInactiveInterval(30 * 60);
                Cookie userName = new Cookie("user", user);
                userName.setMaxAge(30 * 60);
                response.addCookie(userName);
                session.setAttribute("tipo", "empre");

                String encodedURL = response.encodeRedirectURL("principalUsuarios.jsp");
                response.sendRedirect(encodedURL);
            }
        } else {

            request.setAttribute("texto", "Verifica tus datos");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
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