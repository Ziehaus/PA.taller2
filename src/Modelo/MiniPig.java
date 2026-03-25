package Modelo;

/**
 * Entidad que representa un MiniPig registrado en el sistema.
 *
 * @author Julian, Miguel, Andres
 * @version 1.0
 */
public class MiniPig {

    private String codigo;
    private String nombre;
    private Genero genero;
    private String idMicrochip;
    private Raza raza;
    private String color;
    private double peso;
    private double altura;
    private String caracteristica1;
    private String caracteristica2;
    private String urlFoto;

    public MiniPig(String codigo, String nombre, Genero genero, String idMicrochip, Raza raza, String color, double peso, double altura, String caracteristica1, String caracteristica2, String urlFoto) {
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

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getIdMicrochip() {
        return idMicrochip;
    }

    public Raza getRaza() {
        return raza;
    }

    public void setRaza(Raza raza) {
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

    @Override
    public String toString() {
        return "MiniPig{" + "codigo='" + codigo + '\'' + ", nombre='" + nombre + '\'' + ", genero=" + genero + ", idMicrochip='" + idMicrochip + '\'' + ", raza=" + raza + ", color='" + color + '\'' + ", peso=" + peso + "kg" + ", altura=" + altura + "cm" + ", caracteristica1='" + caracteristica1 + '\'' + ", caracteristica2='" + caracteristica2 + '\'' + ", urlFoto='" + urlFoto + '\'' + '}';
    }
}
