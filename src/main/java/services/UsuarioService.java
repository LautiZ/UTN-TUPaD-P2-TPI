package services;

import config.DatabaseConnection;
import daos.UsuarioDao;
import entities.Usuario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import services.Generic.GenericService;


public class UsuarioService implements GenericService<Usuario>{
    
    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    public void insertar(Usuario usuario) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false); // inicia transacción

            usuarioDao.crear(usuario, conn);

            conn.commit(); // confirma
        } catch (Exception e) {
            conn.rollback(); // deshace si hubo error
            throw new SQLException("Error insertando usuario: " + e.getMessage(), e);
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Usuario getById(long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Usuario> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
