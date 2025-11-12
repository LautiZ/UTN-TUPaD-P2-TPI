package daos;

import daos.Generic.GenericDao;
import entities.CredencialAcceso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class CredencialAccesoDao implements GenericDao<CredencialAcceso>{

    @Override
    public void crear(CredencialAcceso ca, Connection conn) throws SQLException {
        String instruccion = "INSERT INTO CredencialAcceso (hashPassword, salt, requiereReset) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(instruccion)){
            ps.setString(1, ca.getHashPassword());
            ps.setString(2, ca.getSalt());
            ps.setBoolean(3, ca.isRequiereReset());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("Error al crear las credenciales.");
        }
    }

    @Override
    public CredencialAcceso leer(long id, Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public void eliminar(long id, Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
