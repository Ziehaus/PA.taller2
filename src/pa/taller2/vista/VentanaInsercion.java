
package pa.taller2.vista;

import pa.taller2.controlador.MinipigController;
import pa.taller2.controlador.MinipigDTO;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Ventana para la inserción manual de minipigs, especialmente cuando se detectan
 * campos null en el archivo de propiedades.
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class VentanaInsercion extends JDialog {
    
    private Map<String, JTextField> camposTexto;
    private JComboBox<String> comboGenero;
    private JComboBox<String> comboRaza;
    private MinipigDTO minipigParcial;
    private MinipigController controlador;
    private boolean guardado = false;
    
    /**
     * Constructor de la ventana de inserción
     * @param parent Ventana padre
     * @param controlador Controlador de la aplicación
     * @param minipigParcial DTO con los datos parciales del minipig
     * @param camposNull Lista de campos que están null y requieren ingreso
     */
    public VentanaInsercion(JFrame parent, MinipigController controlador, 
                           MinipigDTO minipigParcial, java.util.List<String> camposNull) {
        super(parent, 
              "Completar Datos: " + 
              (minipigParcial.getCodigo() != null ? minipigParcial.getCodigo() : "???") + 
              " - " + 
              (minipigParcial.getNombre() != null ? minipigParcial.getNombre() : "Sin nombre"), 
              true);
        this.controlador = controlador;
        this.minipigParcial = minipigParcial;
        this.camposTexto = new HashMap<>();

        inicializarComponentes(camposNull);
        configurarVentana();
    }
    
    /**
     * Inicializa los componentes de la ventana con información detallada del minipig
     * @param camposNull Lista de campos a solicitar
     */
    private void inicializarComponentes(java.util.List<String> camposNull) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int fila = 0;

        // =====================================================
        // ENCABEZADO CON INFORMACIÓN DEL MINIPIG
        // =====================================================
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;

        // Crear panel para el encabezado con borde y color de fondo
        JPanel panelEncabezado = new JPanel();
        panelEncabezado.setLayout(new BoxLayout(panelEncabezado, BoxLayout.Y_AXIS));
        panelEncabezado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new java.awt.Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelEncabezado.setBackground(new java.awt.Color(240, 248, 255)); // AliceBlue

        // Título principal
        JLabel lblTitulo = new JLabel("🐷 COMPLETAR DATOS DEL MINIPIG");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new java.awt.Color(70, 130, 180));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEncabezado.add(lblTitulo);

        panelEncabezado.add(Box.createVerticalStrut(10));

        // Información del minipig en formato tabla
        JPanel panelInfo = new JPanel(new GridLayout(4, 2, 5, 5));
        panelInfo.setBackground(new java.awt.Color(240, 248, 255));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Código
        JLabel lblCodigoLabel = new JLabel("📌 Código:");
        lblCodigoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panelInfo.add(lblCodigoLabel);

        String codigo = minipigParcial.getCodigo() != null ? minipigParcial.getCodigo() : "No asignado";
        JLabel lblCodigoValor = new JLabel(codigo);
        lblCodigoValor.setFont(new Font("Arial", Font.PLAIN, 12));
        if (minipigParcial.getCodigo() == null) {
            lblCodigoValor.setForeground(Color.RED);
            lblCodigoValor.setText(codigo + " ⚠");
        }
        panelInfo.add(lblCodigoValor);

        // Nombre
        JLabel lblNombreLabel = new JLabel("📝 Nombre:");
        lblNombreLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panelInfo.add(lblNombreLabel);

        String nombre = minipigParcial.getNombre() != null ? minipigParcial.getNombre() : "No asignado";
        JLabel lblNombreValor = new JLabel(nombre);
        lblNombreValor.setFont(new Font("Arial", Font.PLAIN, 12));
        if (minipigParcial.getNombre() == null) {
            lblNombreValor.setForeground(Color.RED);
            lblNombreValor.setText(nombre + " ⚠");
        }
        panelInfo.add(lblNombreValor);

        // Microchip
        JLabel lblMicrochipLabel = new JLabel("🔖 Microchip:");
        lblMicrochipLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panelInfo.add(lblMicrochipLabel);

        String microchip = minipigParcial.getIdMicrochip() != null ? minipigParcial.getIdMicrochip() : "No asignado";
        JLabel lblMicrochipValor = new JLabel(microchip);
        lblMicrochipValor.setFont(new Font("Arial", Font.PLAIN, 12));
        if (minipigParcial.getIdMicrochip() == null) {
            lblMicrochipValor.setForeground(Color.RED);
            lblMicrochipValor.setText(microchip + " ⚠");
        }
        panelInfo.add(lblMicrochipValor);

        // Raza
        JLabel lblRazaLabel = new JLabel("🐖 Raza:");
        lblRazaLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panelInfo.add(lblRazaLabel);

        String raza = minipigParcial.getRaza() != null ? minipigParcial.getRaza() : "No asignada";
        JLabel lblRazaValor = new JLabel(raza);
        lblRazaValor.setFont(new Font("Arial", Font.PLAIN, 12));
        if (minipigParcial.getRaza() == null) {
            lblRazaValor.setForeground(Color.RED);
            lblRazaValor.setText(raza + " ⚠");
        }
        panelInfo.add(lblRazaValor);

        panelEncabezado.add(panelInfo);

        panelEncabezado.add(Box.createVerticalStrut(5));

        // Contador de campos pendientes
        JLabel lblPendientes = new JLabel("⏳ Campos pendientes: " + camposNull.size());
        lblPendientes.setFont(new Font("Arial", Font.BOLD, 12));
        lblPendientes.setForeground(new java.awt.Color(255, 140, 0)); // Orange
        lblPendientes.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEncabezado.add(lblPendientes);

        panel.add(panelEncabezado, gbc);

        // =====================================================
        // SEPARADOR
        // =====================================================
        fila++;
        gbc.gridy = fila++;
        JSeparator separador = new JSeparator();
        separador.setPreferredSize(new Dimension(400, 2));
        panel.add(separador, gbc);

        // =====================================================
        // MENSAJE INFORMATIVO
        // =====================================================
        gbc.gridy = fila++;
        JLabel lblMensaje = new JLabel("Por favor complete los siguientes campos:");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblMensaje, gbc);

        // =====================================================
        // CAMPOS A COMPLETAR
        // =====================================================
        for (String campo : camposNull) {
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = fila;

            // Label del campo con icono según tipo
            String icono = "📌";
            if (campo.toLowerCase().contains("peso")) icono = "⚖️";
            else if (campo.toLowerCase().contains("altura")) icono = "📏";
            else if (campo.toLowerCase().contains("color")) icono = "🎨";
            else if (campo.toLowerCase().contains("foto")) icono = "📷";
            else if (campo.toLowerCase().contains("caract")) icono = "✨";

            JLabel lblCampo = new JLabel(icono + " " + campo + ":*");
            lblCampo.setFont(new Font("Arial", Font.PLAIN, 12));
            lblCampo.setForeground(new java.awt.Color(0, 100, 0)); // Dark green
            panel.add(lblCampo, gbc);

            gbc.gridx = 1;
            JTextField txtCampo = new JTextField(25);
            txtCampo.setFont(new Font("Arial", Font.PLAIN, 12));

            // Tooltip con ayuda según el campo
            String tooltip = "";
            switch (campo.toLowerCase()) {
                case "código":
                case "codigo":
                    tooltip = "Ej: M001, M002 (3-10 caracteres alfanuméricos)";
                    break;
                case "nombre":
                    tooltip = "Ej: Rosita, Einstein (2-50 caracteres)";
                    break;
                case "género":
                case "genero":
                    tooltip = "MACHO o HEMBRA";
                    break;
                case "microchip":
                    tooltip = "Ej: 75AF56, 89BC34 (6-20 caracteres hexadecimales)";
                    break;
                case "raza":
                    tooltip = "Ej: Juliana, Göttingen, Vietnamita";
                    break;
                case "peso":
                    tooltip = "Peso en kilogramos (ej: 32.5)";
                    break;
                case "altura":
                    tooltip = "Altura en centímetros (ej: 43)";
                    break;
                case "color":
                    tooltip = "Ej: Manchado, Negro, Rosado";
                    break;
                case "url_foto":
                case "urlfoto":
                case "url foto":
                    tooltip = "Ruta completa de la foto";
                    break;
                default:
                    tooltip = "Ingrese el valor para " + campo;
            }
            txtCampo.setToolTipText(tooltip);

            // Si el campo ya tiene un valor en el DTO, mostrarlo
            String valorActual = obtenerValorCampo(campo);
            if (valorActual != null && !valorActual.equals("null") && !valorActual.isEmpty()) {
                txtCampo.setText(valorActual);
                txtCampo.setForeground(new java.awt.Color(0, 100, 0)); // Verde si ya tiene valor
            } else {
                txtCampo.setForeground(Color.BLACK);
                // Placeholder para campos vacíos
                txtCampo.putClientProperty("JTextField.placeholderText", 
                    "Ingrese " + campo.toLowerCase() + "...");
            }

            panel.add(txtCampo, gbc);
            camposTexto.put(campo.toLowerCase(), txtCampo);

            fila++;
        }

        // =====================================================
        // NOTA DE CAMPOS REQUERIDOS
        // =====================================================
        gbc.gridx = 0;
        gbc.gridy = fila++;
        gbc.gridwidth = 2;
        JLabel lblRequeridos = new JLabel("* Campos requeridos");
        lblRequeridos.setFont(new Font("Arial", Font.ITALIC, 10));
        lblRequeridos.setForeground(Color.GRAY);
        panel.add(lblRequeridos, gbc);

        // =====================================================
        // SEPARADOR
        // =====================================================
        gbc.gridy = fila++;
        JSeparator separador2 = new JSeparator();
        separador2.setPreferredSize(new Dimension(400, 2));
        panel.add(separador2, gbc);

        // =====================================================
        // BOTONES
        // =====================================================
        gbc.gridy = fila++;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setBackground(new java.awt.Color(70, 130, 180));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(120, 35));
        btnGuardar.addActionListener(e -> guardarDatos());

        JButton btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setBackground(new java.awt.Color(220, 20, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        panel.add(panelBotones, gbc);

        // =====================================================
        // AÑADIR PANEL A LA VENTANA
        // =====================================================
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane);
    }
    
    /**
     * Obtiene el valor de un campo del DTO parcial
     * @param campo Nombre del campo
     * @return Valor del campo o null
     */
    private String obtenerValorCampo(String campo) {
        if (minipigParcial == null) return null;
        
        switch (campo.toLowerCase()) {
            case "código":
            case "codigo": return minipigParcial.getCodigo();
            case "nombre": return minipigParcial.getNombre();
            case "género":
            case "genero": return minipigParcial.getGenero();
            case "microchip": return minipigParcial.getIdMicrochip();
            case "raza": return minipigParcial.getRaza();
            case "color": return minipigParcial.getColor();
            case "peso": return String.valueOf(minipigParcial.getPeso());
            case "altura": return String.valueOf(minipigParcial.getAltura());
            case "característica1":
            case "caracteristica1": return minipigParcial.getCaracteristica1();
            case "característica2":
            case "caracteristica2": return minipigParcial.getCaracteristica2();
            case "urlfoto":
            case "url_foto": return minipigParcial.getUrlFoto();
            default: return null;
        }
    }
    
    /**
     * Guarda los datos ingresados y actualiza el DTO
     */
    private void guardarDatos() {
        for (Map.Entry<String, JTextField> entry : camposTexto.entrySet()) {
            String campo = entry.getKey();
            String valor = entry.getValue().getText().trim();
            
            actualizarDTO(campo, valor);
        }
        
        guardado = true;
        dispose();
    }
    
    /**
     * Actualiza el DTO con el valor ingresado
     * @param campo Campo a actualizar
     * @param valor Valor ingresado
     */
    private void actualizarDTO(String campo, String valor) {
        if (minipigParcial == null) return;
        
        switch (campo) {
            case "código":
            case "codigo":
                minipigParcial.setCodigo(valor);
                break;
            case "nombre":
                minipigParcial.setNombre(valor);
                break;
            case "género":
            case "genero":
                minipigParcial.setGenero(valor);
                break;
            case "microchip":
                minipigParcial.setIdMicrochip(valor);
                break;
            case "raza":
                minipigParcial.setRaza(valor);
                break;
            case "color":
                minipigParcial.setColor(valor);
                break;
            case "peso":
                try {
                    minipigParcial.setPeso(Double.parseDouble(valor));
                } catch (NumberFormatException e) {
                    minipigParcial.setPeso(0.0);
                }
                break;
            case "altura":
                try {
                    minipigParcial.setAltura(Double.parseDouble(valor));
                } catch (NumberFormatException e) {
                    minipigParcial.setAltura(0.0);
                }
                break;
            case "característica1":
            case "caracteristica1":
                minipigParcial.setCaracteristica1(valor);
                break;
            case "característica2":
            case "caracteristica2":
                minipigParcial.setCaracteristica2(valor);
                break;
            case "urlfoto":
            case "url_foto":
                minipigParcial.setUrlFoto(valor);
                break;
        }
    }
    
    /**
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        this.setSize(400, 300);
        this.setLocationRelativeTo(getParent());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Indica si se guardaron los datos
     * @return true si se guardó, false en caso contrario
     */
    public boolean isGuardado() {
        return guardado;
    }
    
    /**
     * Obtiene el DTO con los datos completados
     * @return MinipigDTO con los datos completos
     */
    public MinipigDTO getMinipigCompleto() {
        return minipigParcial;
    }
}
