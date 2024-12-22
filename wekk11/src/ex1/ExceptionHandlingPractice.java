package ex1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class ExceptionHandlingPractice {

    public static void divideNumbers(int a, int b) {
        int result;
        try{
            result = a / b;
        }
        catch(ArithmeticException e){
            System.out.println("Error: Cannot divide by zero.");
            return;
        }

        System.out.println("Result: " + result);
    }

    public static void accessArray(int[] array, int index) {
        int value;
        try {
            value = array[index];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Index "+ index +" is out of bounds for array length 5.");
            return;
        }
        System.out.println("Array value: " + value);
    }

    public static void parseInteger(String input) {
        int value;
        try{
            value = Integer.parseInt(input);
        }
        catch(NumberFormatException e){
            System.out.println("Error: '"+input + "' is not a valid integer.");
            return;
        }

        System.out.println("Parsed integer: " + value);
    }

    public static void getStringLength(String text) {
        int length;
        try{
            length = text.length();
        }
        catch(NullPointerException e){
            System.out.println("Error: Cannot get length of null string.");
            return;
        }
        System.out.println("String length: " + length);
    }

    public static void readFileContent(String filePath) {

        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found at '" + filePath + "'.");
            return;
        }
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }

    public static void stringToBytes(String text, String encoding) {
        byte[] bytes = null;
        try {
            bytes = text.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: Unsupported encoding specified.");
            return;
        }
        System.out.println("Converted string to bytes: " + java.util.Arrays.toString(bytes));
    }
}