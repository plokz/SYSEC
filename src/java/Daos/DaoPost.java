/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Beans.MensajesBean;
import Beans.PostBean;
import Beans.UsuariosBean;
import Utilerias.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class DaoPost {

    public int crearPost(PostBean bean) {
        int resultado = 0;
        String sqlcrearPost = "INSERT INTO post values (null,?,?,?,?);";
        PreparedStatement ps = null;
        try {
            Connection conexion = Conexion.getConnection();
            ps = (PreparedStatement) conexion.prepareStatement(sqlcrearPost);
            ps.setString(1, bean.getNombrePublicacion());
            ps.setString(2, bean.getPresupuesto());
            ps.setString(3, bean.getDescripcion());
            ps.setInt(4, bean.getIdUserFK());

            resultado = ps.executeUpdate();
            conexion.close();
            ps.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return resultado;
    }

    public List consultaPost(int idRegistroUsuario) {
        //Lista para retornar los beans con los registros
        List listPost = new ArrayList();
        PostBean postb;
        try {
            //Se establece la conexion
            Connection conexion = Conexion.getConnection();
            //Se prepara la sentencia SQL
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM post where usuariospPFK != " + idRegistroUsuario + ";");
            //Se ejecuta la sentencia SQL
            ResultSet rs = ps.executeQuery();
            //Manipular los datos obtenidos de la consulta (ResultSet)
            while (rs.next()) {
                postb = new PostBean();
                //Asignar el contenido del ResultSet a cada atributo del bean
                postb.setIdPost(rs.getInt(1));
                postb.setNombrePublicacion(rs.getString(2));
                postb.setPresupuesto(rs.getString(3));
                postb.setDescripcion(rs.getString(4));
                postb.setIdUserFK(rs.getInt(5));
                listPost.add(postb);
            }
            //Cerramos el resultset, preparestatement y la conexion
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException sqle) {
            System.err.println("Error en la consulta");
        }
        return listPost;
    }

    public List consultaPostHechos(int idRegistroUsuario) {
        //Lista para retornar los beans con los registros
        List listPost = new ArrayList();
        PostBean postb;
        try {
            //Se establece la conexion
            Connection conexion = Conexion.getConnection();
            //Se prepara la sentencia SQL
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM post where usuariospPFK = " + idRegistroUsuario + ";");
            //Se ejecuta la sentencia SQL
            ResultSet rs = ps.executeQuery();
            //Manipular los datos obtenidos de la consulta (ResultSet)
            while (rs.next()) {
                postb = new PostBean();
                //Asignar el contenido del ResultSet a cada atributo del bean
                postb.setIdPost(rs.getInt(1));
                postb.setNombrePublicacion(rs.getString(2));
                postb.setPresupuesto(rs.getString(3));
                postb.setDescripcion(rs.getString(4));
                postb.setIdUserFK(rs.getInt(5));
                listPost.add(postb);
            }
            //Cerramos el resultset, preparestatement y la conexion
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException sqle) {
            System.err.println("Error en la consulta");
        }
        return listPost;
    }

    public UsuariosBean consultaUser(int buscarId) {
        //Lista para retornar los beans con los registros
        List listUSer = new ArrayList();
        UsuariosBean bean = new UsuariosBean();
        try {
            //Se establece la conexion
            Connection conexion = Conexion.getConnection();
            //Se prepara la sentencia SQL
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM usuarios where idUsuarios = " + buscarId + ";");
            //Se ejecuta la sentencia SQL
            ResultSet rs = ps.executeQuery();
            //Manipular los datos obtenidos de la consulta (ResultSet)
            while (rs.next()) {
                //Asignar el contenido del ResultSet a cada atributo del bean
                bean.setIdUsuario(rs.getInt(1));
                bean.setNombre(rs.getString(2));
                bean.setRfc(rs.getString(3));
                bean.setColonia(rs.getString(4));
                bean.setCP(rs.getString(5));
                bean.setEmail(rs.getString(6));
                bean.setUsuario(rs.getString(7));
                bean.setPassword(rs.getString(8));
            }
            //Cerramos el resultset, preparestatement y la conexion
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException sqle) {
            System.err.println("Error en la consulta");
        }
        return bean;
    }

    public boolean eliminarPost(int idPost) throws SQLException {
        String query = "DELETE FROM post WHERE idPost = ?";
        boolean status = false;

        try {
            Connection conexion = Conexion.getConnection();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, idPost);

            status = ps.execute();
            ps.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("DaoPost.EliminarPost()");
            System.out.println(ex);
        }

        return status;
    }

    public PostBean consultarDatosPost(int id) {
        String query = "SELECT * FROM post WHERE idPost = ?";
        PostBean beanPost = null;
        try {
            Connection conexion = Conexion.getConnection();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                beanPost = new PostBean();
                beanPost.setIdPost(resultSet.getInt("idPost"));
                beanPost.setNombrePublicacion(resultSet.getString("nombrePublicacion"));
                beanPost.setPresupuesto(resultSet.getString("presupuesto"));
                beanPost.setDescripcion(resultSet.getString("descripcion"));
                beanPost.setIdUserFK(resultSet.getInt("usuariospPFK"));

            }
            ps.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("DaoPost.ConsultarPost(int id)");
            System.out.println(ex);
        }
        return beanPost;
    }

    public boolean modificarPost(PostBean bean) {
        String query = "UPDATE post SET nombrePublicacion = ?, presupuesto = ?, descripcion = ? WHERE idPost = ?";
        boolean status = false;

        try {
            Connection connection = Conexion.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(query);

            prepareStatement.setString(1, bean.getNombrePublicacion());
            prepareStatement.setString(2, bean.getPresupuesto());
            prepareStatement.setString(3, bean.getDescripcion());
            prepareStatement.setInt(4, bean.getIdPost());

            status = prepareStatement.executeUpdate() != 0;

            prepareStatement.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("DaoPost.modificar()");
            System.out.println(ex);
        }

        return status;
    }

    public int crearMensaje(MensajesBean bean) {
        int resultado = 0;
        String sqlMensaje = "INSERT INTO tienemensajes values (null,?,?,?,?,?);";
        PreparedStatement ps = null;
        try {
            Connection conexion = Conexion.getConnection();
            ps = (PreparedStatement) conexion.prepareStatement(sqlMensaje);
            ps.setInt(1, bean.getIdUsuario());
            ps.setInt(2, bean.getIdRemitente());
            ps.setString(3, bean.getAsunto());
            ps.setString(4, bean.getMensaje());
            ps.setBoolean(5, bean.getEstado());

            resultado = ps.executeUpdate();
            conexion.close();
            ps.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return resultado;
    }
}
