package Control.DAO;

import Modelo.Conexion.Conexion;
import Modelo.Genero;
import Modelo.MiniPig;
import Modelo.MiniPigDTO;
import Modelo.Raza;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Julian, Miguel, Andres
 * @version 1.0
 */
public class MiniPigDAO {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public MiniPigDAO() {
        con = null;
        st = null;
        rs = null;
    }

    /**
     * Inserta un nuevo MiniPig en la base de datos. Verifica que no exista
     * previamente por código o microchip.
     *
     * @param miniPig MiniPig a insertar.
     * @return {@code true} si se insertó correctamente, {@code false} si ya
     * existe.
     */
    public boolean insertar(MiniPig miniPig) {
        if (existePorCodigo(miniPig.getCodigo())
                || existePorMicrochip(miniPig.getIdMicrochip())) {
            return false;
        }
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            String sql = "INSERT INTO minipig VALUES (" + "'" + miniPig.getCodigo() + "'," + "'" + miniPig.getNombre() + "'," + "'" + miniPig.getGenero().name() + "'," + "'" + miniPig.getIdMicrochip() + "'," + "'" + miniPig.getRaza().name() + "'," + "'" + miniPig.getColor() + "'," + miniPig.getPeso() + "," + miniPig.getAltura() + "," + "'" + miniPig.getCaracteristica1() + "'," + "'" + miniPig.getCaracteristica2() + "'," + "'" + miniPig.getUrlFoto() + "')";
            st.executeUpdate(sql);
            st.close();
            Conexion.desconectar();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Consulta un MiniPig por su código único.
     *
     * @param codigo Código del MiniPig a buscar.
     * @return MiniPig encontrado, o {@code null} si no existe.
     */
    public MiniPig consultarPorCodigo(String codigo) {
        MiniPig miniPig = null;
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(
                    "SELECT * FROM minipig WHERE codigo='" + codigo + "'");
            if (rs.next()) {
                miniPig = mapearResultSet(rs);
            }
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
        }
        return miniPig;
    }

    /**
     * Consulta un MiniPig por el ID de su microchip.
     *
     * @param idMicrochip ID del microchip a buscar.
     * @return MiniPig encontrado, o {@code null} si no existe.
     */
    public MiniPig consultarPorMicrochip(String idMicrochip) {
        MiniPig miniPig = null;
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(
                    "SELECT * FROM minipig WHERE id_microchip='" + idMicrochip + "'");
            if (rs.next()) {
                miniPig = mapearResultSet(rs);
            }
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
        }
        return miniPig;
    }

    /**
     * Consulta todos los MiniPigs de una raza específica.
     *
     * @param raza Raza a filtrar.
     * @return Lista de MiniPigs de esa raza. Lista vacía si no hay resultados.
     */
    public ArrayList<MiniPig> consultarPorRaza(Raza raza) {
        ArrayList<MiniPig> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(
                    "SELECT * FROM minipig WHERE raza='" + raza.name() + "'");
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
        }
        return lista;
    }

    /**
     * Consulta todos los MiniPigs con un nombre específico.
     *
     * @param nombre Nombre a buscar.
     * @return Lista de MiniPigs con ese nombre. Lista vacía si no hay
     * resultados.
     */
    public ArrayList<MiniPig> consultarPorNombre(String nombre) {
        ArrayList<MiniPig> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(
                    "SELECT * FROM minipig WHERE nombre='" + nombre + "'");
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
        }
        return lista;
    }

    /**
     * Retorna todos los MiniPigs almacenados en la base de datos.
     *
     * @return Lista completa de MiniPigs. Lista vacía si no hay registros.
     */
    public ArrayList<MiniPig> listarTodos() {
        ArrayList<MiniPig> lista = new ArrayList<>();
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM minipig");
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
        }
        return lista;
    }

    /**
     * Elimina un MiniPig por su código.
     *
     * @param codigo Código del MiniPig a eliminar.
     * @return {@code true} si se eliminó, {@code false} si no existe.
     */
    public boolean eliminarPorCodigo(String codigo) {
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            int filas = st.executeUpdate(
                    "DELETE FROM minipig WHERE codigo='" + codigo + "'");
            st.close();
            Conexion.desconectar();
            return filas > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Elimina un MiniPig por el ID de su microchip.
     *
     * @param idMicrochip ID del microchip del MiniPig a eliminar.
     * @return {@code true} si se eliminó, {@code false} si no existe.
     */
    public boolean eliminarPorMicrochip(String idMicrochip) {
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            int filas = st.executeUpdate(
                    "DELETE FROM minipig WHERE id_microchip='" + idMicrochip + "'");
            st.close();
            Conexion.desconectar();
            return filas > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Modifica los datos de un MiniPig en la base de datos. Recibe un DTO con
     * los datos actualizados en memoria.
     *
     * @param dto DTO con los datos modificados del MiniPig.
     * @return {@code true} si la modificación fue exitosa, {@code false} si no
     * existe.
     */
    public boolean modificar(MiniPigDTO dto) {
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            String sql = "UPDATE minipig SET " + "nombre='" + dto.getNombre() + "'," + "genero='" + dto.getGenero().name() + "'," + "raza='" + dto.getRaza().name() + "'," + "color='" + dto.getColor() + "'," + "peso=" + dto.getPeso() + "," + "altura=" + dto.getAltura() + "," + "caracteristica1='" + dto.getCaracteristica1() + "'," + "caracteristica2='" + dto.getCaracteristica2() + "'," + "url_foto='" + dto.getUrlFoto() + "' " + "WHERE codigo='" + dto.getCodigo() + "'";
            int filas = st.executeUpdate(sql);
            st.close();
            Conexion.desconectar();
            return filas > 0;
        } catch (SQLException ex) {
            // Error registrado silenciosamente
            return false;
        }
    }

    /**
     * Verifica si ya existe un MiniPig con el código dado.
     *
     * @param codigo Código a verificar.
     * @return {@code true} si ya existe, {@code false} si no.
     */
    public boolean existePorCodigo(String codigo) {
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(
                    "SELECT COUNT(*) FROM minipig WHERE codigo='" + codigo + "'");
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
        }
        return false;
    }

    /**
     * Verifica si ya existe un MiniPig con el ID de microchip dado.
     *
     * @param idMicrochip ID de microchip a verificar.
     * @return {@code true} si ya existe, {@code false} si no.
     */
    public boolean existePorMicrochip(String idMicrochip) {
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(
                    "SELECT COUNT(*) FROM minipig WHERE id_microchip='" + idMicrochip + "'");
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
        }
        return false;
    }

    /**
     * @param rs ResultSet posicionado en una fila válida.
     * @return Objeto MiniPig construido con los datos de la fila.
     * @throws SQLException si ocurre un error al leer el ResultSet.
     */
    private MiniPig mapearResultSet(ResultSet rs) throws SQLException {
        return new MiniPig(rs.getString("codigo"), rs.getString("nombre"), Genero.valueOf(rs.getString("genero")), rs.getString("id_microchip"), Raza.valueOf(rs.getString("raza")), rs.getString("color"), rs.getDouble("peso"), rs.getDouble("altura"), rs.getString("caracteristica1"), rs.getString("caracteristica2"), rs.getString("url_foto")
        );
    }
}
