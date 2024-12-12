import pnu.context.MyApplicationContext;
import pnu.controllers.PersonController;
import pnu.models.PersonDTO;

import java.util.List;

public class Main {
    private static final PersonController controller;
    static {
        MyApplicationContext context = new MyApplicationContext();
        context.scanAndRegisterBeans("pnu");
        context.processAutowiring();
        controller = context.getBean(PersonController.class);
    }

    public static void main(String[] args) {
        PersonDTO personDTO = controller.getPersonById(1);
        System.out.println(personDTO);

        PersonDTO notFoundDTO = controller.getPersonById(99);
        System.out.println(notFoundDTO);

        List<PersonDTO> allPersons = controller.getAllPersons();
        System.out.println("All Persons:");
        allPersons.forEach(System.out::println);
    }
}