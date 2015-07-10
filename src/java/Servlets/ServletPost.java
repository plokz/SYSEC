/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.MensajesBean;
import Beans.PostBean;
import Beans.UsuariosBean;
import Daos.DaoPost;
import Utilerias.javaMail;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            throws ServletException, IOException, SQLException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            List ListMens = null;
            List listaPerso = null;
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            Gson gson = new Gson();
            HttpSession session = request.getSession();
            String JSON = "";
            DaoPost daoPost = new DaoPost();
            PostBean beanPost = new PostBean();
            UsuariosBean beanUsu = new UsuariosBean();

            switch (opcion) {
                case 1:
                    int idRegistroUsuario = Integer.parseInt(session.getAttribute("idU").toString());
                    ListMens = daoPost.consultaPost(idRegistroUsuario);
                    JSON = "{\"parametros\": " + gson.toJson(ListMens) + "}";
                    out.print(JSON);
                    break;
                case 2:
                    int buscarId = Integer.parseInt(request.getParameter("buscarId"));
                    beanUsu = daoPost.consultaUser(buscarId);
                    JSON = "{\"parametros\": [" + gson.toJson(beanUsu) + "]}";
                    out.print(JSON);
                    break;
                case 3:
                    idRegistroUsuario = Integer.parseInt(session.getAttribute("idU").toString());
                    String nombrePost = request.getParameter("nombrePost");
                    String presupuestoPost = request.getParameter("presupuestoPost");
                    String descripcionPost = request.getParameter("descripcionPost");

                    beanPost.setIdUserFK(idRegistroUsuario);
                    beanPost.setNombrePublicacion(nombrePost);
                    beanPost.setPresupuesto(presupuestoPost);
                    beanPost.setDescripcion(descripcionPost);
                    daoPost.crearPost(beanPost);

                    request.setAttribute("texto", "El registro se completo");
                    request.getRequestDispatcher("/principalUsuarios.jsp").forward(request, response);
                    break;
                case 4:
                    javaMail correo = new javaMail();

                    String destino = new String(request.getParameter("email").getBytes("ISO8859-1"), "UTF-8");
                    String asunto = new String(request.getParameter("nombre").getBytes("ISO8859-1"), "UTF-8");
                    String mensaje = new String(request.getParameter("mensaje").getBytes("ISO8859-1"), "UTF-8");

                    correo.send("ploks.valora@gmail.com", asunto, mensaje);

                    request.setAttribute("texto", "El correo ha sido enviado");
                    request.getRequestDispatcher("/principalUsuarios.jsp").forward(request, response);
                    break;
                case 5:
                    idRegistroUsuario = Integer.parseInt(session.getAttribute("idU").toString());
                    ListMens = daoPost.consultaPostHechos(idRegistroUsuario);
                    JSON = "{\"parametros\": " + gson.toJson(ListMens) + "}";
                    out.print(JSON);
                    break;
                case 6:
                    int idpostBorrar = Integer.parseInt(request.getParameter("idpostBorrar"));
                    daoPost.eliminarPost(idpostBorrar);

                    request.setAttribute("texto", "El Post ha sido eliminado");
                    request.getRequestDispatcher("/principalUsuarios.jsp").forward(request, response);
                    break;
                case 7:
                    int id = Integer.parseInt(request.getParameter("id"));
                    PostBean postbean = daoPost.consultarDatosPost(id);
                    System.out.println("" + gson.toJson(postbean));
                    out.println(gson.toJson(postbean));
                    break;
                case 8:
                    int idPostEdit = Integer.parseInt(request.getParameter("idpostEditar"));
                    String nombrePublicacionEditar = request.getParameter("nombrePublicacionEditar");
                    String presupuestoEditar = request.getParameter("presupuestoEditar");
                    String descripcionEditar = request.getParameter("descripcionEditar");

                    beanPost.setIdPost(idPostEdit);
                    beanPost.setNombrePublicacion(nombrePublicacionEditar);
                    beanPost.setPresupuesto(presupuestoEditar);
                    beanPost.setDescripcion(descripcionEditar);
                    daoPost.modificarPost(beanPost);

                    request.setAttribute("texto", "Información modificada exitosamente");
                    request.getRequestDispatcher("/principalUsuarios.jsp").forward(request, response);
                    break;
                case 9:
                    MensajesBean msnBean = new MensajesBean();
                    //id de quien manda 
                    int idRemitente = Integer.parseInt(session.getAttribute("idU").toString());
                    //id quien recive
                    int idUsermensaje = Integer.parseInt(request.getParameter("idUsermensaje"));

                    //String paraQuien = new String(request.getParameter("nombremensaje").getBytes("ISO8859-1"), "UTF-8");
                    String asuntomensaje = new String(request.getParameter("asuntomensaje").getBytes("ISO8859-1"), "UTF-8");
                    String mensajemensaje = new String(request.getParameter("mensajemensaje").getBytes("ISO8859-1"), "UTF-8");

                    msnBean.setIdRemitente(idRemitente);
                    msnBean.setIdUsuario(idUsermensaje);
                    msnBean.setAsunto(asuntomensaje);
                    msnBean.setMensaje(mensajemensaje);
                    msnBean.setEstado(false);

                    daoPost.crearMensaje(msnBean);
                    request.setAttribute("texto", "El mensaje ha sido enviado exitosamente");
                    request.getRequestDispatcher("/principalUsuarios.jsp").forward(request, response);
                    break;
                case 10:
                    int idUsuario = Integer.parseInt(session.getAttribute("idU").toString());
                    ListMens = daoPost.consultaMensajes(idUsuario);
                    JSON = "{\"parametros\": " + gson.toJson(ListMens) + "}";
                    System.out.println(""+JSON);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletPost.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletPost.class.getName()).log(Level.SEVERE, null, ex);
        }
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
