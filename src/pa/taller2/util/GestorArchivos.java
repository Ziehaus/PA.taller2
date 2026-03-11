
package pa.taller2.util;

import pa.taller2.controlador.MinipigDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Clase utilitaria para manejar operaciones con archivos en la aplicación.
 * 
 * Propósito:
 * - Leer archivos de propiedades
 * - Procesar líneas de configuración
 * - Validar campos null en los datos
 * - Centralizar operaciones de E/S
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class GestorArchivos {
    
    // Constantes
    private static final String RUTA_PROPERTIES = "data/minipigs.properties";
    private static final String SEPARADOR = ",";
    private static final String NULL_INDICADOR = "null";
    
    /**
     * Constructor privado para evitar instanciación
     */
    private GestorArchivos() {
        // Clase utilitaria, no debe instanciarse
    }
    
    // ==================== OPERACIONES CON PROPERTIES ====================
    
    /**
     * Carga los minipigs desde el archivo de propiedades
     * @return Lista de DTOs con los datos del archivo
     * @throws IOException Si hay error al leer el archivo
     */
    public static List<MinipigDTO> cargarMinipigsDesdeProperties() throws IOException {
        List<MinipigDTO> minipigs = new ArrayList<>();
        Properties properties = new Properties();
        
        File archivo = new File(RUTA_PROPERTIES);
        if (!archivo.exists()) {
            System.err.println("Archivo de propiedades no encontrado: " + RUTA_PROPERTIES);
            System.err.println("Creando archivo de ejemplo...");
            crearArchivoEjemplo();
            return minipigs;
        }
        
        try (FileInputStream fis = new FileInputStream(archivo)) {
            properties.load(fis);
            
            // Recorrer las propiedades que empiezan con "Minipig"
            for (String key : properties.stringPropertyNames()) {
                if (key.startsWith("Minipig")) {
                    String valor = properties.getProperty(key);
                    MinipigDTO dto = procesarLineaPropiedad(key, valor);
                    if (dto != null) {
                        minipigs.add(dto);
                    }
                }
            }
        }
        
        return minipigs;
    }
    
    /**
     * Procesa una línea del archivo de propiedades
     * @param key Clave de la propiedad
     * @param linea Línea con los datos
     * @return DTO con los datos procesados
     */
    private static MinipigDTO procesarLineaPropiedad(String key, String linea) {
        if (linea == null || linea.trim().isEmpty()) {
            return null;
        }
        
        String[] partes = linea.split(SEPARADOR);
        if (partes.length < 11) {
            System.err.println("Línea mal formada en " + key + ": se esperaban 11 campos, se encontraron " + partes.length);
            return null;
        }
        
        // Limpiar espacios en blanco
        for (int i = 0; i < partes.length; i++) {
            partes[i] = partes[i].trim();
        }
        
        MinipigDTO dto = new MinipigDTO();
        
        // Asignar valores, manejando null
        dto.setCodigo(procesarCampo(partes[0]));
        dto.setNombre(procesarCampo(partes[1]));
        dto.setGenero(procesarCampo(partes[2]));
        dto.setIdMicrochip(procesarCampo(partes[3]));
        dto.setRaza(procesarCampo(partes[4]));
        dto.setColor(procesarCampo(partes[5]));
        
        // Campos numéricos
        dto.setPeso(Validador.convertirADouble(partes[6], 0.0));
        dto.setAltura(Validador.convertirADouble(partes[7], 0.0));
        
        dto.setCaracteristica1(procesarCampo(partes[8]));
        dto.setCaracteristica2(procesarCampo(partes[9]));
        dto.setUrlFoto(procesarCampo(partes[10]));
        
        return dto;
    }
    
    /**
     * Procesa un campo individual, convirtiendo "null" a null real
     * @param campo Campo a procesar
     * @return Valor procesado o null si era "null"
     */
    private static String procesarCampo(String campo) {
        if (campo == null || campo.equalsIgnoreCase(NULL_INDICADOR)) {
            return null;
        }
        return campo;
    }
    
    /**
     * Verifica qué campos de un DTO son null
     * @param dto DTO a verificar
     * @return Lista de nombres de campos que son null
     */
    public static List<String> verificarCamposNull(MinipigDTO dto) {
        List<String> camposNull = new ArrayList<>();
        
        if (dto == null) return camposNull;
        
        if (dto.getCodigo() == null) camposNull.add("Código");
        if (dto.getNombre() == null) camposNull.add("Nombre");
        if (dto.getGenero() == null) camposNull.add("Género");
        if (dto.getIdMicrochip() == null) camposNull.add("Microchip");
        if (dto.getRaza() == null) camposNull.add("Raza");
        if (dto.getColor() == null) camposNull.add("Color");
        if (dto.getPeso() == 0.0 && dto.getPeso() == 0.0) { 
            // No podemos saber si era null o realmente cero
        }
        if (dto.getAltura() == 0.0 && dto.getAltura() == 0.0) {
            // Similar al peso
        }
        if (dto.getCaracteristica1() == null) camposNull.add("Característica1");
        if (dto.getCaracteristica2() == null) camposNull.add("Característica2");
        if (dto.getUrlFoto() == null) camposNull.add("URL Foto");
        
        return camposNull;
    }
    
    /**
     * Genera un mensaje descriptivo con los campos incompletos de un minipig
     * @param dto DTO con los datos del minipig
     * @param camposNull Lista de campos null
     * @return Mensaje formateado con código, nombre y campos faltantes
     */
    public static String generarMensajeCamposIncompletos(MinipigDTO dto, List<String> camposNull) {
        StringBuilder mensaje = new StringBuilder();
        
        mensaje.append("⚠️ DATOS INCOMPLETOS\n");
        mensaje.append("━━━━━━━━━━━━━━━━━━━━━━\n");
        mensaje.append("Minipig: ").append(dto.getCodigo() != null ? dto.getCodigo() : "SIN CÓDIGO");
        
        if (dto.getNombre() != null) {
            mensaje.append(" - ").append(dto.getNombre());
        }
        
        mensaje.append("\n\nCampos incompletos:\n");
        
        for (int i = 0; i < camposNull.size(); i++) {
            mensaje.append("  ").append(i + 1).append(". ").append(camposNull.get(i)).append("\n");
        }
        
        mensaje.append("\nPor favor complete la información para continuar.");
        
        return mensaje.toString();
    }
    
    // ==================== OPERACIONES CON ARCHIVOS DE TEXTO ====================
    
    /**
     * Lee un archivo de texto línea por línea
     * @param ruta Ruta del archivo
     * @return Lista de líneas del archivo
     * @throws IOException Si hay error al leer
     */
    public static List<String> leerArchivoTexto(String ruta) throws IOException {
        List<String> lineas = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }
        
        return lineas;
    }
    
    /**
     * Escribe líneas en un archivo de texto
     * @param ruta Ruta del archivo
     * @param lineas Líneas a escribir
     * @param append true para agregar al final, false para sobrescribir
     * @throws IOException Si hay error al escribir
     */
    public static void escribirArchivoTexto(String ruta, List<String> lineas, boolean append) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, append))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        }
    }
    
    // ==================== OPERACIONES CON ARCHIVOS BINARIOS ====================
    
    /**
     * Guarda un objeto serializado en un archivo
     * @param objeto Objeto a guardar
     * @param ruta Ruta del archivo
     * @throws IOException Si hay error al guardar
     */
    public static void guardarObjeto(Serializable objeto, String ruta) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(objeto);
        }
    }
    
    /**
     * Carga un objeto serializado desde un archivo
     * @param ruta Ruta del archivo
     * @return Objeto cargado
     * @throws IOException Si hay error al cargar
     * @throws ClassNotFoundException Si la clase no existe
     */
    public static Object cargarObjeto(String ruta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return ois.readObject();
        }
    }
    
    // ==================== MÉTODOS DE UTILIDAD ====================
    
    /**
     * Crea un archivo de propiedades de ejemplo
     */
    private static void crearArchivoEjemplo() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        List<String> lineasEjemplo = new ArrayList<>();
        lineasEjemplo.add("# Archivo de propiedades para Minipigs");
        lineasEjemplo.add("# Formato: Minipig{numero}= {codigo}, {nombre}, {genero}, {id_microchip}, {raza}, {color}, {peso_kg}, {altura_cm}, {caracteristica1}, {caracteristica2}, {url_foto}");
        lineasEjemplo.add("");
        lineasEjemplo.add("Minipig1= M001, Rosita, HEMBRA, 75AF56, Juliana, Manchado, 32, 43, esbelto, atletico, file:///data/fotos/rosita.jpg");
        lineasEjemplo.add("Minipig2= M002, Einstein, MACHO, 89BC34, Göttingen, Negro, 38, null, saludable, docil, file:///data/fotos/einstein.jpg");
        lineasEjemplo.add("Minipig3= M003, Barrigon, MACHO, 12DE78, Vietnamita, Rosado, 45, 50, robusto, barrigon, file:///data/fotos/barrigon.jpg");
        
        try {
            escribirArchivoTexto(RUTA_PROPERTIES, lineasEjemplo, false);
            System.out.println("Archivo de ejemplo creado en: " + RUTA_PROPERTIES);
        } catch (IOException e) {
            System.err.println("Error al crear archivo de ejemplo: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si un archivo existe
     * @param ruta Ruta del archivo
     * @return true si existe
     */
    public static boolean archivoExiste(String ruta) {
        return new File(ruta).exists();
    }
    
    /**
     * Obtiene la extensión de un archivo
     * @param nombreArchivo Nombre del archivo
     * @return Extensión del archivo
     */
    public static String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null) return "";
        int lastIndexOf = nombreArchivo.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return nombreArchivo.substring(lastIndexOf);
    }
}