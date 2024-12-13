package application.controller;


import application.domain.Student;
import framework.annotations.GetMapping;
import framework.annotations.PathVariable;
import framework.annotations.PostMapping;
import framework.annotations.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class StudentController {
    private Map<String, Student> studentDatabase = new HashMap<>();

    @PostMapping("/students/add")
    public String addStudent(@RequestBody Student student) {
        studentDatabase.put(student.getId(), student);
        return "Student{id='" + student.getId() + "', name='" + student.getName() + "', age=" + student.getAge() + "} added successfully.";
    }

    @GetMapping("/students/{id}/details")
    public Student getStudentDetails(@PathVariable("id") String id) {
        return studentDatabase.get(id);
    }
}
