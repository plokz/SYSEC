package Utilerias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Conexion {

    private static String ipAddress, dbName, user, pass, portService;
    private static ResourceBundle propertiesDB;

    public static Connection getConnection() throws SQLException {
        try {
            //Definicion de la conexion, con el driver tipo Mysql
            Class.forName("com.mysql.jdbc.Driver");
//              Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (propertiesDB == null) {
            propertiesDB = ResourceBundle.getBundle("db");//nombre del archivo de propiedades
            ipAddress = propertiesDB.getString("ip_address");
            dbName = propertiesDB.getString("db_name");
            user = propertiesDB.getString("user");
            pass = propertiesDB.getString("password");
            portService = propertiesDB.getString("port_service");
        }
        //retornar una url, de las variables definidas en el properties para ver donde esta alojada la base de datos
        return DriverManager.getConnection("jdbc:mysql://" + ipAddress + ":" + portService + "/" + dbName, user, pass);
//      return DriverManager.getConnection("jdbc:sqlserver://" + ipAddress + ":" + portService + ";databaseName=" + dbName, user, pass);
    }

    public static void main(String[] args) throws SQLException {
        try {
            getConnection();
            System.out.println("Conexiòn exitosa");
        } catch (SQLException e) {
            System.out.println("La conexiòn no se pudo establecer" + e);
        }

    }
}
