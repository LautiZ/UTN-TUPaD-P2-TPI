package daos;

import daos.Generic.GenericDao;
import entities.CredencialAcceso;
import entities.Usuario;
import entities.enums.Roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import services.CredencialAccesoService;


public class UsuarioDao implements GenericDao<Usuario> {
    
    private final CredencialAccesoService cas = new CredencialAccesoService();

    @Override
    public Usuario crear(Usuario usuario, Connection conn) throws SQLException {
        String instruccion = "INSERT INTO Usuarios (username, email, activo, rol, credencial_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(instruccion, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getEmail());
            ps.setBoolean(3, usuario.isActivo());
            ps.setString(4, usuario.getRol().getNombre());
            ps.setInt(5, usuario.getCredencial().getId());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo insertar el usuario, no se afecto ninguna fila.");
            }
            
             try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    Usuario u = leer(idGenerado, conn);
                    return u;
                }
            } catch (SQLException e) {
                System.err.println("Error SQL al crear el usuario: " + e.getMessage());
                throw new SQLException("Error al crear el usuario.", e);
            }
             
        } catch (Exception e) {
            throw new SQLException("Error al crear el usuario." + e.getMessage());
        }
        return null;
    }

    @Override
    public Usuario leer(Integer id, Connection conn) throws SQLException {
        String instruccion = "SELECT * FROM Usuarios WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(instruccion)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getBoolean("eliminado") == false) {
                    Roles rol = Roles.fromNombre(rs.getString("rol"));
                    CredencialAcceso ca = cas.getById(rs.getInt("credencial_id"), conn); // Usa metodo sobrecargado
                    return new Usuario(rs.getInt("id"), rs.getBoolean("eliminado"), rs.getString("username"), rs.getString("email"), rs.getBoolean("activo"), rs.getTimestamp("fecha_registro").toLocalDateTime(), rol, ca);
                }
            }
        }
        return null;
    }

    @Override
    public List<Usuario> leerTodos(Connection conn) throws SQLException {
        String instruccion = "SELECT * FROM Usuarios";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(instruccion)) {
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Usuario u = leer(rs.getInt("id"), conn);
                if (u != null) {
                    usuarios.add(u);
                }
            }
        }

        return usuarios;
    }

    @Override
    public Usuario actualizar(Usuario usuario, Connection conn) throws SQLException {
        String instruccion = "UPDATE Usuarios SET username = ?, email = ?, activo = ?, rol = ? WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(instruccion, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getEmail());
            ps.setBoolean(3, usuario.isActivo());
            ps.setString(4, usuario.getRol().getNombre());
            ps.setInt(5, usuario.getId());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo editar el usuario, no se afecto ninguna fila.");
            }
            
            return leer(usuario.getId(), conn);
        } catch (Exception e) {
            throw new SQLException("Error al editar el usuario.");
        }
    }

    @Override
    public Usuario eliminar(Integer id, Connection conn) throws SQLException {
        String instruccion = "UPDATE Usuarios SET eliminado = 1 WHERE id = ?";
        Usuario u = leer(id, conn);
        cas.eliminar(u.getCredencial().getId(), conn);
        try (PreparedStatement ps = conn.prepareStatement(instruccion, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, id);            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo eliminar el usuario, no se afecto ninguna fila.");
            }
            
            return u;
        } catch (Exception e) {
            throw new SQLException("Error al eliminar el usuario.");
        }
    }
    
}
