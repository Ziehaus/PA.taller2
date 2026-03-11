
package pa.taller2.modelo;

/**
 * Enumeración que representa los géneros posibles para un minipig.
 * Esta clase forma parte del modelo de datos y se utiliza para
 * garantizar que solo se asignen valores válidos al género.
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public enum Genero {
    MACHO,
    HEMBRA;
    
    /**
     * Obtiene una representación en español del género.
     * @return String con el género en español (Macho/Hembra)
     */
    public String getEspanol() {
        switch(this) {
            case MACHO: return "Macho";
            case HEMBRA: return "Hembra";
            default: return this.name();
        }
    }
    
    /**
     * Convierte un String a Género, ignorando mayúsculas/minúsculas
     * @param genero String con el género
     * @return Genero correspondiente o null si no coincide
     */
    public static Genero fromString(String genero) {
        if (genero == null) return null;
        
        String gen = genero.trim().toUpperCase();
        switch(gen) {
            case "MACHO":
            case "MASCULINO":
            case "M":
            case "MALE":
                return MACHO;
            case "HEMBRA":
            case "FEMENINO":
            case "F":
            case "FEMALE":
                return HEMBRA;
            default:
                return null;
        }
    }
}
