package framework.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DynamicQueryHandler<T> {
    private final EntityMetadata<T> metadata;
    private final DBConnection dbConnection;
    private final Map<Method, String> sqlCache = new HashMap<>();

    public DynamicQueryHandler(EntityMetadata<T> metadata, DBConnection dbConnection) {
        this.metadata = metadata;
        this.dbConnection = dbConnection;
    }

    public List<T> handleDynamicQuery(Method method, Object[] args) throws Exception {
        // Implement your code

        String sql = sqlCache.computeIfAbsent(method, m -> QueryGenerator.generateSQL(m, metadata));

        ResultSet rs = dbConnection.executeQuery(sql, args);
        List<T> results = new ArrayList<>();
        try{
            while (rs.next()) {
                T entity = mapResultSetToEntity(rs);
                results.add(entity);
            }
        }finally{
            rs.close();
        }
        return results;
    }

    private T mapResultSetToEntity(ResultSet rs) throws Exception {
        T entity = metadata.getEntityClass().getDeclaredConstructor().newInstance();
        for(Map.Entry<String,String> field : metadata.getFieldColumnMap().entrySet()){
            String fieldName = field.getKey();
            String columnName = field.getValue();

            Field field1 = metadata.getField(fieldName);
            field1.setAccessible(true);
            field1.set(entity, convertToFieldType(rs.getObject(columnName), field1.getType()));

        }
        return entity;
    }
    private Object convertToFieldType(Object value, Class<?> fieldType) throws Exception {
        if(fieldType.equals(Long.class) || fieldType.equals(long.class)){
            if(value instanceof Number){
                return ((Number) value).longValue();
            }
        }else if(fieldType.equals(Integer.class) || fieldType.equals(int.class)){
            if(value instanceof Number){
                return ((Number) value).intValue();
            }
        }else if(fieldType.equals(Double.class) || fieldType.equals(double.class)){
            if(value instanceof Number){
                return ((Number) value).doubleValue();
            }
        }else if(fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)){
            if(value instanceof Boolean){
                return ((Boolean) value).booleanValue();
            }
        }
        else if(fieldType.equals(String.class)){
            return value.toString();
        }
        return value;
    }
    // Implement your code
}
