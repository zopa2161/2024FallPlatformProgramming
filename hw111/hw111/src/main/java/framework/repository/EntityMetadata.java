package framework.repository;

import framework.annotations.Entity;
import framework.annotations.Id;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class EntityMetadata<T> {
    //메타데이터는 자료형(여기선 학생,교수)
    //의 값을 받아서 데이터베이스와 연동할 수 있게
    //쿼리문 같은거 생성을 도와주는
    private final Class<T> entityClass;
    private final String tableName;
    private final String idFieldName;
    private final Map<String, String> fieldColumnMap = new HashMap<>();
    private final Map<String, Field> fieldMap = new HashMap<>();
    private final StringJoiner columnDefinitions = new StringJoiner(", ");

    public EntityMetadata(Class<T> entityClass) {
        if (entityClass == null) {
            throw new IllegalArgumentException("Entity class cannot be null.");
        }
        this.entityClass = entityClass;
        this.tableName = initializeTableName();//테이블 연결?
        this.idFieldName = initializeFieldColumnMapping();//id필드 네임은 어따 쓰나 봅니다
    }

    private String initializeTableName() {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(
                    "Class " + entityClass.getName() + " must be annotated with @Entity"
            );
        }

        Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
        return entityAnnotation.tableName().isEmpty()
                ? entityClass.getSimpleName().toLowerCase()//여기서 미리 세팅된 테이블 네임 받아오기
                : entityAnnotation.tableName();
    }

    private String initializeFieldColumnMapping() {
        String idFieldName = null;

        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String columnName = fieldName;
            String columnType = mapJavaTypeToSQLType(field.getType());

            if (field.isAnnotationPresent(Id.class)) {//id라는 어노테이션이 붙으면
                if (idFieldName != null) {//id(키 말하는듯)은 중복되면 안되니까.... 에러
                    throw new IllegalArgumentException(
                            "Multiple fields annotated with @Id in class " + entityClass.getName()
                    );
                }
                idFieldName = fieldName;
                columnDefinitions.add(columnName + " " + columnType + " PRIMARY KEY AUTO_INCREMENT");//키 생성쿼리
            } else {
                columnDefinitions.add(columnName + " " + columnType);//일반 타입 생성쿼리, 조이너는 추가 할때마다 delimiter 끼우고 하는 빌더?
            }
            fieldColumnMap.put(fieldName, columnName);//맵에 클래스와 db의 이름 각각 주기,,,, 근데 이러면 사실상 그냥 이름이 같음
            fieldMap.put(fieldName, field); // Cache Field objects //아하 이러면 해당 필드 객체(클래스-필드 정보를 다담음) 저장된다.
        }

        if (idFieldName == null) {
            throw new IllegalArgumentException(
                    "No field annotated with @Id in class " + entityClass.getName()
            );
        }

        return idFieldName;
    }

    private String mapJavaTypeToSQLType(Class<?> type) {
        //클래스의 타입에따라 sql에서 타입을 선언할 스트링 리턴
        if (type.equals(String.class)) {
            return "VARCHAR(255)";
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            return "BIGINT";
        } else if (type.equals(Integer.class) || type.equals(int.class)) {
            return "INT";
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            return "DOUBLE";
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return "BOOLEAN";
        } else {
            throw new UnsupportedOperationException("Unsupported field type: " + type.getName());
        }
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, String> getFieldColumnMap() {
        return new HashMap<>(fieldColumnMap); // Return a copy
    }

    public Field getField(String fieldName) {
        return fieldMap.get(fieldName);
    }

    public String getCreateTableSQL() {
        if (tableName.isEmpty() || columnDefinitions.length() == 0) {
            throw new IllegalStateException(
                    "Entity metadata is incomplete. Cannot generate SQL."
            );
        }
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnDefinitions.toString() + ")";
    }

    public String getIdFieldName() {
        return idFieldName;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
}
