package Control.Logica;

import Control.DAO.MiniPigDAO;
import Modelo.Genero;
import Modelo.MiniPig;
import Modelo.MiniPigDTO;
import Modelo.Raza;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Julian, Miguel, Andres
 * @version 1.0
 */
public class MiniPigController {

    private static final String RUTA_PROPERTIES = "data/minipigs.properties";
    private final MiniPigDAO miniPigDAO;

    public MiniPigController() {
        this.miniPigDAO = new MiniPigDAO();
        cargarDatosIniciales();
    }

    /**
     * Carga los datos iniciales desde el archivo de propiedades. Inserta en la
     * BD los registros que no existan previamente. Si algún registro tiene
     * campos nulos, los imprime en consola para que el usuario los complete
     * manualmente en la interfaz.
     */
    public void cargarDatosIniciales() {
        try {
            ArrayList<MiniPig> incompletos = cargarDesdeProperties(RUTA_PROPERTIES);
            if (!incompletos.isEmpty()) {
                System.out.println("  MINIPIGS CON CAMPOS NULOS - COMPLETAR ");
                for (MiniPig m : incompletos) {
                    System.out.println(" - Codigo: " + m.getCodigo()
                            + " | Nombre: " + m.getNombre());
                }
                System.out.println("Por favor completelos manualmente en la interfaz.");
            }
        } catch (Exception e) {
            System.out.println("Advertencia: No se pudo cargar el archivo de propiedades.");
            System.out.println("Verifique que exista: " + RUTA_PROPERTIES);
        }
    }

    /**
     * Inserta un nuevo MiniPig en la base de datos.
     *
     * @param miniPig MiniPig a insertar.
     * @return {@code true} si se insertó correctamente.
     */
    public boolean insertar(MiniPig miniPig) {
        if (miniPig == null) {
            return false;
        }
        if (miniPig.getCodigo() == null || miniPig.getCodigo().trim().isEmpty()) {
            return false;
        }
        if (miniPig.getIdMicrochip() == null || miniPig.getIdMicrochip().trim().isEmpty()) {
            return false;
        }
        return miniPigDAO.insertar(miniPig);
    }

