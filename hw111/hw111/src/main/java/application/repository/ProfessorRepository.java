package application.repository;

import application.entity.Professor;
import framework.annotations.Repository;
import framework.repository.JpaRepository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {}
