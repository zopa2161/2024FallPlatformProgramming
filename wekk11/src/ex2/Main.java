package ex2;

public class Main {
    public static void main(String[] args) {
        String csvFilePath = "input.csv";
        String jsonFilePath = "output.json";

        CSVToJsonConverter converter = new CSVToJsonConverterImpl();
        MyFileScanner fileScanner = new MyFileScanner(csvFilePath, jsonFilePath, converter);
        fileScanner.process();
    }
}