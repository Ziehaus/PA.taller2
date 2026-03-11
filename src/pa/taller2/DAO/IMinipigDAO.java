/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pa.taller2.DAO;

import java.util.List;
import pa.taller2.modelo.Minipig;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad Minipig.
 * Esta interfaz abstrae al BusinessObject (Controlador) de los detalles de implementación
 * de la fuente de datos.
 * 
 * Aplica el principio de Inversión de Dependencias (DIP) de SOLID:
 * - Las clases de alto nivel (Controlador) dependen de esta abstracción
 * - Las clases de bajo nivel (MinipigDAOImpl) implementan esta interfaz
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public interface IMinipigDAO {
    
    // Operaciones CRUD básicas
    
    /**
     * Inserta un nuevo minipig en la base de datos
     * @param minipig Objeto Minipig a insertar
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    boolean insertar(Minipig minipig);
    
    /**
     * Actualiza los datos de un minipig existente
     * @param minipig Objeto Minipig con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    boolean actualizar(Minipig minipig);
    
    /**
     * Elimina un minipig de la base de datos por su código
     * @param codigo Código único del minipig a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    boolean eliminar(String codigo);
    
    // Métodos de búsqueda
    
    /**
     * Busca un minipig por su código único
     * @param codigo Código a buscar
     * @return Objeto Minipig si existe, null en caso contrario
     */
    Minipig buscarPorCodigo(String codigo);
    
    /**
     * Busca un minipig por el ID de su microchip
     * @param idMicrochip ID del microchip a buscar
     * @return Objeto Minipig si existe, null en caso contrario
     */
    Minipig buscarPorMicrochip(String idMicrochip);
    
    /**
     * Busca todos los minipigs de una raza específica
     * @param raza Nombre de la raza a buscar
     * @return Lista de minipigs de esa raza
     */
    List<Minipig> buscarPorRaza(String raza);
    
    /**
     * Busca minipigs por su nombre (pueden haber varios con el mismo nombre)
     * @param nombre Nombre a buscar
     * @return Lista de minipigs con ese nombre
     */
    List<Minipig> buscarPorNombre(String nombre);
    
    /**
     * Obtiene todos los minipigs de la base de datos
     * @return Lista con todos los minipigs
     */
    List<Minipig> listarTodos();
    
    /**
     * Verifica si existe un minipig con el código especificado
     * @param codigo Código a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorCodigo(String codigo);
    
    /**
     * Verifica si existe un minipig con el ID de microchip especificado
     * @param idMicrochip ID de microchip a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorMicrochip(String idMicrochip);
}