/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

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
            PreparedStatement prepareStatement = conexion.prepareStatement(query);
            prepareStatement.setInt(1, idPost);

            status = prepareStatement.execute();
        } catch (SQLException ex) {
            System.out.println("DaoAsignatura.aliminar()");
            System.out.println(ex);
        }

        return status;
    }
}
