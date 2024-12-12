package framework.repository;

import java.sql.*;

public class H2DBConnection implements DBConnection {
    private static final String JDBC_URL = "jdbc:h2:mem:testdb";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    private final Connection connection;
    private PreparedStatement lastStatement;

    public H2DBConnection() {
        try {
            Class.forName("org.h2.Driver");
            this.connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    @Override
    public ResultSet executeQuery(String sql, Object[] params) throws SQLException {
        // Implement your code
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(sql);
            System.out.println(sql);
            setParameters(statement, params);
            return statement.executeQuery();
        }catch (SQLException e){
            if(statement != null) statement.close();
            throw e;
        }

    }

    @Override
    public int executeUpdate(String sql, Object[] params) throws SQLException {

        // Implement your code
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println(sql);
            setParameters(statement, params);
            int affectedRows = statement.executeUpdate();
            this.lastStatement = statement;
            return affectedRows;
        }
        catch (SQLException e){
            if(statement != null) statement.close();
            throw e;
        }
    }

    @Override
    public void createTableIfNotExists(String tableName, String createSQL) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet tables = metaData.getTables(null, null, tableName.toUpperCase(), null)) {
            if (!tables.next()) {
                executeUpdate(createSQL, null);
                System.out.println("Table created: " + tableName);
            } else {
                System.out.println("Table already exists: " + tableName);
            }
        }
    }
    
    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        if (lastStatement == null) {
            throw new IllegalStateException("No update operation has been executed.");
        }
        return lastStatement.getGeneratedKeys();//아하 마지막 리설트 셋을 주는 함수구나
    }
    private void setParameters(PreparedStatement statement, Object[] params) throws SQLException {
        if(params == null) return;
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);//
        }
    }
}
