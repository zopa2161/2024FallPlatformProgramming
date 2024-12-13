package application;

import application.controller.CafeOrderController;
import application.controller.StudentController;
import framework.dispatch.RequestDispatcher;


public class Main {
    public static void main(String[] args) {
        try {
            RequestDispatcher processor = new RequestDispatcher();
            processor.registerHandlers(StudentController.class);
            processor.registerHandlers(CafeOrderController.class);

            String studentJson = "{ \"id\": \"101\", \"name\": \"Kim\", \"age\": 20 }";
            Object result1 = processor.processRequest("POST", "/students/add", studentJson);
            System.out.println(result1);
            studentJson = "{ \"id\": \"102\", \"name\": \"Park\", \"age\": 21 }";
            result1 = processor.processRequest("POST", "/students/add", studentJson);
            System.out.println(result1);

            String orderJson = "{ \"orderId\": \"1\", \"itemName\": \"Cappuccino\", \"price\": 4.5 }";
            Object result2 = processor.processRequest("POST", "/orders/new", orderJson);
            System.out.println(result2);
            orderJson = "{ \"orderId\": \"2\", \"itemName\": \"Latte\", \"price\": 4.2 }";
            result2 = processor.processRequest("POST", "/orders/new", orderJson);
            System.out.println(result2);

            Object result3 = processor.processRequest("GET", "/orders/2/status", null);
            System.out.println(result3);

            Object result4 = processor.processRequest("GET", "/students/101/details", null);
            System.out.println(result4);

            Object result5 = processor.processRequest("GET", "/orders/history", null);
            System.out.println(result5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
