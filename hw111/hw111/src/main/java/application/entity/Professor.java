package application.entity;

import framework.annotations.Entity;
import framework.annotations.Id;

@Entity(tableName = "professors")
public class Professor {
    @Id
    private Long id;
    private String name;
    private String department;

    public Professor() {}

    public Professor(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Professor{id=" + id + ", name='" + name + "', department='" + department + "'}";
    }
}
