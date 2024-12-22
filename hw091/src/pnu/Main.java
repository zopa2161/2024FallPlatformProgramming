package pnu;


import pnu.dispacher.ExceptionHandlingProxy;
import pnu.service.PersonService;
import pnu.service.PersonServiceImpl;

public class Main {
    public static void main(String[] args) {
        PersonService service = (PersonService) ExceptionHandlingProxy.createProxy(new PersonServiceImpl());//프록시 인스턴스로 생성
        System.out.println(service.getPersonById(1));  // Valid ID

        try {
            service.getPersonById(101); // Simulated "not found"
        } catch (Exception ignored) { }

        try {
            service.getPersonById(-1); // Invalid ID
        } catch (Exception ignored) { }
    }
}