package Vista;

import Modelo.Genero;
import Modelo.MiniPig;
import Modelo.MiniPigDTO;
import Modelo.Raza;
import Control.Logica.MiniPigController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Listener principal de la vista. Implementa {@link ActionListener} para
 * detectar eventos de los componentes gráficos y ejecutar la acción
 * correspondiente delegando al controlador.
 *
 * <p>
 * Se comunica con: {@link VistaPrincipal} para leer/escribir datos, y con
 * {@link MiniPigController} para ejecutar la lógica de negocio.</p>
 *
 * @author Julian, Miguel, Andres
 * @version 1.0
 */
public class ListenerPrincipal implements ActionListener {

    /**
     * Vista principal desde donde se leen y escriben los datos.
     */
    private final VistaPrincipal vista;

    /**
     * Controlador que ejecuta la lógica de negocio.
     */
    private final MiniPigController controller;

    /**
     * DTO en memoria para la operación de modificación.
     */
    private MiniPigDTO dtoEnMemoria;

    /**
     * Bandera para ignorar eventos del combo mientras se está cargando.
     */
    private boolean cargandoCombo = false;

    /**
     * Construye el listener con la vista y el controlador.
     *
     * @param vista Vista principal.
     * @param controller Controlador del aplicativo.
     */
    public ListenerPrincipal(VistaPrincipal vista, MiniPigController controller) {
        this.vista = vista;
        this.controller = controller;
        actualizarCombo();
    }

    /**
     * Detecta qué componente disparó el evento y delega a la acción
     * correspondiente (performed).
     *
     * @param e Evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();

        if (fuente == vista.btnInsertar) {
            accionInsertar();
        } else if (fuente == vista.btnConsultarCodigo) {
            accionConsultarPorCodigo();
        } else if (fuente == vista.btnConsultarMicrochip) {
            accionConsultarPorMicrochip();
        } else if (fuente == vista.btnConsultarRaza) {
            accionConsultarPorRaza();
        } else if (fuente == vista.btnConsultarNombre) {
            accionConsultarPorNombre();
        } else if (fuente == vista.btnEliminarCodigo) {
            accionEliminarPorCodigo();
        } else if (fuente == vista.btnEliminarMicrochip) {
            accionEliminarPorMicrochip();
        } else if (fuente == vista.btnModificar) {
            accionIniciarModificacion();
        } else if (fuente == vista.btnConfirmarModificacion) {
            accionConfirmarModificacion();
        } else if (fuente == vista.btnLimpiar) {
            accionLimpiar();
        } else if (fuente == vista.btnSalir) {
            accionSalir();
        } else {
            accionSeleccionarCombo();
        }
    }

    /**
     * Acción del botón Insertar. Lee los datos de la vista, construye el objeto
     * y llama al controlador.
     */
    private void accionInsertar() {
        try {
            MiniPig nuevo = construirMiniPigDesdeVista();
            if (nuevo == null) {
                return;
            }

            boolean exito = controller.insertar(nuevo);
            if (exito) {
                vista.setMensaje("MiniPig insertado correctamente.", false);
                actualizarCombo();
                vista.limpiarCampos();
            } else {
                vista.setMensaje("Error: Ya existe un MiniPig con ese código o microchip.", true);
            }
        } catch (Exception ex) {
            vista.setMensaje("Error al insertar: " + ex.getMessage(), true);
        }
    }

    /**
     * Acción del botón Buscar por Código.
     */
    private void accionConsultarPorCodigo() {
        String codigo = vista.getCodigo();
        if (codigo.isEmpty()) {
            vista.setMensaje("Ingrese un código para buscar.", true);
            return;
        }
        MiniPig encontrado = controller.consultarPorCodigo(codigo);
        if (encontrado != null) {
            mostrarMiniPigEnVista(encontrado);
            vista.setMensaje("MiniPig encontrado.", false);
        } else {
            vista.setMensaje("No se encontró ningún MiniPig con ese código.", true);
        }
    }

    /**
     * Acción del botón Buscar por Microchip.
     */
    private void accionConsultarPorMicrochip() {
        String chip = vista.getMicrochip();
        if (chip.isEmpty()) {
            vista.setMensaje("Ingrese un ID de microchip para buscar.", true);
            return;
        }
        MiniPig encontrado = controller.consultarPorMicrochip(chip);
        if (encontrado != null) {
            mostrarMiniPigEnVista(encontrado);
            vista.setMensaje("MiniPig encontrado.", false);
        } else {
            vista.setMensaje("No se encontró ningún MiniPig con ese microchip.", true);
        }
    }

