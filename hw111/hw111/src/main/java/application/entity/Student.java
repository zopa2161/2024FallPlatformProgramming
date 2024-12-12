package application.entity;

import framework.annotations.Entity;
import framework.annotations.Id;

@Entity(tableName = "students")
public class Student {

    @Id
    private Long id;
    private String name;
    private String major;

    public Student() { }

    public Student(String name, String major) {
        this.name = name;
        this.major = major;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
}