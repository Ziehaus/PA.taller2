
package pa.taller2.DAO;

import pa.taller2.modelo.ConexionBD;
import pa.taller2.modelo.Minipig;
import pa.taller2.modelo.Genero;
import pa.taller2.modelo.Raza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de IMinipigDAO para MySQL.
 * Esta clase maneja todas las operaciones de acceso a datos para la entidad Minipig.
 * 
 * Implementa el patrón Singleton para garantizar una única instancia del DAO.
 * Aplica el principio de Responsabilidad Única (SRP) de SOLID:
 * - Únicamente se encarga del acceso a datos
 * 
 * @author Universidad Distrital
 * @version 1.0
 */
public class MinipigDAOImpl implements IMinipigDAO {
    
    // Constantes con las consultas SQL
    private static final String SQL_INSERT = 
        "INSERT INTO minipigs (codigo, nombre, genero, id_microchip, raza, color, peso, altura, caracteristica1, caracteristica2, url_foto) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE = 
        "UPDATE minipigs SET nombre=?, genero=?, color=?, peso=?, altura=?, caracteristica1=?, caracteristica2=?, url_foto=? " +
        "WHERE codigo=?";
    
    private static final String SQL_DELETE = "DELETE FROM minipigs WHERE codigo=?";
    
    private static final String SQL_SELECT_BY_CODIGO = 
        "SELECT * FROM minipigs WHERE codigo=?";
    
    private static final String SQL_SELECT_BY_MICROCHIP = 
        "SELECT * FROM minipigs WHERE id_microchip=?";
    
    private static final String SQL_SELECT_BY_RAZA = 
        "SELECT * FROM minipigs WHERE raza LIKE ?";
    
    private static final String SQL_SELECT_BY_NOMBRE = 
        "SELECT * FROM minipigs WHERE nombre LIKE ?";
    
    private static final String SQL_SELECT_ALL = 
        "SELECT * FROM minipigs ORDER BY nombre";
    
    private static final String SQL_COUNT_BY_CODIGO = 
        "SELECT COUNT(*) FROM minipigs WHERE codigo=?";
    
    private static final String SQL_COUNT_BY_MICROCHIP = 
        "SELECT COUNT(*) FROM minipigs WHERE id_microchip=?";
    
    // Instancia única (Singleton)
    private static MinipigDAOImpl instancia;
    private final ConexionBD conexionBD;
    
    /**
     * Constructor privado para Singleton
     */
    private MinipigDAOImpl() {
        this.conexionBD = ConexionBD.getInstance();
    }
    
    /**
     * Obtiene la única instancia de MinipigDAOImpl
     * @return Instancia única del DAO
     */
    public static synchronized MinipigDAOImpl getInstance() {
        if (instancia == null) {
            instancia = new MinipigDAOImpl();
        }
        return instancia;
    }
    
    @Override
    public boolean insertar(Minipig minipig) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            // Validar que el minipig no exista ya
            if (existePorCodigo(minipig.getCodigo())) {
                System.err.println("Ya existe un minipig con el código: " + minipig.getCodigo());
                return false;
            }
            
            if (existePorMicrochip(minipig.getIdMicrochip())) {
                System.err.println("Ya existe un minipig con el microchip: " + minipig.getIdMicrochip());
                return false;
            }
            
            conn = conexionBD.getConexion();
            pstmt = conn.prepareStatement(SQL_INSERT);
            
