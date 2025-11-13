package services;

import config.DatabaseConnection;
import daos.CredencialAccesoDao;
import entities.CredencialAcceso;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import services.Generic.GenericService;
import utils.PasswordUtils;


public class CredencialAccesoService implements GenericService<CredencialAcceso>{
    
    private final CredencialAccesoDao caDao = new CredencialAccesoDao();

    @Override
    public CredencialAcceso insertar(CredencialAcceso ca, Connection conn) throws SQLException {
        
        // Establecer un salt si no se ingresa
        if (ca.getSalt() == null) {
            ca.setSalt("pepper@1234");
        }
        
        // Hashear la contrasena ingresada
        String hashedPassword = PasswordUtils.hashPassword(ca.getHashPassword() + ca.getSalt());
        ca.setHashPassword(hashedPassword);
        
        CredencialAcceso caCreada;
        try {
            caCreada = caDao.crear(ca, conn);
        } catch (SQLException e) {
            caCreada = null;
            throw new SQLException("Error insertando credencial de usuario: " + e.getMessage(), e);
        }
        return caCreada;
    }

    @Override
    public CredencialAcceso actualizar(CredencialAcceso entidad) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CredencialAcceso eliminar(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CredencialAcceso getById(Integer id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return caDao.leer(id, conn);
        } catch (Exception e) {
            throw new SQLException("Error obteniendo la credencial con id: " + id, e);
        }
    }

    @Override
    public List<CredencialAcceso> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // Metodo sobrecargado para poder utlizar misma conexion que en usuarios
    public CredencialAcceso getById(Integer id, Connection conn) throws SQLException {
        return caDao.leer(id, conn);
    }

    
}
