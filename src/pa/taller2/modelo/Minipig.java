
package pa.taller2.modelo;

import java.util.Objects;

/**
 * Clase que representa la entidad Minipig en el sistema.
 * Esta clase actúa como TransferObject y como clase persistente del modelo.
 * Contiene todos los atributos de un minipig según la especificación del taller.
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class Minipig {
    
    // Atributos principales según el documento
    private String codigo;              // Código único del minipig
    private String nombre;              // Nombre dado por el propietario
    private Genero genero;              // Género/sexo del minipig
    private String idMicrochip;         // ID del microchip (único)
    private Raza raza;                   // Raza del minipig
    private String color;                // Color del pelaje
    private double peso;                 // Peso en kilogramos
    private double altura;               // Altura en centímetros
    private String caracteristica1;      // Primera característica especial
    private String caracteristica2;      // Segunda característica especial
    private String urlFoto;              // URL o ruta de la foto
    
    /**
     * Constructor vacío requerido para algunas operaciones de persistencia
     */
    public Minipig() {
        // Constructor vacío
    }
    
    /**
     * Constructor con todos los atributos
     * @param codigo Código único del minipig
     * @param nombre Nombre del minipig
     * @param genero Género del minipig
     * @param idMicrochip ID del microchip
     * @param raza Raza del minipig
     * @param color Color del pelaje
     * @param peso Peso en kg
     * @param altura Altura en cm
     * @param caracteristica1 Primera característica
     * @param caracteristica2 Segunda característica
     * @param urlFoto Ruta de la foto
     */
    public Minipig(String codigo, String nombre, Genero genero, String idMicrochip, 
                   Raza raza, String color, double peso, double altura, 
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
    
    // Getters y Setters con documentación JavaDoc

    /**
     * Obtiene el código único del minipig
     * @return String con el código
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código único del minipig
     * @param codigo Código a asignar
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre del minipig
     * @return String con el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del minipig
     * @param nombre Nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el género del minipig
     * @return Genero del minipig
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * Establece el género del minipig
     * @param genero Género a asignar
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Obtiene el ID del microchip
     * @return String con el ID del microchip
     */
    public String getIdMicrochip() {
        return idMicrochip;
    }

    /**
     * Establece el ID del microchip
     * @param idMicrochip ID a asignar
     */
    public void setIdMicrochip(String idMicrochip) {
        this.idMicrochip = idMicrochip;
    }

    /**
     * Obtiene la raza del minipig
     * @return Raza del minipig
     */
    public Raza getRaza() {
        return raza;
    }

    /**
     * Establece la raza del minipig
     * @param raza Raza a asignar
     */
    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    /**
     * Obtiene el color del minipig
     * @return String con el color
     */
    public String getColor() {
        return color;
    }

    /**
     * Establece el color del minipig
     * @param color Color a asignar
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Obtiene el peso en kilogramos
     * @return double con el peso
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Establece el peso en kilogramos
     * @param peso Peso a asignar
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Obtiene la altura en centímetros
     * @return double con la altura
     */
    public double getAltura() {
        return altura;
    }

    /**
     * Establece la altura en centímetros
     * @param altura Altura a asignar
     */
    public void setAltura(double altura) {
        this.altura = altura;
    }

    /**
     * Obtiene la primera característica especial
     * @return String con la característica
     */
    public String getCaracteristica1() {
        return caracteristica1;
    }

    /**
     * Establece la primera característica especial
     * @param caracteristica1 Característica a asignar
     */
    public void setCaracteristica1(String caracteristica1) {
        this.caracteristica1 = caracteristica1;
    }

    /**
     * Obtiene la segunda característica especial
     * @return String con la característica
     */
    public String getCaracteristica2() {
        return caracteristica2;
    }

    /**
     * Establece la segunda característica especial
     * @param caracteristica2 Característica a asignar
     */
    public void setCaracteristica2(String caracteristica2) {
        this.caracteristica2 = caracteristica2;
    }

    /**
     * Obtiene la URL o ruta de la foto
     * @return String con la ruta de la foto
     */
    public String getUrlFoto() {
        return urlFoto;
    }

    /**
     * Establece la URL o ruta de la foto
     * @param urlFoto Ruta de la foto a asignar
     */
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    
    /**
     * Verifica si el minipig tiene todos los campos requeridos
     * @return true si todos los campos obligatorios están completos
     */
    public boolean isComplete() {
        return codigo != null && !codigo.trim().isEmpty() &&
               nombre != null && !nombre.trim().isEmpty() &&
               genero != null &&
               idMicrochip != null && !idMicrochip.trim().isEmpty() &&
               raza != null;
    }
    
    /**
     * Retorna una representación en String del objeto Minipig
     * @return String con los datos del minipig formateados
     */
    @Override
    public String toString() {
        return String.format("Minipig{codigo='%s', nombre='%s', genero=%s, microchip='%s', raza=%s}", 
                codigo, nombre, genero != null ? genero.getEspanol() : "null", 
                idMicrochip, raza != null ? raza.getNombre() : "null");
    }
    
    /**
     * Compara dos objetos Minipig por su código y microchip
     * @param obj Objeto a comparar
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Minipig minipig = (Minipig) obj;
        return Objects.equals(codigo, minipig.codigo) && 
               Objects.equals(idMicrochip, minipig.idMicrochip);
    }
    
    /**
     * Genera el hashcode basado en código y microchip
     * @return int con el hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo, idMicrochip);
    }
}