    /**
     * Consulta un MiniPig por su código único.
     *
     * @param codigo Código del MiniPig.
     * @return MiniPig encontrado, o {@code null} si no existe.
     */
    public MiniPig consultarPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }
        return miniPigDAO.consultarPorCodigo(codigo.trim());
    }

    /**
     * Consulta un MiniPig por el ID de su microchip.
     *
     * @param idMicrochip ID del microchip.
     * @return MiniPig encontrado, o {@code null} si no existe.
     */
    public MiniPig consultarPorMicrochip(String idMicrochip) {
        if (idMicrochip == null || idMicrochip.trim().isEmpty()) {
            return null;
        }
        return miniPigDAO.consultarPorMicrochip(idMicrochip.trim());
    }

    /**
     * Consulta todos los MiniPigs de una raza específica.
     *
     * @param raza Raza a filtrar.
     * @return Lista de MiniPigs de esa raza.
     */
    public ArrayList<MiniPig> consultarPorRaza(Raza raza) {
        if (raza == null) {
            return new ArrayList<>();
        }
        return miniPigDAO.consultarPorRaza(raza);
    }

    /**
     * Consulta todos los MiniPigs con un nombre específico.
     *
     * @param nombre Nombre a buscar.
     * @return Lista de MiniPigs con ese nombre.
     */
    public ArrayList<MiniPig> consultarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return miniPigDAO.consultarPorNombre(nombre.trim());
    }

    /**
     * Retorna todos los MiniPigs almacenados en la base de datos.
     *
     * @return Lista completa de MiniPigs.
     */
    public ArrayList<MiniPig> listarTodos() {
        return miniPigDAO.listarTodos();
    }

    /**
     * Elimina un MiniPig por su código.
     *
     * @param codigo Código del MiniPig a eliminar.
     * @return {@code true} si se eliminó.
     */
    public boolean eliminarPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }
        return miniPigDAO.eliminarPorCodigo(codigo.trim());
    }

    /**
     * Elimina un MiniPig por el ID de su microchip.
     *
     * @param idMicrochip ID del microchip del MiniPig a eliminar.
     * @return {@code true} si se eliminó.
     */
    public boolean eliminarPorMicrochip(String idMicrochip) {
        if (idMicrochip == null || idMicrochip.trim().isEmpty()) {
            return false;
        }
        return miniPigDAO.eliminarPorMicrochip(idMicrochip.trim());
    }

    /**
     * Inicia el proceso de modificación consultando el MiniPig desde la BD y
     * cargándolo en un DTO en memoria.
     *
     * @param codigo Código del MiniPig a modificar.
     * @return DTO cargado con los datos actuales, o {@code null} si no existe.
     */
    public MiniPigDTO iniciarModificacion(String codigo) {
        MiniPig miniPig = miniPigDAO.consultarPorCodigo(codigo);
        if (miniPig == null) {
            return null;
        }
        return new MiniPigDTO(miniPig);
    }

    /**
     * Confirma la modificación enviando el DTO actualizado al DAO.
     *
     * @param dto DTO con los datos modificados.
     * @return {@code true} si la modificación fue exitosa.
     */
    public boolean confirmarModificacion(MiniPigDTO dto) {
        if (dto == null) {
            return false;
        }
        return miniPigDAO.modificar(dto);
    }

    /**
     * Lista todos los MiniPigs por consola al salir del aplicativo.
     */
    public void listarPorConsola() {
        java.io.PrintStream out;
        try {
            out = new java.io.PrintStream(System.out, true, "UTF-8");
        } catch (Exception e) {
            out = System.out;
        }
        java.util.ArrayList<MiniPig> todos = miniPigDAO.listarTodos();
        out.println("   MINIPIGS REGISTRADOS EN EL SISTEMA  ");
        if (todos.isEmpty()) {
            out.println("No hay MiniPigs registrados.");
        } else {
            for (MiniPig m : todos) {
                out.println(m.toString());
            }
        }
        out.println("Total: " + todos.size() + " MiniPig(s).");
    }

    /**
     * Carga los MiniPigs desde el archivo de propiedades e inserta en la BD los
     * que no existan previamente.
     *
     * @param rutaArchivo Ruta del archivo .properties.
     * @return Lista de MiniPigs con campos nulos que necesitan completarse.
     */
    private ArrayList<MiniPig> cargarDesdeProperties(String rutaArchivo) {
        ArrayList<MiniPig> incompletos = new ArrayList<>();
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
            props.load(fis);

            for (String key : props.stringPropertyNames()) {
                String linea = props.getProperty(key);
                if (linea == null || linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",");
                if (datos.length < 11) {
                    continue;
                }

                boolean tieneNulos = false;
                for (String dato : datos) {
                    if (dato.trim().equalsIgnoreCase("null")) {
                        tieneNulos = true;
                        break;
                    }
                }

                MiniPig miniPig = construirMiniPig(datos);
                if (miniPig == null) {
                    continue;
                }

                if (tieneNulos) {
                    incompletos.add(miniPig);
                } else if (!miniPigDAO.existePorCodigo(miniPig.getCodigo())) {
                    miniPigDAO.insertar(miniPig);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de propiedades.", e);
        }

        return incompletos;
    }

    /**
     * Construye un MiniPig a partir de un arreglo de datos del properties.
     *
     * @param datos Arreglo de strings con los campos del MiniPig.
     * @return MiniPig construido, o {@code null} si los datos son inválidos.
     */
    private MiniPig construirMiniPig(String[] datos) {
        try {
            String codigo = datos[0].trim();
            String nombre = parsearCampo(datos[1]);
            Genero genero = Genero.valueOf(parsearCampo(datos[2]).toUpperCase());
            String chip = parsearCampo(datos[3]);
            Raza raza = Raza.valueOf(parsearCampo(datos[4]).toUpperCase());
            String color = parsearCampo(datos[5]);
            double peso = parsearDouble(datos[6]);
            double altura = parsearDouble(datos[7]);
            String carac1 = parsearCampo(datos[8]);
            String carac2 = parsearCampo(datos[9]);
            String urlFoto = parsearCampo(datos[10]);

            return new MiniPig(codigo, nombre, genero, chip,
                    raza, color, peso, altura, carac1, carac2, urlFoto);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parsea un campo de texto. Si es "null" retorna cadena vacía.
     *
     * @param valor Valor del campo.
     * @return Valor limpio o cadena vacía si es null.
     */
    private String parsearCampo(String valor) {
        String limpio = valor.trim();
        return limpio.equalsIgnoreCase("null") ? "" : limpio;
    }

    /**
     * Parsea un valor numérico eliminando unidades como kg o cm.
     *
     * @param valor Valor numérico como texto.
     * @return Valor double parseado, o 0.0 si es inválido.
     */
    private double parsearDouble(String valor) {
        try {
            return Double.parseDouble(
                    valor.trim().replaceAll("(?i)kg?|cm", "").trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
