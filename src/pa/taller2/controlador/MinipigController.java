/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pa.taller2.controlador;


import pa.taller2.DAO.IMinipigDAO;
import pa.taller2.DAO.MinipigDAOImpl;
import pa.taller2.modelo.Minipig;
import pa.taller2.util.GestorArchivos;
import pa.taller2.util.Validador;
import pa.taller2.vista.IVista;
import pa.taller2.vista.VentanaInsercion;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador principal de la aplicación de Minipigs.
 * Actúa como BusinessObject en el patrón DAO y como Controlador en el patrón MVC.
 * 
 * Responsabilidades:
 * - Coordinar las operaciones entre la vista y el DAO
 * - Aplicar la lógica de negocio
 * - Manejar la transferencia de datos usando DTOs
 * - Gestionar la carga inicial de datos desde properties
 * 
 * Aplica los principios SOLID:
 * - SRP: Se encarga solo de la lógica de negocio y coordinación
 * - OCP: Abierto a extensión, cerrado a modificación (usa interfaces)
 * - DIP: Depende de abstracciones (IMinipigDAO, IVista)
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class MinipigController {
    
    private final IMinipigDAO dao;
    private IVista vista;
    private MinipigDTO minipigEnMemoria; // Para el patrón de modificación en memoria
    
    /**
     * Constructor del controlador
     * @param dao Implementación del DAO a utilizar
     */
    public MinipigController(IMinipigDAO dao) {
        this.dao = dao;
        this.minipigEnMemoria = null;
    }
    
    /**
     * Establece la vista asociada al controlador
     * @param vista Implementación de la vista
     */
    public void setVista(IVista vista) {
        this.vista = vista;
    }
    
    // ==================== OPERACIONES CRUD ====================
    
    /**
     * Inserta un nuevo minipig en la base de datos
     * @param dto DTO con los datos del minipig
     * @return true si la inserción fue exitosa
     */
    public boolean insertarMinipig(MinipigDTO dto) {
        // Validar datos
        if (!Validador.validarDTOCompleto(dto)) {
            if (vista != null) {
                vista.mostrarMensaje("Datos incompletos: " + 
                    String.join(", ", dto.getCamposFaltantes()));
            }
            return false;
        }
        
        if (!Validador.validarCodigo(dto.getCodigo())) {
            if (vista != null) vista.mostrarMensaje("Código inválido");
            return false;
        }
        
        if (!Validador.validarMicrochip(dto.getIdMicrochip())) {
            if (vista != null) vista.mostrarMensaje("ID de microchip inválido");
            return false;
        }
        
        // Verificar si ya existe
        if (dao.existePorCodigo(dto.getCodigo())) {
            if (vista != null) {
                vista.mostrarMensaje("Ya existe un minipig con el código: " + dto.getCodigo());
            }
            return false;
        }
        
        if (dao.existePorMicrochip(dto.getIdMicrochip())) {
            if (vista != null) {
                vista.mostrarMensaje("Ya existe un minipig con el microchip: " + dto.getIdMicrochip());
            }
            return false;
        }
        
        // Convertir DTO a entidad y guardar
        Minipig minipig = dto.toEntity();
        boolean resultado = dao.insertar(minipig);
        
        if (resultado && vista != null) {
            vista.actualizarCombo(consultarTodos());
        }
        
        return resultado;
    }
    
    /**
     * Consulta un minipig por su código
     * @param codigo Código del minipig
     * @return DTO con los datos o null si no existe
     */
    public MinipigDTO consultarPorCodigo(String codigo) {
        if (!Validador.validarCodigo(codigo)) {
            if (vista != null) vista.mostrarMensaje("Código inválido");
            return null;
        }
        
        Minipig minipig = dao.buscarPorCodigo(codigo);
        if (minipig != null) {
            minipigEnMemoria = MinipigDTO.fromEntity(minipig);
            return minipigEnMemoria;
        }
        
        return null;
    }
    
    /**
     * Consulta un minipig por su ID de microchip
     * @param idMicrochip ID del microchip
     * @return DTO con los datos o null si no existe
     */
    public MinipigDTO consultarPorMicrochip(String idMicrochip) {
        if (!Validador.validarMicrochip(idMicrochip)) {
            if (vista != null) vista.mostrarMensaje("ID de microchip inválido");
            return null;
        }
        
        Minipig minipig = dao.buscarPorMicrochip(idMicrochip);
        if (minipig != null) {
            minipigEnMemoria = MinipigDTO.fromEntity(minipig);
            return minipigEnMemoria;
        }
        
        return null;
    }
    
    /**
     * Consulta minipigs por su raza
     * @param raza Nombre de la raza
     * @return Lista de DTOs de minipigs de esa raza
     */
    public List<MinipigDTO> consultarPorRaza(String raza) {
        if (raza == null || raza.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Minipig> minipigs = dao.buscarPorRaza(raza);
        return convertirLista(minipigs);
    }
    
    /**
     * Consulta minipigs por su nombre
     * @param nombre Nombre a buscar
     * @return Lista de DTOs de minipigs con ese nombre
     */
    public List<MinipigDTO> consultarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Minipig> minipigs = dao.buscarPorNombre(nombre);
        return convertirLista(minipigs);
    }
    
    /**
     * Consulta todos los minipigs
     * @return Lista de DTOs de todos los minipigs
     */
    public List<MinipigDTO> consultarTodos() {
        List<Minipig> minipigs = dao.listarTodos();
        return convertirLista(minipigs);
    }
    
    /**
     * Elimina un minipig por código o microchip
     * @param codigo Código del minipig
     * @param idMicrochip ID del microchip
     * @return true si la eliminación fue exitosa
     */
    public boolean eliminarMinipig(String codigo, String idMicrochip) {
        if (codigo == null || codigo.trim().isEmpty()) {
            if (vista != null) vista.mostrarMensaje("Debe especificar el código");
            return false;
        }
        
        // Verificar que existe
        if (!dao.existePorCodigo(codigo)) {
            if (vista != null) vista.mostrarMensaje("No existe minipig con código: " + codigo);
            return false;
        }
        
        boolean resultado = dao.eliminar(codigo);
        
        if (resultado && vista != null) {
            minipigEnMemoria = null;
            vista.actualizarCombo(consultarTodos());
        }
        
        return resultado;
    }
    
    /**
     * Modifica un minipig existente
     * Patrón: Modificación en memoria -> Luego a BD
     * @param dto DTO con los datos modificados
     * @return true si la modificación fue exitosa
     */
    public boolean modificarMinipig(MinipigDTO dto) {
        // Verificar que tenemos el objeto en memoria
        if (minipigEnMemoria == null) {
            // Buscar el minipig primero
            MinipigDTO existente = consultarPorCodigo(dto.getCodigo());
            if (existente == null) {
                if (vista != null) {
                    vista.mostrarMensaje("Debe consultar el minipig antes de modificarlo");
                }
                return false;
            }
        }
        
        // Validar datos (excepto código y microchip que no se modifican)
        if (!Validador.validarNombre(dto.getNombre())) {
            if (vista != null) vista.mostrarMensaje("Nombre inválido");
            return false;
        }
        
        // Actualizar el objeto en memoria
        actualizarDTOEnMemoria(dto);
        
        // Guardar en base de datos
        Minipig minipig = minipigEnMemoria.toEntity();
        boolean resultado = dao.actualizar(minipig);
        
        if (resultado && vista != null) {
            vista.actualizarCombo(consultarTodos());
            vista.mostrarMensaje("Minipig modificado exitosamente");
        }
        
        return resultado;
    }
    
    /**
     * Actualiza el DTO en memoria con los nuevos valores
     * @param dto DTO con los valores a actualizar
     */
    private void actualizarDTOEnMemoria(MinipigDTO dto) {
        if (minipigEnMemoria == null) {
            minipigEnMemoria = new MinipigDTO();
            minipigEnMemoria.setCodigo(dto.getCodigo());
            minipigEnMemoria.setIdMicrochip(dto.getIdMicrochip());
        }
        
        minipigEnMemoria.setNombre(dto.getNombre());
        minipigEnMemoria.setGenero(dto.getGenero());
        minipigEnMemoria.setRaza(dto.getRaza());
        minipigEnMemoria.setColor(dto.getColor());
        minipigEnMemoria.setPeso(dto.getPeso());
        minipigEnMemoria.setAltura(dto.getAltura());
        minipigEnMemoria.setCaracteristica1(dto.getCaracteristica1());
        minipigEnMemoria.setCaracteristica2(dto.getCaracteristica2());
        minipigEnMemoria.setUrlFoto(dto.getUrlFoto());
    }
    
    // ==================== CARGA DE DATOS INICIALES ====================
    
    /**
     * Carga los datos iniciales desde el archivo de propiedades
     * Maneja campos null solicitando ingreso manual con información clara del minipig
     */
    public void cargarDatosIniciales() {
        try {
            List<MinipigDTO> minipigsIniciales = GestorArchivos.cargarMinipigsDesdeProperties();
            int insertados = 0;
            int pendientes = 0;

            if (minipigsIniciales.isEmpty()) {
                if (vista != null) {
                    vista.mostrarMensaje("No se encontraron datos iniciales en el archivo properties");
                }
                return;
            }

            System.out.println("\n📋 PROCESANDO CARGA INICIAL DE MINIPIGS");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            for (MinipigDTO dto : minipigsIniciales) {
                // Mostrar información del minipig que se está procesando
                System.out.println("\n▶ Procesando: " + 
                    (dto.getCodigo() != null ? dto.getCodigo() : "???") + 
                    " - " + 
                    (dto.getNombre() != null ? dto.getNombre() : "Sin nombre"));

                // Verificar si tiene campos null
                List<String> camposNull = GestorArchivos.verificarCamposNull(dto);

                if (!camposNull.isEmpty() && vista != null) {
                    // Generar mensaje descriptivo con código y nombre
                    String mensaje = GestorArchivos.generarMensajeCamposIncompletos(dto, camposNull);

                    System.out.println("  ⚠ Campos incompletos detectados: " + camposNull.size());

                    // Mostrar diálogo con información clara
                    JFrame frame = (JFrame) vista;
                    int opcion = JOptionPane.showConfirmDialog(
                        frame,
                        mensaje + "\n\n¿Desea completar los datos ahora?",
                        "Datos Incompletos - " + (dto.getCodigo() != null ? dto.getCodigo() : "Minipig"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        VentanaInsercion ventana = new VentanaInsercion(frame, this, dto, camposNull);
                        ventana.setVisible(true);

                        if (ventana.isGuardado()) {
                            dto = ventana.getMinipigCompleto();
                            System.out.println("  ✅ Campos completados manualmente");
                        } else {
                            pendientes++;
                            System.out.println("  ⏸ Pendiente para después");
                            continue;
                        }
                    } else {
                        pendientes++;
                        System.out.println("  ⏸ Pendiente para después (usuario canceló)");
                        continue;
                    }
                }

                // Insertar en base de datos
                if (insertarMinipig(dto)) {
                    insertados++;
                    System.out.println("  ✅ Insertado exitosamente");
                } else {
                    System.out.println("  ❌ Error al insertar");
                }
            }

            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("📊 RESUMEN DE CARGA INICIAL");
            System.out.println("  ✅ Insertados: " + insertados);
            System.out.println("  ⏸ Pendientes: " + pendientes);
            System.out.println("  📦 Total procesados: " + minipigsIniciales.size());
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            if (vista != null) {
                vista.mostrarMensaje("📊 CARGA INICIAL COMPLETADA\n\n" +
                    "✅ Insertados: " + insertados + "\n" +
                    "⏸ Pendientes: " + pendientes + "\n\n" +
                    "Los minipigs pendientes podrán ser insertados manualmente después.");

                vista.actualizarCombo(consultarTodos());
            }

        } catch (Exception e) {
            System.err.println("❌ Error al cargar datos iniciales: " + e.getMessage());
            e.printStackTrace();

            if (vista != null) {
                vista.mostrarMensaje("ERROR al cargar datos iniciales:\n" + e.getMessage());
            }
        }
    }
    
    // ==================== UTILIDADES ====================
    
    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param minipigs Lista de entidades Minipig
     * @return Lista de DTOs correspondiente
     */
    private List<MinipigDTO> convertirLista(List<Minipig> minipigs) {
        List<MinipigDTO> lista = new ArrayList<>();
        for (Minipig m : minipigs) {
            lista.add(MinipigDTO.fromEntity(m));
        }
        return lista;
    }
    
    /**
     * Muestra todos los minipigs por consola (requisito del taller)
     */
    public void mostrarTodosConsola() {
        System.out.println("\n========== MINIPIGS ALMACENADOS ==========");
        List<MinipigDTO> minipigs = consultarTodos();
        
        if (minipigs.isEmpty()) {
            System.out.println("No hay minipigs registrados.");
        } else {
            System.out.println("Total: " + minipigs.size() + " minipigs\n");
            
            for (MinipigDTO m : minipigs) {
                System.out.println("Código: " + m.getCodigo());
                System.out.println("Nombre: " + m.getNombre());
                System.out.println("Género: " + m.getGenero());
                System.out.println("Microchip: " + m.getIdMicrochip());
                System.out.println("Raza: " + m.getRaza());
                System.out.println("Color: " + m.getColor());
                System.out.println("Peso: " + m.getPeso() + " kg");
                System.out.println("Altura: " + m.getAltura() + " cm");
                System.out.println("Características: " + m.getCaracteristica1() + 
                                 (m.getCaracteristica2() != null && !m.getCaracteristica2().isEmpty() ? 
                                 ", " + m.getCaracteristica2() : ""));
                System.out.println("Foto: " + (m.getUrlFoto() != null ? m.getUrlFoto() : "No disponible"));
                System.out.println("----------------------------------------");
            }
        }
        System.out.println("==========================================\n");
    }
    
    /**
     * Obtiene el DTO actual en memoria
     * @return DTO del minipig actual
     */
    public MinipigDTO getMinipigEnMemoria() {
        return minipigEnMemoria;
    }
    
    /**
     * Limpia el DTO en memoria
     */
    public void limpiarMemoria() {
        this.minipigEnMemoria = null;
    }
}
