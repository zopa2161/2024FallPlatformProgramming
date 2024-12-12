package pnu.repositories;

import pnu.annotations.MyRepository;
import pnu.models.Person;

import java.util.ArrayList;
import java.util.List;

@MyRepository
public class PersonRepository {
    private List<Person> persons = new ArrayList<>();

    public PersonRepository() {
        // Initialize with some sample data
        persons.add(new Person(1, "Kim", 22));
        persons.add(new Person(2, "Lee", 21));
        persons.add(new Person(3, "Park", 20));
    }

    public Person findById(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return Person.NullPerson;
    }

    public List<Person> findAll() {
        return persons;
    }
}
