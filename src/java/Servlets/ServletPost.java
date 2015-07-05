/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.PostBean;
import Daos.DaoPost;
import Utilerias.javaMail;
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
public class ServletPost extends HttpServlet {

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
            List ListPost = null;
            List listaPerso = null;
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            Gson gson = new Gson();
            HttpSession session = request.getSession();
            String JSON = "";
            DaoPost dao = new DaoPost();
            PostBean bean = new PostBean();

            switch (opcion) {
                case 1:
                    ListPost = dao.consultaPost();
                    JSON = "{\"parametros\": " + gson.toJson(ListPost) + "}";
                    out.print(JSON);
                    break;
                case 2:
                    int buscarId = Integer.parseInt(request.getParameter("buscarId"));
                    listaPerso = dao.consultaUser(buscarId);
                    JSON = "{\"parametros\": [{\"idUsuarios\": " + gson.toJson(listaPerso.get(0))
                            + ",\"Nombre\":" + gson.toJson(listaPerso.get(1))
                            + ",\"RFC\":" + gson.toJson(listaPerso.get(2))
                            + ",\"Colonia\":" + gson.toJson(listaPerso.get(3))
                            + ",\"CP\":" + gson.toJson(listaPerso.get(4))
                            + ",\"Email\":" + gson.toJson(listaPerso.get(5))
                            + ",\"Usuario\":" + gson.toJson(listaPerso.get(6))
                            + ",\"Password\":" + gson.toJson(listaPerso.get(7))
                            + ",\"Estado\":" + gson.toJson(listaPerso.get(8))
                            + ",\"Tipo\":" + gson.toJson(listaPerso.get(9))
                            + "}]}";
                    out.print(JSON);
                    break;
                case 3:
                    int idRegistroUsuario = Integer.parseInt(session.getAttribute("idU").toString());
                    String nombrePost = request.getParameter("nombrePost");
                    String presupuestoPost = request.getParameter("presupuestoPost");
                    String descripcionPost = request.getParameter("descripcionPost");

                    bean.setIdUserFK(idRegistroUsuario);
                    bean.setNombrePublicacion(nombrePost);
                    bean.setPresupuesto(presupuestoPost);
                    bean.setDescripcion(descripcionPost);
                    dao.crearPost(bean);

                    request.setAttribute("texto", "El registro se completo");
                    request.getRequestDispatcher("/principalUsuarios.jsp").forward(request, response);
                    break;
                case 4:
                    javaMail correo = new javaMail();
                    
                    String destino = request.getParameter("email");
                    String asunto = request.getParameter("nombre");
                    String mensaje = request.getParameter("mensaje");
                    
                    correo.send("ploks.valora@gmail.com", asunto, mensaje);
                    
                    request.setAttribute("texto", "El correo ha sido enviado");
                    request.getRequestDispatcher("/principalUsuarios.jsp").forward(request, response);
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
