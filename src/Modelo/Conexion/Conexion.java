package Modelo.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase Singleton que gestiona la conexión a la base de datos MySQL.
 * Se comunica con: MiniPigDAO que la usa para obtener la conexión activa.
 *
 * @author Julian, Miguel, Andres
 * @version 1.0
 */
public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/minipigs_db";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";
    private static Connection con = null;

    /**
     * Retorna la conexión activa a la base de datos.
     * Si no existe o está cerrada, la crea nuevamente.
     *
     * @return Conexión activa a MySQL.
     */
    public static Connection getConexion() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(URL, USUARIO, CLAVE);
            }
        } catch (ClassNotFoundException | SQLException e) {
        }
        return con;
    }

    /**
     * Cierra la conexión activa a la base de datos.
     */
    public static void desconectar() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
        }
    }
}