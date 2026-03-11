
package pa.taller2.modelo;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que maneja la conexión a la base de datos.
 * Implementa el patrón Singleton para garantizar una única instancia de conexión.
 * Esta clase actúa como DataSource en el patrón DAO.
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class ConexionBD {
    
    private static ConexionBD instancia;
    private Connection conexion;
    private Properties config;
    
    // Datos de conexión por defecto
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/minipigs_db";
    private static final String DEFAULT_USUARIO = "root";
    private static final String DEFAULT_PASSWORD = "";
    
    /**
     * Constructor privado para implementar Singleton
     */
    private ConexionBD() {
        this.config = new Properties();
        cargarConfiguracion();
    }
    
    /**
     * Obtiene la única instancia de ConexionBD
     * @return Instancia única de ConexionBD
     */
    public static synchronized ConexionBD getInstance() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }
    
    /**
     * Carga la configuración desde el archivo properties
     */
    private void cargarConfiguracion() {
        String rutaConfig = "data/config_db.properties";
        
        try (FileInputStream fis = new FileInputStream(rutaConfig)) {
            config.load(fis);
        } catch (IOException e) {
            // Si no se encuentra el archivo, usar valores por defecto
            System.err.println("No se pudo cargar el archivo de configuración: " + e.getMessage());
            System.err.println("Usando valores por defecto para la conexión BD");
            config.setProperty("db.url", DEFAULT_URL);
            config.setProperty("db.usuario", DEFAULT_USUARIO);
            config.setProperty("db.password", DEFAULT_PASSWORD);
        }
    }
    
    /**
     * Establece la conexión con la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si hay error en la conexión
     * @throws ClassNotFoundException si no encuentra el driver
     */
    public Connection getConexion() throws SQLException, ClassNotFoundException {
        String url = config.getProperty("db.url", DEFAULT_URL);
        String usuario = config.getProperty("db.usuario", DEFAULT_USUARIO);
        String password = config.getProperty("db.password", DEFAULT_PASSWORD);

        

        
        // Establecer conexión si no existe o está cerrada
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(url, usuario, password);
        }
        
        return conexion;
    }
    
    /**
     * Cierra la conexión con la base de datos
     */
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            } finally {
                conexion = null;
            }
        }
    }
    
    /**
     * Verifica si la conexión está activa
     * @return true si la conexión está activa, false en caso contrario
     */
    public boolean isConexionActiva() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
