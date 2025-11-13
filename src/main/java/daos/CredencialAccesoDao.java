package daos;

import daos.Generic.GenericDao;
import entities.CredencialAcceso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class CredencialAccesoDao implements GenericDao<CredencialAcceso>{

    @Override
    public CredencialAcceso crear(CredencialAcceso ca, Connection conn) throws SQLException {
        String instruccion = "INSERT INTO CredencialAcceso (hashPassword, salt, requiereReset) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(instruccion, Statement.RETURN_GENERATED_KEYS)){
            
            ps.setString(1, ca.getHashPassword());
            ps.setString(2, ca.getSalt());
            ps.setBoolean(3, ca.isRequiereReset());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo insertar la credencial, no se afecto ninguna fila.");
            }
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    return leer(idGenerado, conn);
                }
            } catch (SQLException e) {
                System.err.println("Error SQL al crear la credencial: " + e.getMessage());
                throw new SQLException("Error al crear la credencial.", e);
            }
        } catch (Exception e) {
            throw new SQLException("Error al crear las credenciales.");
        }
        return null;
    }

    @Override
    public CredencialAcceso leer(Integer id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM CredencialAcceso WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CredencialAcceso(rs.getInt("id"), rs.getBoolean("eliminado"), rs.getString("hashPassword"), rs.getString("salt"), rs.getTimestamp("ultimoCambio").toLocalDateTime(), rs.getBoolean("requiereReset"));
            }
        }
        return null;
    }

    @Override
    public List<CredencialAcceso> leerTodos(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(CredencialAcceso ca, Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(Integer id, Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
