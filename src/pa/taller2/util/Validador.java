/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pa.taller2.util;

import pa.taller2.controlador.MinipigDTO;
import java.util.regex.Pattern;

/**
 * Clase utilitaria para realizar validaciones en la aplicación.
 * 
 * Propósito:
 * - Centralizar todas las validaciones de datos
 * - Garantizar la integridad de los datos antes de procesarlos
 * - Reutilizar lógica de validación en diferentes partes de la aplicación
 * 
 * Aplica el principio de Responsabilidad Única (SRP):
 * - Solo se encarga de validar datos
 * - No contiene lógica de negocio ni de presentación
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class Validador {
    
    // Patrones de validación
    private static final Pattern PATRON_CODIGO = Pattern.compile("^[A-Za-z0-9]{3,10}$");
    private static final Pattern PATRON_MICROCHIP = Pattern.compile("^[A-Fa-f0-9]{6,20}$");
    private static final Pattern PATRON_NOMBRE = Pattern.compile("^[A-Za-záéíóúÁÉÍÓÚñÑ\\s]{2,50}$");
    private static final Pattern PATRON_COLOR = Pattern.compile("^[A-Za-záéíóúÁÉÍÓÚñÑ\\s]{2,30}$");
    
    /**
     * Constructor privado para evitar instanciación
     */
    private Validador() {
        // Clase utilitaria, no debe instanciarse
    }
    
    // ==================== VALIDACIONES DE CAMPOS ====================
    
    /**
     * Valida el código del minipig
     * @param codigo Código a validar
     * @return true si es válido
     */
    public static boolean validarCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }
        return PATRON_CODIGO.matcher(codigo.trim()).matches();
    }
    
    /**
     * Valida el nombre del minipig
     * @param nombre Nombre a validar
     * @return true si es válido
     */
    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return PATRON_NOMBRE.matcher(nombre.trim()).matches();
    }
    
    /**
     * Valida el ID del microchip
     * @param microchip ID a validar
     * @return true si es válido
     */
    public static boolean validarMicrochip(String microchip) {
        if (microchip == null || microchip.trim().isEmpty()) {
            return false;
        }
        return PATRON_MICROCHIP.matcher(microchip.trim()).matches();
    }
    
    /**
     * Valida el género
     * @param genero Género a validar
     * @return true si es válido (MACHO o HEMBRA)
     */
    public static boolean validarGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            return false;
        }
        String gen = genero.trim().toUpperCase();
        return gen.equals("MACHO") || gen.equals("HEMBRA") ||
               gen.equals("M") || gen.equals("H") ||
               gen.equals("MALE") || gen.equals("FEMALE");
    }
    
    /**
     * Valida la raza
     * @param raza Raza a validar
     * @return true si no está vacía
     */
    public static boolean validarRaza(String raza) {
        return raza != null && !raza.trim().isEmpty();
    }
    
    /**
     * Valida el color
     * @param color Color a validar
     * @return true si es válido (puede ser vacío)
     */
    public static boolean validarColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            return true; // Campo opcional
        }
        return PATRON_COLOR.matcher(color.trim()).matches();
    }
    
    /**
     * Valida el peso
     * @param peso Peso a validar
     * @return true si es un número positivo
     */
    public static boolean validarPeso(double peso) {
        return peso >= 0 && peso <= 500; // Máximo 500 kg
    }
    
    /**
     * Valida la altura
     * @param altura Altura a validar
     * @return true si es un número positivo
     */
    public static boolean validarAltura(double altura) {
        return altura >= 0 && altura <= 200; // Máximo 200 cm
    }
    
    /**
     * Valida una característica
     * @param caracteristica Característica a validar
     * @return true si es válida (puede ser vacía)
     */
    public static boolean validarCaracteristica(String caracteristica) {
        if (caracteristica == null || caracteristica.trim().isEmpty()) {
            return true; // Campo opcional
        }
        return caracteristica.length() <= 200;
    }
    
    /**
     * Valida la URL de la foto
     * @param urlFoto URL a validar
     * @return true si es válida (puede ser vacía)
     */
    public static boolean validarUrlFoto(String urlFoto) {
        if (urlFoto == null || urlFoto.trim().isEmpty()) {
            return true; // Campo opcional
        }
        // Validar formato básico de URL/ruta
        return urlFoto.length() <= 500;
    }
    
    // ==================== VALIDACIONES DE DTO ====================
    
    /**
     * Valida que un DTO tenga todos los campos requeridos
     * @param dto DTO a validar
     * @return true si todos los campos requeridos son válidos
     */
    public static boolean validarDTOCompleto(MinipigDTO dto) {
        if (dto == null) return false;
        
        return validarCodigo(dto.getCodigo()) &&
               validarNombre(dto.getNombre()) &&
               validarGenero(dto.getGenero()) &&
               validarMicrochip(dto.getIdMicrochip()) &&
               validarRaza(dto.getRaza());
    }
    
    /**
     * Valida los campos opcionales de un DTO
     * @param dto DTO a validar
     * @return Lista de campos opcionales con problemas
     */
    public static java.util.List<String> validarCamposOpcionales(MinipigDTO dto) {
        java.util.List<String> problemas = new java.util.ArrayList<>();
        
        if (dto == null) return problemas;
        
        if (!validarColor(dto.getColor())) {
            problemas.add("Color inválido");
        }
        
        if (!validarPeso(dto.getPeso())) {
            problemas.add("Peso inválido (debe ser entre 0 y 500 kg)");
        }
        
        if (!validarAltura(dto.getAltura())) {
            problemas.add("Altura inválida (debe ser entre 0 y 200 cm)");
        }
        
        if (!validarCaracteristica(dto.getCaracteristica1())) {
            problemas.add("Característica 1 demasiado larga");
        }
        
        if (!validarCaracteristica(dto.getCaracteristica2())) {
            problemas.add("Característica 2 demasiado larga");
        }
        
        if (!validarUrlFoto(dto.getUrlFoto())) {
            problemas.add("URL de foto inválida");
        }
        
        return problemas;
    }
    
    // ==================== VALIDACIONES NUMÉRICAS ====================
    
    /**
     * Valida si un string puede convertirse a double
     * @param valor String a validar
     * @return true si es un número válido
     */
    public static boolean esNumeroValido(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(valor.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Convierte un string a double de forma segura
     * @param valor String a convertir
     * @param valorPorDefecto Valor por defecto si hay error
     * @return double convertido o valor por defecto
     */
    public static double convertirADouble(String valor, double valorPorDefecto) {
        if (esNumeroValido(valor)) {
            try {
                return Double.parseDouble(valor.trim());
            } catch (NumberFormatException e) {
                return valorPorDefecto;
            }
        }
        return valorPorDefecto;
    }
}
