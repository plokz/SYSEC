/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import Beans.EstadoBean;
import Beans.PreferenciasBean;
import Beans.TipoBean;
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

    public UsuariosBean consultarDatosUsuario(int id) {
        UsuariosBean logeado = new UsuariosBean();
        TipoBean tipo = new TipoBean();
        EstadoBean estado = null;
        PreferenciasBean prefe = null;
        try {
            Connection conexion = Conexion.getConnection();
            String query = "SELECT idUsuarios, nombre, rfc, colonia, "
                    + "cp, email, usuario, password, tipo, estado FROM usuarios WHERE idUsuarios = ?";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                logeado.setIdUsuario(rs.getInt(1));
                logeado.setNombre(rs.getString(2));
                logeado.setRfc(rs.getString(3));
                logeado.setColonia(rs.getString(4));
                logeado.setCP(rs.getString(5));
                logeado.setEmail(rs.getString(6));
                logeado.setUsuario(rs.getString(7));
                logeado.setPassword(rs.getString(8));

                tipo.setIdTipo(rs.getInt(9));
                logeado.setTipoBean(tipo);

                String queryEstado = "SELECT * FROM estado WHERE idEstado = ?";

                ps = conexion.prepareStatement(queryEstado);
                ps.setInt(1, rs.getInt(10));
                rs = ps.executeQuery();

                while (rs.next()) {
                    estado = new EstadoBean();
                    estado.setIdEstado(rs.getInt(1));
                    estado.setNombre(rs.getString(2));
                    logeado.setEstadoBean(estado);
                }

                String queryPreferencias = "SELECT p.idPreferencias, p.preferencia "
                        + "FROM tienepreferencia t join preferencias p "
                        + "on t.preferencias = p.idPreferencias WHERE usuariosTPFK = ?";

                ps = conexion.prepareStatement(queryPreferencias);
                ps.setInt(1, logeado.getIdUsuario());
                rs = ps.executeQuery();
                List<PreferenciasBean> listaPre = new ArrayList<PreferenciasBean>();
                while (rs.next()) {
                    prefe = new PreferenciasBean();
                    prefe.setIdPreferencias(rs.getInt(1));
                    prefe.setPreferencia(rs.getString(2));
                    prefe.setTiene(true);
                    listaPre.add(prefe);
                }

                String queryPreferenciasFaltan = "SELECT idPreferencias, preferencia FROM preferencias where idPreferencias not in "
                        + "(SELECT preferencias FROM tienepreferencia where usuariosTPFK = ?);";

                ps = conexion.prepareStatement(queryPreferenciasFaltan);
                ps.setInt(1, logeado.getIdUsuario());
                rs = ps.executeQuery();

                while (rs.next()) {
                    prefe = new PreferenciasBean();
                    prefe.setIdPreferencias(rs.getInt(1));
                    prefe.setPreferencia(rs.getString(2));
                    prefe.setTiene(false);
                    listaPre.add(prefe);
                }
                logeado.setPreferenciasBean(listaPre);
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
