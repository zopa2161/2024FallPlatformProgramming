package framework.repository;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;

public class RepositoryProxy<T, ID> implements InvocationHandler {
    //핸들러 자체는 가로채 주는 역할이고(invoca...를 상속함으로 인해)
    //그 이후는 구현 해주어야함
    //여기선 두가지 핸들러(자동가로채기 아님)을 만들어서 실행시키는 방법임

    private final CrudHandler<T> crudHandler;
    private final DynamicQueryHandler<T> dynamicQueryHandler;//이 친구는 jpa리포지토리에 없는 다른 쿼리문을 위한 핸들러
    private final Class<T> entityClass;
    private final Class<?> repositoryInterface;


    public RepositoryProxy(EntityMetadata<T> metadata, DBConnection dbConnection, Class<?> repositoryInterface) {
        //만들어 질때 해당 데이터 타입의 (여기선 학생, 교수)
        //핸들러들을 만들고 간다.
        this.crudHandler = new CrudHandler<>(metadata, dbConnection);
        this.dynamicQueryHandler = new DynamicQueryHandler<>(metadata, dbConnection);
        this.entityClass = metadata.getEntityClass();
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isCrudMethod(method)) {//
            return crudHandler.handleCrudMethod(method, args);
        } else {
            return dynamicQueryHandler.handleDynamicQuery(method, args);
        }
    }

    private boolean isCrudMethod(Method method) {
        // Check if the method is declared in JpaRepository
        for (Method m : JpaRepository.class.getDeclaredMethods()) {
            if (m.getName().equals(method.getName()) &&
                    Arrays.equals(m.getParameterTypes(), method.getParameterTypes())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <T, ID> T create(Class<T> repositoryInterface) {
        try {
            // Extract entity class from generic parameters
            Type[] genericInterfaces = repositoryInterface.getGenericInterfaces();
            ParameterizedType pt = (ParameterizedType) genericInterfaces[0];
            Class<T> entityClass = (Class<T>) pt.getActualTypeArguments()[0];
            EntityMetadata<T> metadata = new EntityMetadata<>(entityClass);
            RepositoryProxy<T, ID> handler = new RepositoryProxy<>(metadata, new H2DBConnection(), repositoryInterface);
            return (T) Proxy.newProxyInstance(
                    repositoryInterface.getClassLoader(),
                    new Class[]{repositoryInterface},
                    handler);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create proxy for " + repositoryInterface.getName(), e);
        }
    }
}
