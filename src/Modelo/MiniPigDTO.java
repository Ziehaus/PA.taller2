package Modelo;

/**
 * Objeto de Transferencia de Datos (DTO) para MiniPig. Se usa exclusivamente
 * para operaciones de modificación: primero se carga el MiniPig desde la base
 * de datos a este DTO, se aplican los cambios en memoria, y luego se envía al
 * DAO.
 *
 * <p>
 * Se comunica con: {@link MiniPig} para construcción y conversión, y con
 * {@code MiniPigDAOImpl} para enviar la modificación.</p>
 *
 * @author Julian, Miguel, Andres
 * @version 1.0
 */
public class MiniPigDTO {

    private final String codigo;
    private String nombre;
    private Genero genero;
    private final String idMicrochip;
    private Raza raza;
    private String color;
    private double peso;
    private double altura;
    private String caracteristica1;
    private String caracteristica2;
    private String urlFoto;

    /**
     * Construye un DTO a partir de un {@link MiniPig} existente. Los campos no
     * modificables (codigo, idMicrochip) se marcan final.
     *
     * @param miniPig MiniPig consultado desde la base de datos.
     */
    public MiniPigDTO(MiniPig miniPig) {
        this.codigo = miniPig.getCodigo();
        this.nombre = miniPig.getNombre();
        this.genero = miniPig.getGenero();
        this.idMicrochip = miniPig.getIdMicrochip();
        this.raza = miniPig.getRaza();
        this.color = miniPig.getColor();
        this.peso = miniPig.getPeso();
        this.altura = miniPig.getAltura();
        this.caracteristica1 = miniPig.getCaracteristica1();
        this.caracteristica2 = miniPig.getCaracteristica2();
        this.urlFoto = miniPig.getUrlFoto();
    }

    /**
     * Convierte este DTO en un objeto {@link MiniPig}. Usado para reconstruir
     * la entidad después de la modificación.
     *
     * @return Nuevo objeto MiniPig con los datos actualizados del DTO.
     */
    public MiniPig toMiniPig() {
        return new MiniPig(codigo, nombre, genero, idMicrochip,
                raza, color, peso, altura,
                caracteristica1, caracteristica2, urlFoto);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getIdMicrochip() {
        return idMicrochip;
    }

    public Raza getRaza() {
        return raza;
    }

    public String getColor() {
        return color;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }

    public String getCaracteristica1() {
        return caracteristica1;
    }

    public String getCaracteristica2() {
        return caracteristica2;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setCaracteristica1(String caracteristica1) {
        this.caracteristica1 = caracteristica1;
    }

    public void setCaracteristica2(String caracteristica2) {
        this.caracteristica2 = caracteristica2;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
