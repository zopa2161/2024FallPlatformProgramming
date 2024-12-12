package framework.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBConnection {
    ResultSet executeQuery(String sql, Object[] params) throws SQLException;
    int executeUpdate(String sql, Object[] params) throws SQLException;
    void createTableIfNotExists(String tableName, String createSQL) throws SQLException;
    ResultSet getGeneratedKeys() throws SQLException;
}