package services.Generic;

import java.sql.SQLException;
import java.util.List;

public interface GenericService<T> {
    void insertar(T entidad) throws SQLException;
    void actualizar(T entidad) throws SQLException;
    void eliminar(long id) throws SQLException;
    T getById(long id) throws SQLException;
    List<T> getAll() throws SQLException;
}
