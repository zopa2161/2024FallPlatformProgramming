package ex1;

public class Main {
    public static void main(String[] args) {
        System.out.println("Testing divideNumbers method:");
        ExceptionHandlingPractice.divideNumbers(10, 2);
        ExceptionHandlingPractice.divideNumbers(10, 0);

        System.out.println("\nTesting accessArray method:");
        int[] sampleArray = {1, 2, 3, 4, 5};
        ExceptionHandlingPractice.accessArray(sampleArray, 2);
        ExceptionHandlingPractice.accessArray(sampleArray, 10);

        System.out.println("\nTesting parseInteger method:");
        ExceptionHandlingPractice.parseInteger("123");
        ExceptionHandlingPractice.parseInteger("abc123");

        System.out.println("\nTesting getStringLength method:");
        ExceptionHandlingPractice.getStringLength("Hello");
        ExceptionHandlingPractice.getStringLength(null);

        System.out.println("\nTesting readFileContent method:");
        ExceptionHandlingPractice.readFileContent("test.txt");
        ExceptionHandlingPractice.readFileContent("nonexistent.txt");

        System.out.println("\nTesting stringToBytes method:");
        ExceptionHandlingPractice.stringToBytes("Hello", "UTF-8");
        ExceptionHandlingPractice.stringToBytes("Hello", "unsupported-encoding");
    }
}