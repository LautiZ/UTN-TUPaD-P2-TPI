package services.Generic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface GenericService<T> {
    T insertar(T entidad, Connection conn) throws SQLException;
    T actualizar(Integer id, Map<String, Object> nuevosDatos) throws SQLException;
    T eliminar(Integer id) throws SQLException;
    T getById(Integer id) throws SQLException;
    List<T> getAll() throws SQLException;
}
