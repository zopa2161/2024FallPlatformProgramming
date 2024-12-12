package framework.repository;

import java.util.List;

public class EntityTableInitializer {

    private final DBConnection dbConnection;

    public EntityTableInitializer(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void initializeTables(List<Class<?>> entityClasses) {
        for (Class<?> entityClass : entityClasses) {
            try {
                EntityMetadata<?> metadata = new EntityMetadata<>(entityClass);//클래스에 대한 메타데이터 만들고
                String createTableSQL = metadata.getCreateTableSQL();//그 메타데이터 내부에서 sql문을 만들어 주면
                dbConnection.createTableIfNotExists(metadata.getTableName(), createTableSQL);//왜 이게 인터페이스임??
                //가 아니라 상속받은 무언가를 받기위해~ 다양한 db확장성~
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize table for entity: " + entityClass.getName(), e);
            }
        }
    }
}