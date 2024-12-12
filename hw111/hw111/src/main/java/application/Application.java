package application;

import application.entity.Professor;
import application.entity.Student;
import application.repository.ProfessorRepository;
import application.repository.StudentRepository;
import framework.repository.DBConnection;
import framework.repository.EntityTableInitializer;
import framework.repository.H2DBConnection;
import framework.repository.RepositoryProxy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Application {
    static {
        DBConnection dbConnection = new H2DBConnection();
        EntityTableInitializer tableInitializer = new EntityTableInitializer(dbConnection);//
        tableInitializer.initializeTables(Arrays.asList(Student.class, Professor.class));
    }
    public static void main(String[] args) {
        testProfessorRepository();
        testStudentRepository();
    }

    private static void testProfessorRepository() {
        System.out.println("Testing professor repository\n");
        ProfessorRepository professorRepository = RepositoryProxy.create(ProfessorRepository.class);

        Professor professor1 = new Professor( "Pro Kim", "Computer Science");
        Professor professor2 = new Professor( "Pro Lee", "Mathematics");

        professorRepository.save(professor1);
        professorRepository.save(professor2);

        List<Professor> allProfessors = professorRepository.findAll();
        System.out.println("Professors:");
        allProfessors.forEach(p-> System.out.println(p.getName()));

        professorRepository.delete(professor1);
        allProfessors = professorRepository.findAll();
        System.out.println("After deleting student with ID 1:");
        allProfessors.forEach(p-> System.out.println(p.getName()));

    }

    private static void testStudentRepository() {
        System.out.println("Testing student repository\n");
        StudentRepository studentRepository = RepositoryProxy.create(StudentRepository.class);

        Student students[] = {new Student("Apple Kim", "Computer Science"),
                new Student("Cherry Park", "Computer Science"),
                new Student("Durian Jo", "Mathematics"),
                new Student("Figs Lee", "Mathematics"),
                new Student("Grapes Kang", "Computer Science"),
        };

        for (Student student: students)
            studentRepository.save(student);

        Optional<Student> foundStudent = studentRepository.findById(2L);
        foundStudent.ifPresentOrElse(
                student -> System.out.println("Student found: " + student.getName()),
                () -> System.out.println("Student not found!")
        );

        List<Student> allStudents = studentRepository.findAll();
        System.out.println("Students:");
        allStudents.forEach(s -> System.out.println(s.getName()));

        List<Student> csStudents = studentRepository.findByMajor("Computer Science");
        System.out.println("Students in Computer Science:");
        csStudents.forEach(s -> System.out.println(s.getName()));

        List<Student> studentsWithName = studentRepository.findByNameContaining("Durian");
        System.out.println("Students with name containing 'Durian':");
        studentsWithName.forEach(s -> System.out.println(s.getName()));
    }
}