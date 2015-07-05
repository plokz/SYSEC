/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Beans.TipoBean;
import Beans.UsuariosBean;
import Utilerias.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Eduardo
 */
public class DaoLogin {

    public UsuariosBean consultarUsuario(String user, String pass) {
        UsuariosBean logeado = new UsuariosBean();
        TipoBean tipo = new TipoBean();
        tipo.setIdTipo(0);
        logeado.setTipoBean(tipo);
        try {
            Connection conexion = Conexion.getConnection();
            String query = "SELECT idUsuarios, nombre, tipo FROM usuarios WHERE usuario = ? and password = ?;";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            System.out.println("PS*******" + ps);
            while (rs.next()) {
                logeado.setIdUsuario(rs.getInt(1));
                logeado.setNombre(rs.getString(2));
                tipo.setIdTipo(rs.getInt(3));
                logeado.setTipoBean(tipo);
            }
            rs.close();
            ps.close();
            conexion.close();

        } catch (SQLException sale) {
            System.out.println(" dao " + sale);
        }
        return logeado;
    }
}
