/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pa.taller2.vista;

import pa.taller2.controlador.MinipigController;
import pa.taller2.controlador.MinipigDTO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

/**
 * Ventana principal de la aplicación de Minipigs.
 * Implementa la interfaz IVista y se encarga de la presentación de la interfaz gráfica.
 * 
 * Aplica el principio de Responsabilidad Única (SRP):
 * - Solo se encarga de la presentación y captura de datos
 * - No contiene lógica de negocio ni acceso a datos
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class VentanaPrincipal extends JFrame implements IVista {
    
    // Componentes de la interfaz
    private JPanel panelPrincipal;
    private JPanel panelBusqueda;
    private JPanel panelDatos;
    private JPanel panelBotones;
    private JPanel panelFoto;
    
    // Componentes de búsqueda
    private JComboBox<String> comboMinipigs;
    private DefaultComboBoxModel<String> modeloCombo;
    private JTextField txtBusqueda;
    private JRadioButton rbCodigo;
    private JRadioButton rbMicrochip;
    private JRadioButton rbNombre;
    private JRadioButton rbRaza;
    private ButtonGroup grupoBusqueda;
    private JButton btnBuscar;
    
    // Campos de datos del minipig
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<String> comboGenero;
    private JTextField txtMicrochip;
    private JComboBox<String> comboRaza;
    private JTextField txtColor;
    private JTextField txtPeso;
    private JTextField txtAltura;
    private JTextField txtCaract1;
    private JTextField txtCaract2;
    private JLabel lblFoto;
    private JTextField txtUrlFoto;
    
    // Botones de acción
    private JButton btnNuevo;
    private JButton btnInsertar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnSalir;
    
    // Referencia al controlador
    private MinipigController controlador;
    private EscuchadorEventos escuchador;
    
    /**
     * Constructor de la ventana principal
     */
    public VentanaPrincipal() {
        inicializarComponentes();
        configurarVentana();
        this.escuchador = new EscuchadorEventos(this, null); // Controlador se setea después
    }
    
    /**
     * Inicializa todos los componentes gráficos
     */
    private void inicializarComponentes() {
        // Panel principal con BorderLayout
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Crear los subpaneles
        crearPanelBusqueda();
        crearPanelDatos();
        crearPanelBotones();
        crearPanelFoto();
        
        // Agregar paneles al principal
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);
        
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelDatos, BorderLayout.CENTER);
        panelCentro.add(panelFoto, BorderLayout.EAST);
        
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        this.setContentPane(panelPrincipal);
    }
    
    /**
     * Crea el panel de búsqueda
     */
    private void crearPanelBusqueda() {
        panelBusqueda = new JPanel(new GridBagLayout());
        panelBusqueda.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Búsqueda de Minipigs", 
            TitledBorder.LEFT, TitledBorder.TOP));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Combo de minipigs
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelBusqueda.add(new JLabel("Minipigs registrados:"), gbc);
        
        gbc.gridx = 2;
        gbc.gridwidth = 3;
        modeloCombo = new DefaultComboBoxModel<>();
        comboMinipigs = new JComboBox<>(modeloCombo);
        comboMinipigs.setPreferredSize(new Dimension(200, 25));
        panelBusqueda.add(comboMinipigs, gbc);
        
        // Opciones de búsqueda
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelBusqueda.add(new JLabel("Buscar por:"), gbc);
        
        gbc.gridx = 1;
        rbCodigo = new JRadioButton("Código");
        panelBusqueda.add(rbCodigo, gbc);
        
        gbc.gridx = 2;
        rbMicrochip = new JRadioButton("Microchip");
        panelBusqueda.add(rbMicrochip, gbc);
        
        gbc.gridx = 3;
        rbNombre = new JRadioButton("Nombre");
        panelBusqueda.add(rbNombre, gbc);
        
        gbc.gridx = 4;
        rbRaza = new JRadioButton("Raza");
        panelBusqueda.add(rbRaza, gbc);
        
        // Grupo de radio buttons
        grupoBusqueda = new ButtonGroup();
        grupoBusqueda.add(rbCodigo);
        grupoBusqueda.add(rbMicrochip);
        grupoBusqueda.add(rbNombre);
        grupoBusqueda.add(rbRaza);
        rbCodigo.setSelected(true);
        
        // Campo de texto para búsqueda y botón
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        txtBusqueda = new JTextField(20);
        panelBusqueda.add(txtBusqueda, gbc);
        
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar, gbc);
        
        gbc.gridx = 4;
        btnLimpiar = new JButton("Limpiar");
        panelBusqueda.add(btnLimpiar, gbc);
    }
    
    /**
     * Crea el panel de datos del minipig
     */
    private void crearPanelDatos() {
        panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Datos del Minipig", 
            TitledBorder.LEFT, TitledBorder.TOP));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 0: Código
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("Código:*"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtCodigo = new JTextField(20);
        txtCodigo.setEditable(false);
        panelDatos.add(txtCodigo, gbc);
        
        // Fila 1: Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelDatos.add(new JLabel("Nombre:*"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtNombre = new JTextField(20);
        panelDatos.add(txtNombre, gbc);
        
        // Fila 2: Género
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panelDatos.add(new JLabel("Género:*"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        comboGenero = new JComboBox<>(new String[]{"", "MACHO", "HEMBRA"});
        comboGenero.setPreferredSize(new Dimension(200, 25));
        panelDatos.add(comboGenero, gbc);
        
        // Fila 3: Microchip
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelDatos.add(new JLabel("Microchip:*"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtMicrochip = new JTextField(20);
        txtMicrochip.setEditable(false);
        panelDatos.add(txtMicrochip, gbc);
        
        // Fila 4: Raza
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelDatos.add(new JLabel("Raza:*"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        comboRaza = new JComboBox<>(new String[]{
            "", "Juliana", "Göttingen", "Vietnamita", "Kunekune", 
            "Yucatán", "Guinea Americano", "Mulefoot", "Ossabaw Island", 
            "Meishan", "Hanford Mini Swine", "Congo"
        });
        comboRaza.setPreferredSize(new Dimension(200, 25));
        panelDatos.add(comboRaza, gbc);
        
        // Fila 5: Color
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelDatos.add(new JLabel("Color:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtColor = new JTextField(20);
        panelDatos.add(txtColor, gbc);
        
        // Fila 6: Peso
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelDatos.add(new JLabel("Peso (kg):"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        txtPeso = new JTextField(10);
        panelDatos.add(txtPeso, gbc);
        
        // Fila 6 (columna 2): Altura
        gbc.gridx = 2;
        panelDatos.add(new JLabel("Altura (cm):"), gbc);
        
        gbc.gridx = 3;
        txtAltura = new JTextField(10);
        panelDatos.add(txtAltura, gbc);
        
        // Fila 7: Característica 1
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panelDatos.add(new JLabel("Característica 1:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        txtCaract1 = new JTextField(30);
        panelDatos.add(txtCaract1, gbc);
        
        // Fila 8: Característica 2
        gbc.gridx = 0;
        gbc.gridy = 8;
        panelDatos.add(new JLabel("Característica 2:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        txtCaract2 = new JTextField(30);
        panelDatos.add(txtCaract2, gbc);
        
        // Fila 9: URL Foto
        gbc.gridx = 0;
        gbc.gridy = 9;
        panelDatos.add(new JLabel("URL Foto:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        txtUrlFoto = new JTextField(30);
        panelDatos.add(txtUrlFoto, gbc);
        
        // Nota campos requeridos
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 4;
        JLabel lblRequeridos = new JLabel("* Campos requeridos");
        lblRequeridos.setFont(new Font("Arial", Font.ITALIC, 11));
        lblRequeridos.setForeground(Color.RED);
        panelDatos.add(lblRequeridos, gbc);
    }
    
    /**
     * Crea el panel de la foto
     */
    private void crearPanelFoto() {
        panelFoto = new JPanel(new BorderLayout());
        panelFoto.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Foto", 
            TitledBorder.LEFT, TitledBorder.TOP));
        panelFoto.setPreferredSize(new Dimension(200, 200));
        
        lblFoto = new JLabel();
        lblFoto.setHorizontalAlignment(JLabel.CENTER);
        lblFoto.setVerticalAlignment(JLabel.CENTER);
        lblFoto.setText("Sin foto");
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JScrollPane scrollFoto = new JScrollPane(lblFoto);
        scrollFoto.setPreferredSize(new Dimension(180, 180));
        panelFoto.add(scrollFoto, BorderLayout.CENTER);
        
        JButton btnCargarFoto = new JButton("Cargar Foto");
        panelFoto.add(btnCargarFoto, BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel de botones de acción
     */
    private void crearPanelBotones() {
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBorder(BorderFactory.createEtchedBorder());
        
        btnNuevo = new JButton("Nuevo");
        btnInsertar = new JButton("Insertar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnSalir = new JButton("Salir");
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnInsertar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnSalir);
    }
    
    /**
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        this.setTitle("Sistema de Gestión de Minipigs");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 700);
        this.setLocationRelativeTo(null); // Centrar en pantalla
        this.setResizable(true);
    }
    
    /**
     * Establece el controlador y registra los eventos
     * @param controlador Controlador de la aplicación
     */
    public void setControlador(MinipigController controlador) {
        this.controlador = controlador;
        this.escuchador = new EscuchadorEventos(this, controlador);
        registrarEventos();
    }
    
    /**
     * Registra los eventos en los componentes
     */
    private void registrarEventos() {
        // Botones de búsqueda
        btnBuscar.addActionListener(escuchador);
        btnLimpiar.addActionListener(escuchador);
        
        // Botones de acción
        btnNuevo.addActionListener(escuchador);
        btnInsertar.addActionListener(escuchador);
        btnModificar.addActionListener(escuchador);
        btnEliminar.addActionListener(escuchador);
        btnSalir.addActionListener(escuchador);
        
        // Combo de selección
        comboMinipigs.addActionListener(escuchador);
    }
    
    @Override
    public void mostrarMinipig(MinipigDTO minipig) {
        if (minipig != null) {
            txtCodigo.setText(minipig.getCodigo());
            txtNombre.setText(minipig.getNombre());
            comboGenero.setSelectedItem(minipig.getGenero());
            txtMicrochip.setText(minipig.getIdMicrochip());
            comboRaza.setSelectedItem(minipig.getRaza());
            txtColor.setText(minipig.getColor());
            txtPeso.setText(String.valueOf(minipig.getPeso()));
            txtAltura.setText(String.valueOf(minipig.getAltura()));
            txtCaract1.setText(minipig.getCaracteristica1());
            txtCaract2.setText(minipig.getCaracteristica2());
            txtUrlFoto.setText(minipig.getUrlFoto());
            
            // Cargar foto si existe URL
            cargarFoto(minipig.getUrlFoto());
        }
    }
    
    /**
     * Carga una foto desde la URL especificada
     * @param urlFoto URL de la foto
     */
    private void cargarFoto(String urlFoto) {
        if (urlFoto != null && !urlFoto.trim().isEmpty()) {
            try {
                ImageIcon icono = new ImageIcon(urlFoto);
                Image imagen = icono.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                lblFoto.setIcon(new ImageIcon(imagen));
                lblFoto.setText("");
            } catch (Exception e) {
                lblFoto.setIcon(null);
                lblFoto.setText("Error al cargar foto");
            }
        } else {
            lblFoto.setIcon(null);
            lblFoto.setText("Sin foto");
        }
    }
    
    @Override
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        comboGenero.setSelectedIndex(0);
        txtMicrochip.setText("");
        comboRaza.setSelectedIndex(0);
        txtColor.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        txtCaract1.setText("");
        txtCaract2.setText("");
        txtUrlFoto.setText("");
        txtBusqueda.setText("");
        lblFoto.setIcon(null);
        lblFoto.setText("Sin foto");
    }
    
    @Override
    public void actualizarCombo(List<MinipigDTO> minipigs) {
        modeloCombo.removeAllElements();
        modeloCombo.addElement("Seleccione un minipig...");
        
        for (MinipigDTO minipig : minipigs) {
            String item = minipig.getCodigo() + " - " + minipig.getNombre();
            modeloCombo.addElement(item);
        }
    }
    
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public MinipigDTO obtenerDatosFormulario() {
        MinipigDTO dto = new MinipigDTO();
        
        dto.setCodigo(txtCodigo.getText().trim());
        dto.setNombre(txtNombre.getText().trim());
        dto.setGenero(comboGenero.getSelectedItem() != null ? comboGenero.getSelectedItem().toString() : "");
        dto.setIdMicrochip(txtMicrochip.getText().trim());
        dto.setRaza(comboRaza.getSelectedItem() != null ? comboRaza.getSelectedItem().toString() : "");
        dto.setColor(txtColor.getText().trim());
        
        try {
            if (!txtPeso.getText().trim().isEmpty()) {
                dto.setPeso(Double.parseDouble(txtPeso.getText().trim()));
            }
        } catch (NumberFormatException e) {
            dto.setPeso(0.0);
        }
        
        try {
            if (!txtAltura.getText().trim().isEmpty()) {
                dto.setAltura(Double.parseDouble(txtAltura.getText().trim()));
            }
        } catch (NumberFormatException e) {
            dto.setAltura(0.0);
        }
        
        dto.setCaracteristica1(txtCaract1.getText().trim());
        dto.setCaracteristica2(txtCaract2.getText().trim());
        dto.setUrlFoto(txtUrlFoto.getText().trim());
        
        return dto;
    }
    
    @Override
    public void setCamposEditables(boolean editable) {
        txtNombre.setEditable(editable);
        comboGenero.setEnabled(editable);
        comboRaza.setEnabled(editable);
        txtColor.setEditable(editable);
        txtPeso.setEditable(editable);
        txtAltura.setEditable(editable);
        txtCaract1.setEditable(editable);
        txtCaract2.setEditable(editable);
        txtUrlFoto.setEditable(editable);
    }
    
    // Getters para los componentes (usados por el escuchador)
    
    public JComboBox<String> getComboMinipigs() { return comboMinipigs; }
    public JTextField getTxtBusqueda() { return txtBusqueda; }
    public JRadioButton getRbCodigo() { return rbCodigo; }
    public JRadioButton getRbMicrochip() { return rbMicrochip; }
    public JRadioButton getRbNombre() { return rbNombre; }
    public JRadioButton getRbRaza() { return rbRaza; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnNuevo() { return btnNuevo; }
    public JButton getBtnInsertar() { return btnInsertar; }
    public JButton getBtnModificar() { return btnModificar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnSalir() { return btnSalir; }
    // En VentanaPrincipal.java - Agregar estos métodos adicionales

    /**
     * Obtiene el campo de texto del código
     * @return JTextField del código
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Obtiene el campo de texto del microchip
     * @return JTextField del microchip
     */
    public JTextField getTxtMicrochip() {
        return txtMicrochip;
    }

    /**
     * Obtiene el campo de texto del nombre
     * @return JTextField del nombre
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Obtiene el combo box de género
     * @return JComboBox del género
     */
    public JComboBox<String> getComboGenero() {
        return comboGenero;
    }

    /**
     * Obtiene el combo box de raza
     * @return JComboBox de la raza
     */
    public JComboBox<String> getComboRaza() {
        return comboRaza;
    }

    /**
     * Obtiene el campo de texto del color
     * @return JTextField del color
     */
    public JTextField getTxtColor() {
        return txtColor;
    }

    /**
     * Obtiene el campo de texto del peso
     * @return JTextField del peso
     */
    public JTextField getTxtPeso() {
        return txtPeso;
    }

    /**
     * Obtiene el campo de texto de la altura
     * @return JTextField de la altura
     */
    public JTextField getTxtAltura() {
        return txtAltura;
    }

    /**
     * Obtiene el campo de texto de la característica 1
     * @return JTextField de característica 1
     */
    public JTextField getTxtCaract1() {
        return txtCaract1;
    }

    /**
     * Obtiene el campo de texto de la característica 2
     * @return JTextField de característica 2
     */
    public JTextField getTxtCaract2() {
        return txtCaract2;
    }

    /**
     * Obtiene el campo de texto de la URL de la foto
     * @return JTextField de URL foto
     */
    public JTextField getTxtUrlFoto() {
        return txtUrlFoto;
    }

    /**
     * Obtiene la etiqueta de la foto
     * @return JLabel de la foto
     */
    public JLabel getLblFoto() {
        return lblFoto;
    }
}
