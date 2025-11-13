package daos.Generic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {
    T crear(T entidad, Connection conn) throws SQLException;
    T leer(Integer id, Connection conn) throws SQLException;
    List<T> leerTodos(Connection conn) throws SQLException;
    T actualizar(T entidad, Connection conn) throws SQLException;
    T eliminar(Integer id, Connection conn) throws SQLException;
}
