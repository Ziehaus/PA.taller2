/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pa.taller2.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase utilitaria para manejar la configuración de la base de datos.
 * 
 * Propósito:
 * - Cargar configuración desde archivo properties
 * - Proveer acceso centralizado a parámetros de BD
 * - Manejar valores por defecto
 * 
 * Aplica el principio de Responsabilidad Única (SRP):
 * - Solo se encarga de la configuración de BD
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class ConfiguracionDB {
    
    private static final String RUTA_CONFIG = "data/config_db.properties";
    private static Properties configuracion;
    
    // Valores por defecto
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/minipigs_db";
    private static final String DEFAULT_USUARIO = "root";
    private static final String DEFAULT_PASSWORD = "";
    private static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    static {
        cargarConfiguracion();
    }
    
    /**
     * Constructor privado para evitar instanciación
     */
    private ConfiguracionDB() {
        // Clase utilitaria, no debe instanciarse
    }
    
    /**
     * Carga la configuración desde el archivo properties
     */
    private static void cargarConfiguracion() {
        configuracion = new Properties();
        
        try (FileInputStream fis = new FileInputStream(RUTA_CONFIG)) {
            configuracion.load(fis);
            System.out.println("Configuración de BD cargada desde: " + RUTA_CONFIG);
        } catch (IOException e) {
            System.err.println("No se pudo cargar archivo de configuración: " + e.getMessage());
            System.err.println("Usando valores por defecto");
            usarValoresPorDefecto();
        }
    }
    
    /**
     * Establece valores por defecto
     */
    private static void usarValoresPorDefecto() {
        configuracion.setProperty("db.url", DEFAULT_URL);
        configuracion.setProperty("db.usuario", DEFAULT_USUARIO);
        configuracion.setProperty("db.password", DEFAULT_PASSWORD);
        configuracion.setProperty("db.driver", DEFAULT_DRIVER);
    }
    
    /**
     * Obtiene la URL de conexión
     * @return URL de la base de datos
     */
    public static String getUrl() {
        return configuracion.getProperty("db.url", DEFAULT_URL);
    }
    
    /**
     * Obtiene el usuario de conexión
     * @return Usuario de BD
     */
    public static String getUsuario() {
        return configuracion.getProperty("db.usuario", DEFAULT_USUARIO);
    }
    
    /**
     * Obtiene la contraseña de conexión
     * @return Contraseña de BD
     */
    public static String getPassword() {
        return configuracion.getProperty("db.password", DEFAULT_PASSWORD);
    }
    
    /**
     * Obtiene el driver JDBC
     * @return Clase del driver
     */
    public static String getDriver() {
        return configuracion.getProperty("db.driver", DEFAULT_DRIVER);
    }
    
    /**
     * Obtiene un parámetro específico de configuración
     * @param clave Clave del parámetro
     * @param valorPorDefecto Valor por defecto si no existe
     * @return Valor del parámetro
     */
    public static String getParametro(String clave, String valorPorDefecto) {
        return configuracion.getProperty(clave, valorPorDefecto);
    }
    
    /**
     * Recarga la configuración desde el archivo
     */
    public static void recargarConfiguracion() {
        cargarConfiguracion();
    }
    
    /**
     * Muestra la configuración actual por consola
     */
    public static void mostrarConfiguracion() {
        System.out.println("\n=== Configuración BD ===");
        System.out.println("URL: " + getUrl());
        System.out.println("Usuario: " + getUsuario());
        System.out.println("Driver: " + getDriver());
        System.out.println("========================\n");
    }
}