            pstmt.setString(1, minipig.getCodigo());
            pstmt.setString(2, minipig.getNombre());
            pstmt.setString(3, minipig.getGenero() != null ? minipig.getGenero().name() : null);
            pstmt.setString(4, minipig.getIdMicrochip());
            pstmt.setString(5, minipig.getRaza() != null ? minipig.getRaza().getNombre() : null);
            pstmt.setString(6, minipig.getColor());
            pstmt.setDouble(7, minipig.getPeso());
            pstmt.setDouble(8, minipig.getAltura());
            pstmt.setString(9, minipig.getCaracteristica1());
            pstmt.setString(10, minipig.getCaracteristica2());
            pstmt.setString(11, minipig.getUrlFoto());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al insertar minipig: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(null, pstmt, conn);
        }
    }
    
    @Override
    public boolean actualizar(Minipig minipig) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = conexionBD.getConexion();
            pstmt = conn.prepareStatement(SQL_UPDATE);
            
            pstmt.setString(1, minipig.getNombre());
            pstmt.setString(2, minipig.getGenero() != null ? minipig.getGenero().name() : null);
            pstmt.setString(3, minipig.getColor());
            pstmt.setDouble(4, minipig.getPeso());
            pstmt.setDouble(5, minipig.getAltura());
            pstmt.setString(6, minipig.getCaracteristica1());
            pstmt.setString(7, minipig.getCaracteristica2());
            pstmt.setString(8, minipig.getUrlFoto());
            pstmt.setString(9, minipig.getCodigo()); // WHERE codigo=?
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al actualizar minipig: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(null, pstmt, conn);
        }
    }
    
    @Override
    public boolean eliminar(String codigo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = conexionBD.getConexion();
            pstmt = conn.prepareStatement(SQL_DELETE);
            pstmt.setString(1, codigo);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al eliminar minipig: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(null, pstmt, conn);
        }
    }
    
    @Override
    public Minipig buscarPorCodigo(String codigo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = conexionBD.getConexion();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_CODIGO);
            pstmt.setString(1, codigo);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearMinipig(rs);
            }
            
            return null;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al buscar minipig por código: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            cerrarRecursos(rs, pstmt, conn);
        }
    }
    
    @Override
    public Minipig buscarPorMicrochip(String idMicrochip) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = conexionBD.getConexion();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_MICROCHIP);
            pstmt.setString(1, idMicrochip);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearMinipig(rs);
            }
            
            return null;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al buscar minipig por microchip: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            cerrarRecursos(rs, pstmt, conn);
        }
    }
    
    @Override
    public List<Minipig> buscarPorRaza(String raza) {
        List<Minipig> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = conexionBD.getConexion();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_RAZA);
            pstmt.setString(1, "%" + raza + "%");
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearMinipig(rs));
            }
            
            return lista;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al buscar minipigs por raza: " + e.getMessage());
            e.printStackTrace();
            return lista;
        } finally {
            cerrarRecursos(rs, pstmt, conn);
        }
    }
    
    @Override
    public List<Minipig> buscarPorNombre(String nombre) {
        List<Minipig> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = conexionBD.getConexion();
            pstmt = conn.prepareStatement(SQL_SELECT_BY_NOMBRE);
            pstmt.setString(1, "%" + nombre + "%");
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearMinipig(rs));
            }
            
            return lista;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al buscar minipigs por nombre: " + e.getMessage());
            e.printStackTrace();
            return lista;
        } finally {
            cerrarRecursos(rs, pstmt, conn);
        }
    }
    
    @Override
    public List<Minipig> listarTodos() {
        List<Minipig> lista = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = conexionBD.getConexion();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            
            while (rs.next()) {
                lista.add(mapearMinipig(rs));
            }
            
            return lista;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al listar minipigs: " + e.getMessage());
            e.printStackTrace();
            return lista;
        } finally {
            cerrarRecursos(rs, stmt, conn);
        }
    }
    
    @Override
    public boolean existePorCodigo(String codigo) {
        return existeRegistro(SQL_COUNT_BY_CODIGO, codigo);
    }
    
    @Override
    public boolean existePorMicrochip(String idMicrochip) {
        return existeRegistro(SQL_COUNT_BY_MICROCHIP, idMicrochip);
    }
    
    /**
     * Método auxiliar para verificar existencia de registros
     * @param sql Consulta SQL de conteo
     * @param parametro Parámetro a buscar
     * @return true si existe al menos un registro
     */
    private boolean existeRegistro(String sql, String parametro) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = conexionBD.getConexion();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parametro);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al verificar existencia: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(rs, pstmt, conn);
        }
    }
    
    /**
     * Convierte un ResultSet en un objeto Minipig
     * @param rs ResultSet con los datos
     * @return Objeto Minipig mapeado
     * @throws SQLException Si hay error al leer el ResultSet
     */
    private Minipig mapearMinipig(ResultSet rs) throws SQLException {
        Minipig minipig = new Minipig();
        
        minipig.setCodigo(rs.getString("codigo"));
        minipig.setNombre(rs.getString("nombre"));
        
        // Convertir String a Enum Genero
        String generoStr = rs.getString("genero");
        if (generoStr != null) {
            minipig.setGenero(Genero.valueOf(generoStr));
        }
        
        minipig.setIdMicrochip(rs.getString("id_microchip"));
        
        // Convertir String a Enum Raza
        String razaStr = rs.getString("raza");
        if (razaStr != null) {
            minipig.setRaza(Raza.fromNombre(razaStr));
        }
        
        minipig.setColor(rs.getString("color"));
        minipig.setPeso(rs.getDouble("peso"));
        minipig.setAltura(rs.getDouble("altura"));
        minipig.setCaracteristica1(rs.getString("caracteristica1"));
        minipig.setCaracteristica2(rs.getString("caracteristica2"));
        minipig.setUrlFoto(rs.getString("url_foto"));
        
        return minipig;
    }
    
    /**
     * Cierra los recursos de base de datos de forma segura
     * @param rs ResultSet a cerrar
     * @param stmt Statement a cerrar
     * @param conn Connection a cerrar
     */
    private void cerrarRecursos(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar ResultSet: " + e.getMessage());
        }
        
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar Statement: " + e.getMessage());
        }
        
        // No cerramos la conexión aquí porque queremos mantenerla abierta
        // La conexión se cierra al finalizar la aplicación
    }
}
