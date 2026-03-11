
package pa.taller2.Main;

import pa.taller2.controlador.MinipigController;
import pa.taller2.DAO.IMinipigDAO;
import pa.taller2.DAO.MinipigDAOImpl;
import pa.taller2.modelo.ConexionBD;
import pa.taller2.vista.VentanaPrincipal;
import pa.taller2.util.ConfiguracionDB;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Clase principal que inicia la aplicación de Minipigs.
 * 
 * Responsabilidades:
 * - Configurar el entorno de la aplicación
 * - Inicializar las capas MVC (Modelo, Vista, Controlador)
 * - Establecer las conexiones entre componentes
 * - Cargar datos iniciales
 * - Iniciar la interfaz gráfica
 * 
 * IMPORTANTE: Esta clase solo debe contener el método main y la configuración
 * inicial. NO debe contener lógica de negocio, acceso a datos directo,
 * ni creación de objetos del modelo fuera de la configuración.
 * 
 * Aplica el principio de Responsabilidad Única (SRP):
 * - Solo se encarga de arrancar la aplicación
 * - No contiene lógica de negocio ni de presentación
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class Aplicacion {
    
    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Configurar el Look and Feel para mejor apariencia
        configurarLookAndFeel();
        
        // Inicializar la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    iniciarAplicacion();
                } catch (Exception e) {
                    manejarErrorInicializacion(e);
                }
            }
        });
    }
    
    /**
     * Configura el Look and Feel de la aplicación
     */
    private static void configurarLookAndFeel() {
        try {
            // Usar el Look and Feel del sistema operativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Configurar colores personalizados
            UIManager.put("Button.background", new java.awt.Color(70, 130, 180));
            UIManager.put("Button.foreground", java.awt.Color.WHITE);
            UIManager.put("Button.font", new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
            
        } catch (Exception e) {
            System.err.println("Error al configurar Look and Feel: " + e.getMessage());
            // Continuar con el Look and Feel por defecto
        }
    }
    
    /**
     * Inicia la aplicación configurando todas las capas
     */
    private static void iniciarAplicacion() {
        System.out.println("=== INICIANDO SISTEMA DE GESTIÓN DE MINIPIGS ===\n");
        
        // Mostrar configuración de BD
        ConfiguracionDB.mostrarConfiguracion();
        
        // Paso 1: Verificar conexión a base de datos
        if (!verificarConexionBD()) {
            JOptionPane.showMessageDialog(
                null,
                "No se pudo establecer conexión con la base de datos.\n" +
                "Verifique que MySQL esté ejecutándose y la configuración sea correcta.\n\n" +
                "Configuración actual:\n" +
                "URL: " + ConfiguracionDB.getUrl() + "\n" +
                "Usuario: " + ConfiguracionDB.getUsuario(),
                "Error de Conexión",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        // Paso 2: Inicializar DAO (Singleton)
        IMinipigDAO dao = MinipigDAOImpl.getInstance();
        
        // Paso 3: Inicializar Controlador
        MinipigController controlador = new MinipigController(dao);
        
        // Paso 4: Inicializar Vista
        VentanaPrincipal vista = new VentanaPrincipal();
        
        // Paso 5: Conectar Vista y Controlador
        controlador.setVista(vista);
        vista.setControlador(controlador);
        
        // Paso 6: Cargar datos iniciales desde properties
        cargarDatosIniciales(controlador);
        
        // Paso 7: Mostrar la ventana principal
        mostrarVentanaPrincipal(vista);
        
        System.out.println("\n=== APLICACIÓN INICIADA CORRECTAMENTE ===\n");
    }
    
    /**
     * Verifica la conexión a la base de datos
     * @return true si la conexión es exitosa
     */
    private static boolean verificarConexionBD() {
        try {
            ConexionBD conexion = ConexionBD.getInstance();
            conexion.getConexion();
            System.out.println("✓ Conexión a base de datos establecida");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("✗ Error de conexión a BD: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Carga los datos iniciales desde el archivo de propiedades
     * @param controlador Controlador de la aplicación
     */
    private static void cargarDatosIniciales(MinipigController controlador) {
        System.out.println("\n--- Cargando datos iniciales ---");
        
        try {
            controlador.cargarDatosIniciales();
            System.out.println("✓ Datos iniciales procesados");
        } catch (Exception e) {
            System.err.println("✗ Error al cargar datos iniciales: " + e.getMessage());
            e.printStackTrace();
            
            // Mostrar advertencia pero continuar
            JOptionPane.showMessageDialog(
                null,
                "Error al cargar datos iniciales, pero la aplicación continuará.\n" +
                "Detalles: " + e.getMessage(),
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    /**
     * Muestra la ventana principal
     * @param vista Ventana principal a mostrar
     */
    private static void mostrarVentanaPrincipal(VentanaPrincipal vista) {
        // Configurar comportamiento al cerrar
        vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Agregar listener para confirmar salida
        vista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmarSalida(vista);
            }
        });
        
        // Mostrar ventana
        vista.setVisible(true);
        System.out.println("✓ Interfaz gráfica inicializada");
    }
    
    /**
     * Confirma la salida de la aplicación
     * @param vista Ventana principal
     */
    private static void confirmarSalida(JFrame vista) {
        int opcion = JOptionPane.showConfirmDialog(
            vista,
            "¿Está seguro de salir de la aplicación?\n" +
            "Los datos estarán guardados en la base de datos.",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (opcion == JOptionPane.YES_OPTION) {
            cerrarAplicacion(vista);
        }
    }
    
    /**
     * Cierra la aplicación de forma ordenada
     * @param vista Ventana principal
     */
    private static void cerrarAplicacion(JFrame vista) {
        System.out.println("\n--- Cerrando aplicación ---");
        
        // Cerrar conexión a base de datos
        try {
            ConexionBD.getInstance().cerrarConexion();
            System.out.println("✓ Conexión a BD cerrada");
        } catch (Exception e) {
            System.err.println("✗ Error al cerrar conexión BD: " + e.getMessage());
        }
        
        System.out.println("✓ Aplicación finalizada");
        vista.dispose();
        System.exit(0);
    }
    
    /**
     * Maneja errores durante la inicialización
     * @param e Excepción ocurrida
     */
    private static void manejarErrorInicializacion(Exception e) {
        System.err.println("\n=== ERROR FATAL EN INICIALIZACIÓN ===");
        System.err.println("Tipo: " + e.getClass().getSimpleName());
        System.err.println("Mensaje: " + e.getMessage());
        e.printStackTrace();
        
        JOptionPane.showMessageDialog(
            null,
            "Error al iniciar la aplicación:\n" +
            e.getMessage() + "\n\n" +
            "La aplicación se cerrará.",
            "Error Fatal",
            JOptionPane.ERROR_MESSAGE
        );
        
        System.exit(1);
    }
}
