package framework.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CrudHandler<T> {
    private final EntityMetadata<T> metadata;
    private final DBConnection dbConnection;

    public CrudHandler(EntityMetadata<T> metadata, DBConnection dbConnection) {
        this.metadata = metadata;
        this.dbConnection = dbConnection;
    }

    public Object handleCrudMethod(Method method, Object[] args) throws Exception {
        //메소드를 받아와서 각 행동을 정의한다
        String methodName = method.getName();
        switch (methodName) {
            case "save":
                return handleSave((T) args[0]);
            case "findById":
                return handleFindById(args[0]);
            case "findAll":
                return handleFindAll();
            case "delete":
                return handleDelete((T) args[0]);
            default:
                throw new UnsupportedOperationException("Unsupported method: " + methodName);
        }
    }

    public T handleSave(T entity) throws Exception {
        //데이터 베이스에 현재 메타데이터를 저장하는 함수.
        Field idField = metadata.getField(metadata.getIdFieldName());//id 필드의 이름을 가진 필드를 받아온다.
        //id 필드는 실제필드, 그리고 스트링으로 된 필드 이름이 있다.
        idField.setAccessible(true);
        Object id = idField.get(entity);//파라메터로 받은 해당 데이터의 인스턴스가 entity임, 그 인스턴스의 id필드 값을 ...
        //id 가 없다는 건, db에 등록된적 없다는거임

        if(id == null) {
            return insert(entity);
        }else{
            return update(entity);//id
        }
    }
    private T insert(T entity) throws Exception {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(metadata.getTableName()).append(" (");

        List<Object> values = new ArrayList<>();
        List<String> columns = new ArrayList<>();

        for(Map.Entry<String,String> field : metadata.getFieldColumnMap().entrySet()){
            String fieldName = field.getKey();
            String columnName = field.getValue();

            if(!fieldName.equals(metadata.getIdFieldName())){//id 필드아닌 경우만 처리 왜냐 아이디 필드는 자동생성이에요~~
                columns.add(columnName);

                Field field1 = metadata.getField(fieldName);
                field1.setAccessible(true);
                values.add(field1.get(entity));//해당 필드의 실제 값 가져오기
            }
        }
        sql.append(String.join(",", columns));//컬럼 이름들 나열
        sql.append(") VALUES (");
        sql.append(String.join(",",columns.stream().map(c-> "?").toArray(String[]::new)));//임시 sql문 만들기
        //물음표로 채우는 이유는 나중에 물음표를 값들로 채워주는 함수가 있음
        sql.append(")");

        int affected = dbConnection.executeUpdate(sql.toString(), values.toArray());
        if(affected == 0){
            throw new SQLException("Insert failed");
        }

        ResultSet generatedKeys = dbConnection.getGeneratedKeys();//마지막으로 불린 키
        //값을 받아와서
        if(generatedKeys.next()){
            Field idField = metadata.getField(metadata.getIdFieldName());
            idField.setAccessible(true);
            idField.set(entity, generatedKeys.getLong(1));//인스턴스에 값 주기

        }else{
            throw new SQLException("Insert failed");
        }
        return entity;

    }
    private T update(T entity) throws Exception {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(metadata.getTableName()).append(" SET ");

        List<Object> values = new ArrayList<>();
        List<String> columns = new ArrayList<>();

        for(Map.Entry<String,String> field : metadata.getFieldColumnMap().entrySet()){
            String fieldName = field.getKey();
            String columnName = field.getValue();

            if(!fieldName.equals(metadata.getIdFieldName())){//id 필드아닌 경우만 처리 왜냐 아이디 필드는 자동생성이에요~~
                columns.add(columnName + " = ?");//컬럼 = ? 이런식으로 만들어주고

                Field field1 = metadata.getField(fieldName);
                field1.setAccessible(true);
                values.add(field1.get(entity));//해당 필드의 실제 값 가져오기
            }
        }
        sql.append(String.join(",", columns));//컬럼 =? 나열 하고
        sql.append(" WHERE ");
        sql.append(metadata.getFieldColumnMap().get(metadata.getIdFieldName())).append(" = ? ");// 아이디필드 = ? where 문
        //물음표로 채우는 이유는 나중에 물음표를 값들로 채워주는 함수가 있음

        Field idField = metadata.getField(metadata.getIdFieldName());
        idField.setAccessible(true);
        values.add(idField.get(entity));

        int affected = dbConnection.executeUpdate(sql.toString(), values.toArray());
        if(affected == 0){
            throw new SQLException("Update failed");
        }
        return entity;

    }

    public Optional<T> handleFindById(Object id) throws Exception {
        // Implement your code
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(metadata.getTableName()).append(" WHERE ");
        sql.append(metadata.getFieldColumnMap().get(metadata.getIdFieldName())).append(" = ?");

        ResultSet rs = dbConnection.executeQuery(sql.toString(),new Object[]{id});//파라메터로 받은 아이디 값 스트링임
        if(rs.next()){
            T entity = metadata.getEntityClass().getDeclaredConstructor().newInstance();
            for(Map.Entry<String,String> field : metadata.getFieldColumnMap().entrySet()){
                String fieldName = field.getKey();
                String columnName = field.getValue();

                Field field1 = metadata.getField(fieldName);
                field1.setAccessible(true);
                field1.set(entity, convertToFieldType(rs.getObject(columnName), field1.getType()));

            }
            rs.close();
            return Optional.of(entity);
        }else {
            return Optional.empty();
        }

    }






    public List<T> handleFindAll() throws Exception {
        // Implement your code
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(metadata.getTableName());

        ResultSet rs = dbConnection.executeQuery(sql.toString(),null);
        List<T> entities = new ArrayList<>();
        while(rs.next()){
            T entity = metadata.getEntityClass().getDeclaredConstructor().newInstance();
            for(Map.Entry<String,String> field : metadata.getFieldColumnMap().entrySet()){
                String fieldName = field.getKey();
                String columnName = field.getValue();

                Field field1 = metadata.getField(fieldName);
                field1.setAccessible(true);
                field1.set(entity, convertToFieldType(rs.getObject(columnName), field1.getType()));
            }
            entities.add(entity);
        }
        rs.close();
        return entities;


    }

    public boolean handleDelete(T entity) throws Exception {

        // Implement your code
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(metadata.getTableName()).append(" WHERE ");
        sql.append(metadata.getFieldColumnMap().get(metadata.getIdFieldName())).append(" = ?");

        Field idField = metadata.getField(metadata.getIdFieldName());
        idField.setAccessible(true);
        Object id = idField.get(entity);


        int affected = dbConnection.executeUpdate(sql.toString(),new Object[]{id});
        return affected > 0;
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
