/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Beans.EstadoBean;
import Beans.PreferenciasBean;
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
public class DaoRegistro {

    public List consultarListado() {
        //Lista para retornar los beans con los registros
        List lisEdo = new ArrayList();
        try {
            //Se establece la conexion
            Connection conexion = Conexion.getConnection();
            //Se prepara la sentencia SQL
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM estado;");
            //Se ejecuta la sentencia SQL
            ResultSet rs = ps.executeQuery();
            //Manipular los datos obtenidos de la consulta (ResultSet)
            while (rs.next()) {
                //CrearBean
                EstadoBean bean = new EstadoBean();
                //Asignar el contenido del ResultSet a cada atributo del bean
                bean.setIdEstado(rs.getInt(1));
                bean.setNombre(rs.getString(2));
                //Guardar los beans en la lista
                lisEdo.add(bean);
            }
            //Cerramos el resultset, preparestatement y la conexion
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException sqle) {
            System.err.println("Error en la consulta");
        }
        return lisEdo;
    }

    public EstadoBean consultaridEstado(String nombre) {
        int id = 0;
        EstadoBean bean = new EstadoBean();
        try {
            bean.setIdEstado(id);
            //Se establece la conexion
            Connection conexion = Conexion.getConnection();
            //Se prepara la sentencia SQL
            PreparedStatement ps = conexion.prepareStatement("select idestado from estado where nombre='" + nombre + "';");
            //Se ejecuta la sentencia SQL
            ResultSet rs = ps.executeQuery();
            //Manipular los datos obtenidos de la consulta (ResultSet)
            while (rs.next()) {
                //Asignar el contenido del ResultSet a cada atributo del bean
                id = (rs.getInt(1));
                bean.setIdEstado(id);
            }
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException sqle) {
            System.err.println("Error en la consulta");
        }
        return bean;
    }

    public boolean consultarCorreoUsuario(String correo) {
        boolean id = false;
        try {
            //Se establece la conexion
            Connection conexion = Conexion.getConnection();
            //Se prepara la sentencia SQL
            PreparedStatement ps = conexion.prepareStatement("select count(*) from usuarios where email='" + correo + "';");
            //Se ejecuta la sentencia SQL
            ResultSet rs = ps.executeQuery();
            //Manipular los datos obtenidos de la consulta (ResultSet)
            while (rs.next()) {
                int correoUser = (rs.getInt(1));
                if (correoUser != 0) {
                    id = true;
                }
            }
            //Cerramos el resultset, preparestatement y la conexion
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException sqle) {
            System.err.println("Error en la consulta correo");
        }
        return id;
    }

    public int altaUsuario(UsuariosBean bean, String[] valuesChecks) {
        int resultado = 0;
        String sqlAltaUsuario = "insert into usuarios values (null,?,?,?,?,?,?,?,?,?);";
        PreparedStatement ps = null;
        try {
            Connection conexion = Conexion.getConnection();
            ps = (PreparedStatement) conexion.prepareStatement(sqlAltaUsuario);
            ps.setString(1, bean.getNombre());
            ps.setString(2, bean.getRfc());
            ps.setString(3, bean.getColonia());
            ps.setString(4, bean.getCP());
            ps.setString(5, bean.getEmail());
            ps.setString(6, bean.getUsuario());
            ps.setString(7, bean.getPassword());
            ps.setInt(8, bean.getEstadoBean().getIdEstado());
            ps.setInt(9, bean.getTipoBean().getIdTipo());
            resultado = ps.executeUpdate();

            if (valuesChecks.length > 0) {
                String sqlAltaPreferencia = "insert into tienepreferencia values (null,?,?);";

                DaoLogin login = new DaoLogin();
                bean = login.consultarUsuario(bean.getUsuario(), bean.getPassword());
                int idUsuarioReg = bean.getIdUsuario();

                for (String value : valuesChecks) {
                    ps = (PreparedStatement) conexion.prepareStatement(sqlAltaPreferencia);
                    int idpref = (Integer.parseInt(value));

                    ps.setInt(1, idpref);
                    ps.setInt(2, idUsuarioReg);
                    ps.executeUpdate();
                }
            }

            conexion.close();
            ps.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        System.out.println(resultado + " :se registro?");
        return resultado;
    }

    public List consultarListadoPreferencias() {
        //Lista para retornar los beans con los registros
        List listPref = new ArrayList();
        try {
            //Se establece la conexion
            Connection conexion = Conexion.getConnection();
            //Se prepara la sentencia SQL
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM preferencias;");
            //Se ejecuta la sentencia SQL
            ResultSet rs = ps.executeQuery();
            //Manipular los datos obtenidos de la consulta (ResultSet)
            while (rs.next()) {
                //CrearBean
                PreferenciasBean bean = new PreferenciasBean();
                //Asignar el contenido del ResultSet a cada atributo del bean
                bean.setIdPreferencias(rs.getInt(1));
                bean.setPreferencia(rs.getString(2));
                //Guardar los beans en la lista
                listPref.add(bean);
            }
            //Cerramos el resultset, preparestatement y la conexion
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException sqle) {
            System.err.println("Error en la consulta");
        }
        return listPref;
    }
}
