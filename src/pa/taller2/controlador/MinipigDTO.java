/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pa.taller2.controlador;

import pa.taller2.modelo.Minipig;
import pa.taller2.modelo.Genero;
import pa.taller2.modelo.Raza;

/**
 * Clase DTO (Data Transfer Object) para transferir datos entre la capa de vista y controlador.
 * 
 * Propósito:
 * - Evita exponer directamente las entidades del modelo a la vista
 * - Desacopla la capa de presentación de la capa de persistencia
 * - Facilita la transferencia de datos entre capas
 * 
 * Aplica el patrón DTO y el principio de Responsabilidad Única (SRP):
 * - Solo se encarga de transportar datos, no contiene lógica de negocio
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class MinipigDTO {
    
    private String codigo;
    private String nombre;
    private String genero;
    private String idMicrochip;
    private String raza;
    private String color;
    private double peso;
    private double altura;
    private String caracteristica1;
    private String caracteristica2;
    private String urlFoto;
    
    /**
     * Constructor vacío
     */
    public MinipigDTO() {
    }
    
    /**
     * Constructor con todos los atributos
     */
    public MinipigDTO(String codigo, String nombre, String genero, String idMicrochip, 
                      String raza, String color, double peso, double altura, 
                      String caracteristica1, String caracteristica2, String urlFoto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.idMicrochip = idMicrochip;
        this.raza = raza;
        this.color = color;
        this.peso = peso;
        this.altura = altura;
        this.caracteristica1 = caracteristica1;
        this.caracteristica2 = caracteristica2;
        this.urlFoto = urlFoto;
    }
    
    // Getters y Setters
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getIdMicrochip() {
        return idMicrochip;
    }
    
    public void setIdMicrochip(String idMicrochip) {
        this.idMicrochip = idMicrochip;
    }
    
    public String getRaza() {
        return raza;
    }
    
    public void setRaza(String raza) {
        this.raza = raza;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public double getAltura() {
        return altura;
    }
    
    public void setAltura(double altura) {
        this.altura = altura;
    }
    
    public String getCaracteristica1() {
        return caracteristica1;
    }
    
    public void setCaracteristica1(String caracteristica1) {
        this.caracteristica1 = caracteristica1;
    }
    
    public String getCaracteristica2() {
        return caracteristica2;
    }
    
    public void setCaracteristica2(String caracteristica2) {
        this.caracteristica2 = caracteristica2;
    }
    
    public String getUrlFoto() {
        return urlFoto;
    }
    
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    
    /**
     * Convierte este DTO a una entidad Minipig del modelo
     * @return Entidad Minipig con los datos del DTO
     */
    public Minipig toEntity() {
        Minipig minipig = new Minipig();
        
        minipig.setCodigo(this.codigo);
        minipig.setNombre(this.nombre);
        
        // Convertir String a Enum Genero
        if (this.genero != null && !this.genero.isEmpty()) {
            minipig.setGenero(Genero.fromString(this.genero));
        }
        
        minipig.setIdMicrochip(this.idMicrochip);
        
        // Convertir String a Enum Raza
        if (this.raza != null && !this.raza.isEmpty()) {
            minipig.setRaza(Raza.fromNombre(this.raza));
        }
        
        minipig.setColor(this.color);
        minipig.setPeso(this.peso);
        minipig.setAltura(this.altura);
        minipig.setCaracteristica1(this.caracteristica1);
        minipig.setCaracteristica2(this.caracteristica2);
        minipig.setUrlFoto(this.urlFoto);
        
        return minipig;
    }
    
    /**
     * Crea un DTO a partir de una entidad Minipig
     * @param minipig Entidad Minipig del modelo
     * @return DTO con los datos del minipig
     */
    public static MinipigDTO fromEntity(Minipig minipig) {
        if (minipig == null) return null;
        
        MinipigDTO dto = new MinipigDTO();
        
        dto.setCodigo(minipig.getCodigo());
        dto.setNombre(minipig.getNombre());
        dto.setGenero(minipig.getGenero() != null ? minipig.getGenero().name() : "");
        dto.setIdMicrochip(minipig.getIdMicrochip());
        dto.setRaza(minipig.getRaza() != null ? minipig.getRaza().getNombre() : "");
        dto.setColor(minipig.getColor());
        dto.setPeso(minipig.getPeso());
        dto.setAltura(minipig.getAltura());
        dto.setCaracteristica1(minipig.getCaracteristica1());
        dto.setCaracteristica2(minipig.getCaracteristica2());
        dto.setUrlFoto(minipig.getUrlFoto());
        
        return dto;
    }
    
    /**
     * Verifica si el DTO tiene todos los campos requeridos
     * @return true si los campos requeridos están presentes
     */
    public boolean isComplete() {
        return codigo != null && !codigo.trim().isEmpty() &&
               nombre != null && !nombre.trim().isEmpty() &&
               genero != null && !genero.trim().isEmpty() &&
               idMicrochip != null && !idMicrochip.trim().isEmpty() &&
               raza != null && !raza.trim().isEmpty();
    }
    
    /**
     * Retorna una lista de los campos que están null o vacíos
     * @return Lista de nombres de campos requeridos faltantes
     */
    public java.util.List<String> getCamposFaltantes() {
        java.util.List<String> faltantes = new java.util.ArrayList<>();
        
        if (codigo == null || codigo.trim().isEmpty()) faltantes.add("Código");
        if (nombre == null || nombre.trim().isEmpty()) faltantes.add("Nombre");
        if (genero == null || genero.trim().isEmpty()) faltantes.add("Género");
        if (idMicrochip == null || idMicrochip.trim().isEmpty()) faltantes.add("Microchip");
        if (raza == null || raza.trim().isEmpty()) faltantes.add("Raza");
        
        return faltantes;
    }
    
    @Override
    public String toString() {
        return String.format("MinipigDTO{codigo='%s', nombre='%s', genero='%s', microchip='%s', raza='%s'}", 
                codigo, nombre, genero, idMicrochip, raza);
    }
}
