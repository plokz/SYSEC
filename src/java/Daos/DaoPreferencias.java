package Daos;

import java.util.List;
import Beans.PreferenciasBean;
import Utilerias.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aya
 */
public class DaoPreferencias {

    public boolean agregar(String preferencia) {
        String query = "INSERT INTO preferencias (preferencia) VALUES (?)";
        boolean status = false;

        try {
            Connection connection = Conexion.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(query);

            prepareStatement.setString(1, preferencia);

            status = prepareStatement.executeUpdate() != 0;

            prepareStatement.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("DaoPreferencias.agregar()");
            System.out.println(ex);
        }

        return status;
    }

    public boolean modificar(int id, String preferencia) {
        String query = "UPDATE preferencias SET preferencia = ? WHERE idPreferencias = ?";
        boolean status = false;

        try {
            Connection connection = Conexion.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(query);

            prepareStatement.setString(1, preferencia);
            prepareStatement.setInt(2, id);

            status = prepareStatement.executeUpdate() != 0;

            prepareStatement.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("DaoPreferencias.modificar()");
            System.out.println(ex);
        }

        return status;
    }

    public PreferenciasBean consultar(int id) {
        String query = "SELECT idPreferencias, preferencia FROM preferencias WHERE idPreferencias = ?";
        PreferenciasBean preferencia = null;

        try {
            Connection connection = Conexion.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(query);

            prepareStatement.setInt(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                preferencia = new PreferenciasBean();
                preferencia.setIdPreferencias(resultSet.getInt("idPreferencias"));
                preferencia.setPreferencia(resultSet.getString("preferencia"));
            }
        } catch (SQLException ex) {
            System.out.println("DaoPreferencias.consultar(int id)");
            System.out.println(ex);
        }

        return preferencia;
    }

    public List<PreferenciasBean> consultar() {
        String query = "SELECT idPreferencias, preferencia FROM preferencias";
        List<PreferenciasBean> preferencias = new ArrayList<PreferenciasBean>();

        try {
            Connection connection = Conexion.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(query);

            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                PreferenciasBean preferencia = new PreferenciasBean();
                preferencia.setIdPreferencias(resultSet.getInt("idPreferencias"));
                preferencia.setPreferencia(resultSet.getString("preferencia"));

                preferencias.add(preferencia);
            }
        } catch (SQLException ex) {
            System.out.println("DaoPreferencias.consultar(int id)");
            System.out.println(ex);
        }

        return preferencias;
    }
}