    /**
     * Acción del botón Buscar por Raza.
     */
    private void accionConsultarPorRaza() {
        try {
            Raza raza = Raza.valueOf(vista.getRazaSeleccionada());
            List<MiniPig> lista = controller.consultarPorRaza(raza);
            if (lista.isEmpty()) {
                vista.setMensaje("No hay MiniPigs de esa raza.", true);
                vista.mostrarResultados("");
            } else {
                vista.mostrarResultados(construirTextoLista(lista));
                vista.setMensaje(lista.size() + " MiniPig(s) encontrado(s).", false);
            }
        } catch (Exception ex) {
            vista.setMensaje("Error al buscar por raza: " + ex.getMessage(), true);
        }
    }

    /**
     * Acción del botón Buscar por Nombre.
     */
    private void accionConsultarPorNombre() {
        String nombre = vista.getNombre();
        if (nombre.isEmpty()) {
            vista.setMensaje("Ingrese un nombre para buscar.", true);
            return;
        }
        List<MiniPig> lista = controller.consultarPorNombre(nombre);
        if (lista.isEmpty()) {
            vista.setMensaje("No se encontró ningún MiniPig con ese nombre.", true);
            vista.mostrarResultados("");
        } else {
            vista.mostrarResultados(construirTextoLista(lista));
            vista.setMensaje(lista.size() + " MiniPig(s) encontrado(s).", false);
        }
    }

    /**
     * Acción del botón Eliminar por Código.
     */
    private void accionEliminarPorCodigo() {
        String codigo = vista.getCodigo();
        if (codigo.isEmpty()) {
            vista.setMensaje("Ingrese el código del MiniPig a eliminar.", true);
            return;
        }
        boolean exito = controller.eliminarPorCodigo(codigo);
        if (exito) {
            vista.setMensaje("MiniPig eliminado correctamente.", false);
            actualizarCombo();
            vista.limpiarCampos();
        } else {
            vista.setMensaje("No se encontró ningún MiniPig con ese código.", true);
        }
    }

    /**
     * Acción del botón Eliminar por Microchip.
     */
    private void accionEliminarPorMicrochip() {
        String chip = vista.getMicrochip();
        if (chip.isEmpty()) {
            vista.setMensaje("Ingrese el ID de microchip del MiniPig a eliminar.", true);
            return;
        }
        boolean exito = controller.eliminarPorMicrochip(chip);
        if (exito) {
            vista.setMensaje("MiniPig eliminado correctamente.", false);
            actualizarCombo();
            vista.limpiarCampos();
        } else {
            vista.setMensaje("No se encontró ningún MiniPig con ese microchip.", true);
        }
    }

    /**
     * Acción del botón Modificar. Carga el MiniPig en el DTO en memoria y
     * habilita los campos editables.
     */
    private void accionIniciarModificacion() {
        String codigo = vista.getCodigo();
        if (codigo.isEmpty()) {
            vista.setMensaje("Primero busque el MiniPig a modificar por código.", true);
            return;
        }
        dtoEnMemoria = controller.iniciarModificacion(codigo);
        if (dtoEnMemoria == null) {
            vista.setMensaje("No se encontró el MiniPig. Búsquelo primero.", true);
            return;
        }
        mostrarMiniPigEnVista(dtoEnMemoria.toMiniPig());
        vista.habilitarCamposModificacion(true);
        vista.setMensaje("Modifique los campos y presione Confirmar Modificación.", false);
    }

    /**
     * Acción del botón Confirmar Modificación. Actualiza el DTO en memoria con
     * los nuevos datos y lo envía al controlador.
     */
    private void accionConfirmarModificacion() {
        if (dtoEnMemoria == null) {
            vista.setMensaje("Inicie la modificación primero.", true);
            return;
        }
        try {
            actualizarDTODesdeVista(dtoEnMemoria);
            boolean exito = controller.confirmarModificacion(dtoEnMemoria);
            if (exito) {
                vista.setMensaje("MiniPig modificado correctamente.", false);
                vista.habilitarCamposModificacion(false);
                actualizarCombo();
                dtoEnMemoria = null;
            } else {
                vista.setMensaje("No se pudo modificar el MiniPig.", true);
            }
        } catch (Exception ex) {
            vista.setMensaje("Error al modificar: " + ex.getMessage(), true);
        }
    }

    /**
     * Acción del botón Limpiar.
     */
    private void accionLimpiar() {
        vista.limpiarCampos();
        vista.habilitarTodosCampos();
        dtoEnMemoria = null;
    }

    /**
     * Acción del botón Salir y del cierre de ventana. Lista todos los MiniPigs
     * por consola antes de cerrar.
     */
    public void accionSalir() {
        controller.listarPorConsola();
        System.exit(0);
    }

