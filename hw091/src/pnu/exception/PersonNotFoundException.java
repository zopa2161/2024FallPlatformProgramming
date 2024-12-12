package pnu.exception;

public class PersonNotFoundException extends RuntimeException {


    public PersonNotFoundException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
