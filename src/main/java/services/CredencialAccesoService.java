package services;

import config.DatabaseConnection;
import daos.CredencialAccesoDao;
import daos.UsuarioDao;
import entities.CredencialAcceso;
import entities.Usuario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import services.Generic.GenericService;
import utils.PasswordUtils;


public class CredencialAccesoService implements GenericService<CredencialAcceso>{
    
    private final CredencialAccesoDao caDao;
    private final UsuarioDao usuarioDao;


    public CredencialAccesoService(CredencialAccesoDao caDao, UsuarioDao usuarioDao) {
        this.caDao = caDao;
        this.usuarioDao = usuarioDao;
    }

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
    public CredencialAcceso actualizar(Integer id, Map<String, Object> nuevosDatos) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        CredencialAcceso ca = getById(id);
        
        for (Map.Entry<String, Object> entry : nuevosDatos.entrySet()) {
            String clave = entry.getKey();
            Object valor = entry.getValue();
            
            switch (clave) {
                case "password" -> {
                    String hashedPassword = PasswordUtils.hashPassword(valor.toString() + ca.getSalt());
                    ca.setHashPassword(hashedPassword);
                }
                case "salt" -> ca.setSalt(valor.toString());
                default -> {
                }
            }
        }
        return caDao.actualizar(ca, conn);
    }

    @Override
    public CredencialAcceso eliminar(Integer id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Usuario user = usuarioDao.buscarUsuarioPorCredencial(id, conn);
            if (user != null) {
                throw new SQLException("Hay un usuario asociado a esta credencial.");
            }
            return caDao.eliminar(id, conn);
        } catch (Exception e) {
            throw new SQLException("Error eliminando la credencial con id: " + id + " " + e.getMessage());
        }
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
        try (Connection conn = DatabaseConnection.getConnection()) {
            return caDao.leerTodos(conn);
        } catch (Exception e) {
            throw new SQLException("Error obteniendo las credenciales." + e.getMessage());
        }
    }
    
    // Metodos sobrecargados para poder utlizar misma conexion que en usuarios
    public CredencialAcceso getById(Integer id, Connection conn) throws SQLException {
        return caDao.leer(id, conn);
    }
    
    public CredencialAcceso eliminar(Integer id, Connection conn) throws SQLException {
        return caDao.eliminar(id, conn);
    }
    
}
