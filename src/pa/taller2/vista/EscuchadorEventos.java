
package pa.taller2.vista;

import pa.taller2.controlador.MinipigController;
import pa.taller2.controlador.MinipigDTO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clase que maneja todos los eventos de la interfaz gráfica.
 * Aplica el principio de Responsabilidad Única (SRP):
 * - Solo se encarga de capturar y delegar eventos
 * - No contiene lógica de negocio
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class EscuchadorEventos implements ActionListener {
    
    private final VentanaPrincipal vista;
    private final MinipigController controlador;
    
    /**
     * Constructor del escuchador de eventos
     * @param vista Referencia a la ventana principal
     * @param controlador Referencia al controlador
     */
    public EscuchadorEventos(VentanaPrincipal vista, MinipigController controlador) {
        this.vista = vista;
        this.controlador = controlador;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        Object fuente = e.getSource();
        
        // Botones de búsqueda
        if (fuente == vista.getBtnBuscar()) {
            realizarBusqueda();
        }
        else if (fuente == vista.getBtnLimpiar()) {
            vista.limpiarCampos();
        }
        
        // Botones de acción
        else if (fuente == vista.getBtnNuevo()) {
            prepararNuevoMinipig();
        }
        else if (fuente == vista.getBtnInsertar()) {
            insertarMinipig();
        }
        else if (fuente == vista.getBtnModificar()) {
            modificarMinipig();
        }
        else if (fuente == vista.getBtnEliminar()) {
            eliminarMinipig();
        }
        else if (fuente == vista.getBtnSalir()) {
            salirAplicacion();
        }
        
        // Combo de selección
        else if (fuente == vista.getComboMinipigs()) {
            seleccionarMinipigCombo();
        }
    }
    
    /**
     * Realiza la búsqueda según el criterio seleccionado
     */
    private void realizarBusqueda() {
        String criterio = vista.getTxtBusqueda().getText().trim();
        if (criterio.isEmpty()) {
            vista.mostrarMensaje("Ingrese un criterio de búsqueda");
            return;
        }
        
        List<MinipigDTO> resultados = null;
        
        if (vista.getRbCodigo().isSelected()) {
            MinipigDTO minipig = controlador.consultarPorCodigo(criterio);
            if (minipig != null) {
                vista.mostrarMinipig(minipig);
            } else {
                vista.mostrarMensaje("No se encontró minipig con código: " + criterio);
            }
        }
        else if (vista.getRbMicrochip().isSelected()) {
            MinipigDTO minipig = controlador.consultarPorMicrochip(criterio);
            if (minipig != null) {
                vista.mostrarMinipig(minipig);
            } else {
                vista.mostrarMensaje("No se encontró minipig con microchip: " + criterio);
            }
        }
        else if (vista.getRbNombre().isSelected()) {
            resultados = controlador.consultarPorNombre(criterio);
            if (resultados != null && !resultados.isEmpty()) {
                mostrarResultadosBusqueda(resultados);
            } else {
                vista.mostrarMensaje("No se encontraron minipigs con nombre: " + criterio);
            }
        }
        else if (vista.getRbRaza().isSelected()) {
            resultados = controlador.consultarPorRaza(criterio);
            if (resultados != null && !resultados.isEmpty()) {
                mostrarResultadosBusqueda(resultados);
            } else {
                vista.mostrarMensaje("No se encontraron minipigs de raza: " + criterio);
            }
        }
    }
    
    /**
     * Muestra los resultados de una búsqueda múltiple
     * @param resultados Lista de minipigs encontrados
     */
    private void mostrarResultadosBusqueda(List<MinipigDTO> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("Se encontraron ").append(resultados.size()).append(" minipigs:\n\n");
        
        for (MinipigDTO m : resultados) {
            sb.append("• ").append(m.getCodigo())
              .append(" - ").append(m.getNombre())
              .append(" (").append(m.getRaza()).append(")\n");
        }
        
        JOptionPane.showMessageDialog(vista, sb.toString(), 
                                      "Resultados de búsqueda", 
                                      JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Prepara la interfaz para insertar un nuevo minipig
     */
    private void prepararNuevoMinipig() {
        vista.limpiarCampos();
        vista.setCamposEditables(true);

        // Ahora usamos los getters de la vista
        vista.getTxtCodigo().setEditable(true);
        vista.getTxtMicrochip().setEditable(true);
    }
    
    /**
     * Inserta un nuevo minipig
     */
    private void insertarMinipig() {
        MinipigDTO dto = vista.obtenerDatosFormulario();
        
        // Validaciones básicas
        if (dto.getCodigo().isEmpty() || dto.getNombre().isEmpty() || 
            dto.getGenero().isEmpty() || dto.getIdMicrochip().isEmpty() || 
            dto.getRaza().isEmpty()) {
            vista.mostrarMensaje("Todos los campos requeridos deben estar diligenciados");
            return;
        }
        
        if (controlador.insertarMinipig(dto)) {
            vista.mostrarMensaje("Minipig insertado exitosamente");
            vista.limpiarCampos();
            actualizarCombo();
        } else {
            vista.mostrarMensaje("Error al insertar el minipig. Verifique que código y microchip sean únicos.");
        }
    }
    
    /**
     * Modifica el minipig actual
     */
    private void modificarMinipig() {
        MinipigDTO dto = vista.obtenerDatosFormulario();
        
        if (dto.getCodigo().isEmpty()) {
            vista.mostrarMensaje("Debe consultar un minipig primero");
            return;
        }
        
        if (controlador.modificarMinipig(dto)) {
            vista.mostrarMensaje("Minipig modificado exitosamente");
            actualizarCombo();
        } else {
            vista.mostrarMensaje("Error al modificar el minipig");
        }
    }
    
    /**
     * Elimina el minipig actual
     */
    private void eliminarMinipig() {
        MinipigDTO dto = vista.obtenerDatosFormulario();
        
        if (dto.getCodigo().isEmpty()) {
            vista.mostrarMensaje("Debe consultar un minipig primero");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            vista, 
            "¿Está seguro de eliminar el minipig " + dto.getNombre() + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (controlador.eliminarMinipig(dto.getCodigo(), dto.getIdMicrochip())) {
                vista.mostrarMensaje("Minipig eliminado exitosamente");
                vista.limpiarCampos();
                actualizarCombo();
            } else {
                vista.mostrarMensaje("Error al eliminar el minipig");
            }
        }
    }
    
    /**
     * Maneja la selección en el combo box
     */
    private void seleccionarMinipigCombo() {
        JComboBox<String> combo = vista.getComboMinipigs();
        String seleccion = (String) combo.getSelectedItem();
        
        if (seleccion != null && !seleccion.equals("Seleccione un minipig...")) {
            String codigo = seleccion.split(" - ")[0];
            MinipigDTO minipig = controlador.consultarPorCodigo(codigo);
            if (minipig != null) {
                vista.mostrarMinipig(minipig);
                vista.setCamposEditables(false);
            }
        }
    }
    
    /**
     * Actualiza el combo box con los minipigs actuales
     */
    private void actualizarCombo() {
        List<MinipigDTO> minipigs = controlador.consultarTodos();
        vista.actualizarCombo(minipigs);
    }
    
    /**
     * Sale de la aplicación mostrando los datos por consola
     */
    private void salirAplicacion() {
        // Mostrar todos los minipigs por consola (requisito del taller)
        controlador.mostrarTodosConsola();
        
        int confirmacion = JOptionPane.showConfirmDialog(
            vista, 
            "¿Está seguro de salir?", 
            "Confirmar salida", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}