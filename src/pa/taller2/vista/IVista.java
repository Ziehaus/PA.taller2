
package pa.taller2.vista;

import java.util.List;
import pa.taller2.controlador.MinipigDTO;

/**
 * Interfaz que define el contrato que debe cumplir cualquier implementación de la vista.
 * Aplica el principio de Inversión de Dependencias (DIP) de SOLID:
 * - El controlador depende de esta abstracción, no de una implementación concreta
 * 
 * @author Universidad Distrital
 * @version 1.0
    */
   public interface IVista {

       /**
        * Muestra los datos de un minipig en la interfaz
        * @param minipig DTO con los datos del minipig a mostrar
        */
       void mostrarMinipig(MinipigDTO minipig);

       /**
        * Limpia todos los campos de texto de la interfaz
        */
       void limpiarCampos();

       /**
        * Actualiza el combo box con la lista de minipigs
        * @param minipigs Lista de DTOs para actualizar el combo
        */
       void actualizarCombo(List<MinipigDTO> minipigs);

       /**
        * Muestra un mensaje al usuario
        * @param mensaje Texto del mensaje a mostrar
        */
       void mostrarMensaje(String mensaje);

       /**
        * Obtiene los datos ingresados en el formulario
        * @return DTO con los datos del formulario
        */
       MinipigDTO obtenerDatosFormulario();

       /**
        * Habilita o deshabilita los campos para modificación
        * @param editable true para edición, false para solo lectura
        */
       void setCamposEditables(boolean editable);
   }
