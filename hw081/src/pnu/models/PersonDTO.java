package pnu.models;

public class PersonDTO {
    private int id;
    private String name;

    public PersonDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonDTO{id=" + id + ", name='" + name + "'}";
    }
}
