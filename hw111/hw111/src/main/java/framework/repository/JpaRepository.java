package framework.repository;

import java.util.List;
import java.util.Optional;

public interface JpaRepository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    boolean delete(T entity);
}
