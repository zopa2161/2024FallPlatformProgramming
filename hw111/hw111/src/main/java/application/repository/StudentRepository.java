package application.repository;

import application.entity.Student;
import framework.annotations.Repository;
import framework.repository.JpaRepository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByMajor(String major);
    List<Student> findByNameContaining(String name);
}
