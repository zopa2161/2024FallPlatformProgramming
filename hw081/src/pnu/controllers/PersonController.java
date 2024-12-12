package pnu.controllers;

import pnu.annotations.MyAutowired;
import pnu.annotations.MyController;
import pnu.models.PersonDTO;
import pnu.services.PersonService;

import java.util.List;

@MyController
public class PersonController {

    @MyAutowired
    private PersonService personService;

    public PersonDTO getPersonById(int id) {
        return personService.findPersonById(id);
    }

    public List<PersonDTO> getAllPersons() {
        return personService.getAllPersons();
    }
}
