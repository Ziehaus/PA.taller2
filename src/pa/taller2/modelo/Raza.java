
package pa.taller2.modelo;
/**
 * Enumeración que representa todas las razas de minipigs disponibles
 * según la información del documento.
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public enum Raza {
    JULIANA("Juliana", "Raza esbelta y atlética, hasta 32kg y 43cm"),
    GOTTINGEN("Göttingen", "Raza alemana para investigación, 35-45kg"),
    VIETNAMITA("Vietnamita", "También llamado barrigón, hasta 136kg"),
    KUNEKUNE("Kunekune", "Raza pastoril, pelo largo, 60-200kg"),
    YUCATAN("Yucatán", "Sin pelo, color grisáceo, 70-83kg"),
    GUINEA_AMERICANO("Guinea Americano", "Pelo negro, 55-68cm alto"),
    MULEFOOT("Mulefoot", "Raza poco común en Colombia"),
    OSSABAW_ISLAND("Ossabaw Island", "Raza poco común"),
    MEISHAN("Meishan", "Raza poco común"),
    HANFORD_MINI_SWINE("Hanford Mini Swine", "Raza poco común"),
    CONGO("Congo", "Raza criolla colombiana, negra o pintada oscura");
    
    private final String nombre;
    private final String descripcion;
    
    /**
     * Constructor del enum Raza
     * @param nombre Nombre comercial de la raza
     * @param descripcion Breve descripción de la raza
     */
    Raza(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene el nombre comercial de la raza
     * @return String con el nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene la descripción de la raza
     * @return String con la descripción
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Busca una raza por su nombre comercial
     * @param nombreRaza Nombre a buscar
     * @return Raza correspondiente o null si no se encuentra
     */
    public static Raza fromNombre(String nombreRaza) {
        if (nombreRaza == null) return null;
        
        String nombreBuscar = nombreRaza.trim().toLowerCase();
        for (Raza raza : Raza.values()) {
            if (raza.getNombre().toLowerCase().contains(nombreBuscar) ||
                raza.name().toLowerCase().contains(nombreBuscar)) {
                return raza;
            }
        }
        return null;
    }
}