    /**
     * Acción de selección en el combo de MiniPigs. Carga los datos del MiniPig
     * seleccionado en el formulario.
     */
    private void accionSeleccionarCombo() {
        if (cargandoCombo) {
            return;
        }
        String seleccionado = vista.getComboSeleccionado();
        if (seleccionado == null || seleccionado.startsWith("--")) {
            return;
        }

        String codigo = seleccionado.split(" - ")[0].trim();
        MiniPig encontrado = controller.consultarPorCodigo(codigo);
        if (encontrado != null) {
            mostrarMiniPigEnVista(encontrado);
            vista.setMensaje("MiniPig cargado desde el combo.", false);
        }
    }

    /**
     * Construye un objeto {@link MiniPig} leyendo los datos de la vista.
     *
     * @return MiniPig construido, o {@code null} si hay error de formato.
     */
    private MiniPig construirMiniPigDesdeVista() {
        try {
            String codigo = vista.getCodigo();
            String nombre = vista.getNombre();
            Genero genero = Genero.valueOf(vista.getGeneroSeleccionado());
            String chip = vista.getMicrochip();
            Raza raza = Raza.valueOf(vista.getRazaSeleccionada());
            String color = vista.getColor();
            double peso = Double.parseDouble(vista.getPeso());
            double altura = Double.parseDouble(vista.getAltura());
            String carac1 = vista.getCaracteristica1();
            String carac2 = vista.getCaracteristica2();
            String urlFoto = "data/fotos/" + raza.name().toLowerCase() + ".jpg";

            if (codigo.isEmpty() || chip.isEmpty()) {
                vista.setMensaje("Código y microchip son obligatorios.", true);
                return null;
            }
            return new MiniPig(codigo, nombre, genero, chip,
                    raza, color, peso, altura, carac1, carac2, urlFoto);
        } catch (NumberFormatException ex) {
            vista.setMensaje("Peso y Altura deben ser números válidos.", true);
            return null;
        }
    }

    /**
     * Actualiza un {@link MiniPigDTO} en memoria con los datos actuales de la
     * vista.
     *
     * @param dto DTO a actualizar.
     */
    private void actualizarDTODesdeVista(MiniPigDTO dto) {
        Raza raza = Raza.valueOf(vista.getRazaSeleccionada());
        dto.setNombre(vista.getNombre());
        dto.setGenero(Genero.valueOf(vista.getGeneroSeleccionado()));
        dto.setRaza(raza);
        dto.setColor(vista.getColor());
        dto.setPeso(Double.parseDouble(vista.getPeso()));
        dto.setAltura(Double.parseDouble(vista.getAltura()));
        dto.setCaracteristica1(vista.getCaracteristica1());
        dto.setCaracteristica2(vista.getCaracteristica2());
        dto.setUrlFoto("data/fotos/" + raza.name().toLowerCase() + ".jpg");
    }

    /**
     * Muestra los datos de un MiniPig en la vista convirtiendo a Strings. Los
     * objetos del modelo no pasan a la vista directamente.
     *
     * @param m MiniPig a mostrar.
     */
    private void mostrarMiniPigEnVista(MiniPig m) {
        vista.mostrarDatos(m.getCodigo(), m.getNombre(), m.getGenero().name(), m.getIdMicrochip(), m.getRaza().name(), m.getColor(), String.valueOf(m.getPeso()), String.valueOf(m.getAltura()), m.getCaracteristica1(), m.getCaracteristica2(), m.getUrlFoto());
        String rutaFoto = "data/fotos/" + m.getRaza().name().toLowerCase() + ".jpg";
        vista.mostrarFoto(rutaFoto);
    }

    /**
     * Actualiza el combo de la vista con todos los MiniPigs actuales.
     */
    private void actualizarCombo() {
        cargandoCombo = true;
        List<MiniPig> todos = controller.listarTodos();
        java.util.List<String> items = new java.util.ArrayList<>();
        for (MiniPig m : todos) {
            items.add(m.getCodigo() + " - " + m.getNombre());
        }
        vista.actualizarCombo(items);
        cargandoCombo = false;
    }

    /**
     * Construye un texto legible con la lista de MiniPigs para el área de
     * resultados.
     *
     * @param lista Lista de MiniPigs.
     * @return Texto formateado.
     */
    private String construirTextoLista(List<MiniPig> lista) {
        StringBuilder sb = new StringBuilder();
        for (MiniPig m : lista) {
            sb.append("Código: ").append(m.getCodigo()).append(" | Nombre: ").append(m.getNombre()).append(" | Raza: ").append(m.getRaza().name()).append(" | Género: ").append(m.getGenero().name()).append(" | Peso: ").append(m.getPeso()).append("kg").append(" | Altura: ").append(m.getAltura()).append("cm").append("\n");
        }
        return sb.toString();
    }
}
