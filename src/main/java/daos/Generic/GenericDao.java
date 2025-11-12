package daos.Generic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {
    void crear(T entidad, Connection conn) throws SQLException;
    T leer(long id, Connection conn) throws SQLException;
    List<T> leerTodos(Connection conn) throws SQLException;
    void actualizar(T entidad, Connection conn) throws SQLException;
    void eliminar(long id, Connection conn) throws SQLException;
}
