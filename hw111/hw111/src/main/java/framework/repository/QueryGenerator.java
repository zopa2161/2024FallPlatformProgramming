package framework.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class QueryGenerator {

    public static String generateSQL(Method method, EntityMetadata<?> metadata) {
        String methodName = method.getName();
        String tableName = metadata.getTableName();
        Map<String, String> fieldColumnMap = metadata.getFieldColumnMap();

        if (!methodName.startsWith("findBy")) {
            // Implement your code
            throw new UnsupportedOperationException("Method not implemented : "+ methodName);
        }

        String condition = methodName.substring("findBy".length());

        StringBuilder query = new StringBuilder("Select * from " + tableName + " where ");

        if(condition.endsWith("Containing")) {
            String fieldName = condition.substring(0, condition.length() - 10);
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
            String columnName = fieldColumnMap.get(fieldName);
            query.append(columnName).append(" like concat('%' , ? , '%')");
        }else {
            String fieldName = condition.substring(0, 1).toLowerCase() + condition.substring(1);
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
            String columnName = fieldColumnMap.get(fieldName);
            query.append(columnName).append(" = ?");

        }
        return query.toString();

    }
}
