package Vista;

import Control.Logica.MiniPigController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Vista principal del aplicativo MiniPigs.
 *
 * @author Julian, Miguel, Andres
 * @version 1.0
 */
public class VistaPrincipal extends JFrame {

    /**
     * Combo desplegable con código y nombre de los MiniPigs registrados.
     */
    private JComboBox<String> cmbMinipigs;

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<String> cmbGenero;
    private JTextField txtMicrochip;
    private JComboBox<String> cmbRaza;
    private JTextField txtColor;
    private JTextField txtPeso;
    private JTextField txtAltura;
    private JTextField txtCaracteristica1;
    private JTextField txtCaracteristica2;

    private JLabel lblFoto;
    private JLabel lblMensaje;
    private JTextArea txtAreaResultados;

    JButton btnInsertar;
    JButton btnConsultarCodigo;
    JButton btnConsultarMicrochip;
    JButton btnConsultarRaza;
    JButton btnConsultarNombre;
    JButton btnEliminarCodigo;
    JButton btnEliminarMicrochip;
    JButton btnModificar;
    JButton btnConfirmarModificacion;
    JButton btnLimpiar;
    JButton btnSalir;

    private static final Color C_FONDO = new Color(242, 245, 250);
    private static final Color C_PANEL = new Color(255, 255, 255);
    private static final Color C_HEADER = new Color(18, 52, 100);
    private static final Color C_HEADER_LIGHT = new Color(35, 85, 155);
    private static final Color C_IZQUIERDA = new Color(232, 238, 248);
    private static final Color C_BORDE = new Color(195, 208, 228);
    private static final Color C_FILA_PAR = new Color(246, 249, 255);
    private static final Color C_BTN_AZUL = new Color(28, 78, 148);
    private static final Color C_BTN_ROJO = new Color(155, 28, 28);
    private static final Color C_BTN_NARANJA = new Color(170, 100, 0);
    private static final Color C_BTN_GRIS = new Color(65, 85, 105);
    private static final Color C_EXITO = new Color(25, 110, 55);
    private static final Color C_ERROR = new Color(175, 28, 28);
    private static final Color C_TEXTO = new Color(22, 22, 38);

    private static final Font F_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font F_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font F_SECCION = new Font("Segoe UI", Font.BOLD, 11);
    private static final Font F_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font F_CAMPO = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font F_BOTON = new Font("Segoe UI", Font.BOLD, 11);

    /**
     * Construye la vista principal e inicializa todos los componentes.
     *
     * @param controller Controlador del aplicativo.
     */
    public VistaPrincipal(MiniPigController controller) {
        construirUI();
        registrarListeners(controller);
    }

    private void construirUI() {
        setTitle("Sistema de Gestión MiniPigs - UD");
        setSize(1200, 790);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(C_FONDO);
        setLayout(new BorderLayout(0, 0));

        add(construirHeader(), BorderLayout.NORTH);
        add(construirCuerpo(), BorderLayout.CENTER);
        add(construirFooter(), BorderLayout.SOUTH);
    }

    /**
     * Header superior: título a la izquierda, combo a la derecha. Similar al
     * encabezado del documento de referencia.
     */
    private JPanel construirHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(C_HEADER);
        header.setBorder(new EmptyBorder(14, 22, 14, 22));

        // Izquierda: título + subtítulo
        JPanel izq = new JPanel(new GridLayout(2, 1, 0, 3));
        izq.setBackground(C_HEADER);
        JLabel lblTitulo = new JLabel("Sistema de Gestión de MiniPigs");
        lblTitulo.setFont(F_TITULO);
        lblTitulo.setForeground(Color.WHITE);
        JLabel lblSub = new JLabel(
                "Universidad Distrital Francisco José de Caldas  ·  Programación Avanzada");
        lblSub.setFont(F_SUBTITULO);
        lblSub.setForeground(new Color(175, 198, 235));
        izq.add(lblTitulo);
        izq.add(lblSub);

