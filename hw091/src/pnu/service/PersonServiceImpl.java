package pnu.service;



import pnu.annotation.MyExceptionHandler;
import pnu.exception.PersonNotFoundException;

public class PersonServiceImpl implements PersonService {

    @Override
    public String getPersonById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0.");
        }
        if (id > 100) { // Simulating a "not found" condition
            throw new PersonNotFoundException("No person found with ID: " + id);
        }
        return "Kim CS";
    }

    @MyExceptionHandler({PersonNotFoundException.class})
    public void handlePersonNotFoundException(PersonNotFoundException ex) {
        System.out.println("[EXCEPTION] Person not found: " + ex.getMessage());
    }

    @MyExceptionHandler({IllegalArgumentException.class})
    public void handleIllegalArgumentException(IllegalArgumentException ex) {
        System.out.println("[EXCEPTION] Illegal argument: " + ex.getMessage());
    }
}