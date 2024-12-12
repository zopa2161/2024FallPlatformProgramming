package pnu.services;

import pnu.annotations.MyAutowired;
import pnu.annotations.MyService;
import pnu.models.Person;
import pnu.models.PersonDTO;
import pnu.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@MyService
public class PersonService {
    @MyAutowired
    private PersonRepository personRepository;

    public PersonDTO findPersonById(int id) {
        Person person = personRepository.findById(id);
        if (person != Person.NullPerson) {
            return new PersonDTO(person.getId(), person.getName());
        }
        return new PersonDTO(-1, "Not Found");
    }

    public List<PersonDTO> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        List<PersonDTO> personDTOs = new ArrayList<>();

        for (Person person : persons) {
            personDTOs.add(new PersonDTO(person.getId(), person.getName()));
        }

        return personDTOs;
    }

}