        // Derecha: combo selector
        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        der.setBackground(C_HEADER);
        JLabel lblCombo = new JLabel("MiniPig:");
        lblCombo.setFont(F_LABEL);
        lblCombo.setForeground(Color.WHITE);
        cmbMinipigs = new JComboBox<>();
        cmbMinipigs.setFont(F_CAMPO);
        cmbMinipigs.setPreferredSize(new Dimension(285, 30));
        cmbMinipigs.addItem("-- Seleccione un MiniPig --");
        der.add(lblCombo);
        der.add(cmbMinipigs);

        header.add(izq, BorderLayout.WEST);
        header.add(der, BorderLayout.EAST);
        return header;
    }

    private JPanel construirCuerpo() {
        JPanel cuerpo = new JPanel(new BorderLayout(0, 0));
        cuerpo.setBackground(C_FONDO);
        cuerpo.add(construirPanelIzquierdo(), BorderLayout.WEST);
        cuerpo.add(construirPanelDerecho(), BorderLayout.CENTER);
        return cuerpo;
    }

    /**
     * Panel izquierdo con foto del minipig y botones agrupados por sección.
     */
    private JPanel construirPanelIzquierdo() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(C_IZQUIERDA);
        panel.setPreferredSize(new Dimension(215, 0));
        panel.setBorder(new MatteBorder(0, 0, 0, 1, C_BORDE));

        panel.add(construirPanelFoto(), BorderLayout.NORTH);
        panel.add(construirPanelBotones(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel construirPanelFoto() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(C_IZQUIERDA);
        panel.setBorder(new EmptyBorder(14, 12, 10, 12));

        JLabel cabFoto = new JLabel("FOTO DEL MINIPIG");
        cabFoto.setFont(F_SECCION);
        cabFoto.setForeground(C_HEADER);
        cabFoto.setBorder(new MatteBorder(0, 0, 1, 0, C_BORDE));

        lblFoto = new JLabel("Sin foto", SwingConstants.CENTER);
        lblFoto.setPreferredSize(new Dimension(191, 162));
        lblFoto.setFont(F_CAMPO);
        lblFoto.setForeground(Color.GRAY);
        lblFoto.setBackground(C_PANEL);
        lblFoto.setOpaque(true);
        lblFoto.setBorder(BorderFactory.createLineBorder(C_BORDE, 1));

        panel.add(cabFoto, BorderLayout.NORTH);
        panel.add(lblFoto, BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane construirPanelBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(C_IZQUIERDA);
        panel.setBorder(new EmptyBorder(6, 12, 12, 12));

        // Consultas
        agregarCabSeccion(panel, "CONSULTAS");
        btnConsultarCodigo = crearBtn("Buscar por Código", C_BTN_AZUL);
        btnConsultarMicrochip = crearBtn("Buscar por Microchip", C_BTN_AZUL);
        btnConsultarRaza = crearBtn("Buscar por Raza", C_BTN_AZUL);
        btnConsultarNombre = crearBtn("Buscar por Nombre", C_BTN_AZUL);
        agregarBotones(panel, btnConsultarCodigo, btnConsultarMicrochip,
                btnConsultarRaza, btnConsultarNombre);

        // Gestión
        agregarCabSeccion(panel, "GESTIÓN");
        btnInsertar = crearBtn("Insertar", C_BTN_AZUL);
        btnModificar = crearBtn("Modificar", C_BTN_NARANJA);
        btnConfirmarModificacion = crearBtn("Confirmar Modificación", C_BTN_NARANJA);
        btnEliminarCodigo = crearBtn("Eliminar por Código", C_BTN_ROJO);
        btnEliminarMicrochip = crearBtn("Eliminar por Microchip", C_BTN_ROJO);
        agregarBotones(panel, btnInsertar, btnModificar, btnConfirmarModificacion,
                btnEliminarCodigo, btnEliminarMicrochip);

        // Acciones
        agregarCabSeccion(panel, "ACCIONES");
        btnLimpiar = crearBtn("Limpiar", C_BTN_GRIS);
        btnSalir = crearBtn("Salir", C_BTN_ROJO);
        agregarBotones(panel, btnLimpiar, btnSalir);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(C_IZQUIERDA);
        return scroll;
    }

    private void agregarCabSeccion(JPanel panel, String texto) {
        panel.add(Box.createVerticalStrut(8));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(F_SECCION);
        lbl.setForeground(C_HEADER);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDE));
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
    }

    private void agregarBotones(JPanel panel, JButton... botones) {
        for (JButton btn : botones) {
            panel.add(btn);
            panel.add(Box.createVerticalStrut(3));
        }
    }

    /**
     * Panel derecho: cabecera de sección + formulario tipo tabla + resultados.
     */
    private JPanel construirPanelDerecho() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(C_FONDO);
        panel.setBorder(new EmptyBorder(14, 16, 0, 16));

        // Cabecera sección (similar al "Objetivos e hitos" del doc)
        JPanel cabSec = new JPanel(new BorderLayout());
        cabSec.setBackground(new Color(215, 228, 248));
        cabSec.setBorder(new EmptyBorder(10, 16, 10, 16));
        JLabel lblSec = new JLabel("Datos del MiniPig", SwingConstants.CENTER);
        lblSec.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblSec.setForeground(C_HEADER);
        cabSec.add(lblSec, BorderLayout.CENTER);

        panel.add(cabSec, BorderLayout.NORTH);
        panel.add(construirFormularioTabla(), BorderLayout.CENTER);
        panel.add(construirAreaResultados(), BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Formulario con estructura de tabla: columna etiqueta | columna campo.
     * Inspirado en la tabla del documento de referencia.
     */
    private JPanel construirFormularioTabla() {
        JPanel tabla = new JPanel(new GridBagLayout());
        tabla.setBackground(C_PANEL);
        tabla.setBorder(BorderFactory.createLineBorder(C_BORDE, 1));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        cmbGenero = new JComboBox<>(new String[]{"MACHO", "HEMBRA"});
        txtMicrochip = new JTextField();
        cmbRaza = new JComboBox<>(new String[]{"JULIANA", "GOTTINGEN", "VIETNAMITA", "KUNEKUNE", "YUCATAN", "GUINEA_AMERICANA"});
        txtColor = new JTextField();
        txtPeso = new JTextField();
        txtAltura = new JTextField();
        txtCaracteristica1 = new JTextField();
        txtCaracteristica2 = new JTextField();

        String[] etiquetas = {"C\u00f3digo", "Nombre", "G\u00e9nero", "ID Microchip", "Raza", "Color", "Peso (kg)", "Altura (cm)", "Caracter\u00edstica 1", "Caracter\u00edstica 2"};
        JComponent[] campos = {txtCodigo, txtNombre, cmbGenero, txtMicrochip, cmbRaza, txtColor, txtPeso, txtAltura, txtCaracteristica1, txtCaracteristica2};

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipady = 4;

        // Cabecera
        gbc.gridy = 0;
        agregarCeldaHeader(tabla, gbc, 0, 0.30, "  Campo");
        agregarCeldaHeader(tabla, gbc, 1, 0.70, "  Valor");

        gbc.ipady = 6;
        for (int i = 0; i < etiquetas.length; i++) {
            Color bg = (i % 2 == 0) ? C_PANEL : C_FILA_PAR;
            gbc.gridy = i + 1;

            // Celda etiqueta
            gbc.gridx = 0;
            gbc.weightx = 0.30;
            JLabel lbl = new JLabel("  " + etiquetas[i]);
            lbl.setFont(F_LABEL);
            lbl.setForeground(C_TEXTO);
            lbl.setBackground(bg);
            lbl.setOpaque(true);
            lbl.setPreferredSize(new Dimension(170, 34));
            lbl.setBorder(new MatteBorder(0, 0, 1, 1, C_BORDE));
            tabla.add(lbl, gbc);

            // Celda campo ocupa todo el ancho restante
            gbc.gridx = 1;
            gbc.weightx = 0.70;
            campos[i].setFont(F_CAMPO);
            campos[i].setBackground(bg);
            campos[i].setPreferredSize(new Dimension(0, 34));

            if (campos[i] instanceof JTextField) {
                ((JTextField) campos[i]).setBorder(
                        BorderFactory.createCompoundBorder(
                                new MatteBorder(0, 0, 1, 0, C_BORDE),
                                new EmptyBorder(0, 10, 0, 8)
                        )
                );
            } else {
                campos[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
                campos[i].setBorder(new MatteBorder(0, 0, 1, 0, C_BORDE));
            }
            tabla.add(campos[i], gbc);
        }

        // Fila expansora
        gbc.gridy = etiquetas.length + 1;
        gbc.weighty = 1.0;
        gbc.ipady = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.30;
        tabla.add(new JLabel(), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.70;
        tabla.add(new JLabel(), gbc);

        return tabla;
    }

    private void agregarCeldaHeader(JPanel tabla, GridBagConstraints gbc,
            int col, double peso, String texto) {
        gbc.gridx = col;
        gbc.weightx = peso;
        JLabel lbl = new JLabel(texto);
        lbl.setFont(F_SECCION);
        lbl.setForeground(Color.WHITE);
        lbl.setBackground(C_HEADER_LIGHT);
        lbl.setOpaque(true);
        lbl.setBorder(new EmptyBorder(7, 8, 7, 8));
        tabla.add(lbl, gbc);
    }

    private JPanel construirAreaResultados() {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setBackground(C_FONDO);
        panel.setPreferredSize(new Dimension(0, 125));

        JLabel lbl = new JLabel("Resultados");
        lbl.setFont(F_SECCION);
        lbl.setForeground(C_HEADER);
        lbl.setBorder(new MatteBorder(0, 0, 1, 0, C_BORDE));

        txtAreaResultados = new JTextArea();
        txtAreaResultados.setFont(new Font("Monospaced", Font.PLAIN, 11));
        txtAreaResultados.setEditable(false);
        txtAreaResultados.setBackground(C_PANEL);
        txtAreaResultados.setBorder(new EmptyBorder(6, 8, 6, 8));

        JScrollPane scroll = new JScrollPane(txtAreaResultados);
        scroll.setBorder(BorderFactory.createLineBorder(C_BORDE, 1));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel construirFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 6));
        footer.setBackground(new Color(218, 227, 242));
        footer.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDE));
        lblMensaje = new JLabel("Listo.");
        lblMensaje.setFont(F_CAMPO);
        lblMensaje.setForeground(C_HEADER);
        footer.add(lblMensaje);
        return footer;
    }

    private JButton crearBtn(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(F_BOTON);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(color.darker(), 1));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    /**
     * Registra el {@link ListenerPrincipal} en todos los componentes
     * interactivos.
     *
     * @param controller Controlador del aplicativo.
     */
    private void registrarListeners(MiniPigController controller) {
        ListenerPrincipal listener = new ListenerPrincipal(this, controller);

        btnInsertar.addActionListener(listener);
        btnConsultarCodigo.addActionListener(listener);
        btnConsultarMicrochip.addActionListener(listener);
        btnConsultarRaza.addActionListener(listener);
        btnConsultarNombre.addActionListener(listener);
        btnEliminarCodigo.addActionListener(listener);
        btnEliminarMicrochip.addActionListener(listener);
        btnModificar.addActionListener(listener);
        btnConfirmarModificacion.addActionListener(listener);
        btnLimpiar.addActionListener(listener);
        btnSalir.addActionListener(listener);
        cmbMinipigs.addActionListener(listener);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                listener.accionSalir();
            }
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * @return Código ingresado.
     */
    public String getCodigo() {
        return txtCodigo.getText().trim();
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getGeneroSeleccionado() {
        return (String) cmbGenero.getSelectedItem();
    }

    public String getMicrochip() {
        return txtMicrochip.getText().trim();
    }

    public String getRazaSeleccionada() {
        return (String) cmbRaza.getSelectedItem();
    }

    public String getColor() {
        return txtColor.getText().trim();
    }

    public String getPeso() {
        return txtPeso.getText().trim();
    }

    public String getAltura() {
        return txtAltura.getText().trim();
    }

    public String getCaracteristica1() {
        return txtCaracteristica1.getText().trim();
    }

    public String getCaracteristica2() {
        return txtCaracteristica2.getText().trim();
    }

    public String getComboSeleccionado() {
        return (String) cmbMinipigs.getSelectedItem();
    }

    public void actualizarCombo(java.util.List<String> items) {
        cmbMinipigs.removeAllItems();
        cmbMinipigs.addItem("-- Seleccione un MiniPig --");
        for (String item : items) {
            cmbMinipigs.addItem(item);
        }
    }

    /**
     * Muestra los datos de un MiniPig en el formulario. Solo recibe Strings,
     * sin objetos del modelo.
     */
    public void mostrarDatos(String codigo, String nombre, String genero, String microchip, String raza, String color, String peso, String altura, String caracteristica1, String caracteristica2, String urlFoto) {
        txtCodigo.setText(codigo);
        txtNombre.setText(nombre);
        cmbGenero.setSelectedItem(genero);
        txtMicrochip.setText(microchip);
        cmbRaza.setSelectedItem(raza);
        txtColor.setText(color);
        txtPeso.setText(peso);
        txtAltura.setText(altura);
        txtCaracteristica1.setText(caracteristica1);
        txtCaracteristica2.setText(caracteristica2);
    }

    /**
     * Muestra la foto del MiniPig según su ruta.
     *
     * @param rutaFoto Ruta local de la foto.
     */
    public void mostrarFoto(String rutaFoto) {
        if (rutaFoto == null || rutaFoto.isEmpty()) {
            lblFoto.setIcon(null);
            lblFoto.setText("Sin foto");
            return;
        }
        try {
            ImageIcon icono = new ImageIcon(rutaFoto);
            Image img = icono.getImage().getScaledInstance(191, 158, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(img));
            lblFoto.setText("");
        } catch (Exception e) {
            lblFoto.setIcon(null);
            lblFoto.setText("Foto no encontrada");
        }
    }

    /**
     * Muestra texto en el área de resultados.
     *
     * @param texto Texto a mostrar.
     */
    public void mostrarResultados(String texto) {
        txtAreaResultados.setText(texto);
    }

    /**
     * Muestra un mensaje en el footer.
     *
     * @param mensaje Mensaje a mostrar.
     * @param esError Si es true se muestra en rojo, si no en verde.
     */
    public void setMensaje(String mensaje, boolean esError) {
        lblMensaje.setText(mensaje);
        lblMensaje.setForeground(esError ? C_ERROR : C_EXITO);
    }

    /**
     * Limpia todos los campos y el área de resultados.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        cmbGenero.setSelectedIndex(0);
        txtMicrochip.setText("");
        cmbRaza.setSelectedIndex(0);
        txtColor.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        txtCaracteristica1.setText("");
        txtCaracteristica2.setText("");
        txtAreaResultados.setText("");
        lblFoto.setIcon(null);
        lblFoto.setText("Sin foto");
        lblMensaje.setText("Listo.");
        lblMensaje.setForeground(C_HEADER);
    }

    /**
     * Habilita todos los campos para nueva entrada de datos. Se llama al
     * limpiar el formulario.
     */
    public void habilitarTodosCampos() {
        txtCodigo.setEditable(true);
        txtNombre.setEditable(true);
        cmbGenero.setEnabled(true);
        txtMicrochip.setEditable(true);
        cmbRaza.setEnabled(true);
        txtColor.setEditable(true);
        txtPeso.setEditable(true);
        txtAltura.setEditable(true);
        txtCaracteristica1.setEditable(true);
        txtCaracteristica2.setEditable(true);
        btnConfirmarModificacion.setEnabled(false);
    }

    /**
     * Habilita o deshabilita los campos modificables. Código y microchip
     * siempre quedan en solo lectura.
     *
     * @param habilitado true para habilitar modificación.
     */
    public void habilitarCamposModificacion(boolean habilitado) {
        txtNombre.setEditable(habilitado);
        cmbGenero.setEnabled(habilitado);
        cmbRaza.setEnabled(habilitado);
        txtColor.setEditable(habilitado);
        txtPeso.setEditable(habilitado);
        txtAltura.setEditable(habilitado);
        txtCaracteristica1.setEditable(habilitado);
        txtCaracteristica2.setEditable(habilitado);
        txtCodigo.setEditable(false);
        txtMicrochip.setEditable(false);
        btnConfirmarModificacion.setEnabled(habilitado);
    }
}
