package ex2;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CSVToJsonConverterImpl implements CSVToJsonConverter {

    @Override
    public String convertToJson(List<String> csvLines, List<String> headers) {
        //Implement your code
        //이 메소드는 파일로 입력받은 스트링 배열들을 잘 쪼게서 json으로 만들어 보는메소드이다.
        //헤더는 이미 잘 쪼개진 컬럼 이름이다.
        List<Class<?>> types = inferColumnTypes(csvLines.get(0), headers);
        //일케하면 각 타입까지 얻어옴
        StringBuilder json = new StringBuilder();
        json.append("[\n");

        for(String s : csvLines) {
            json.append("{");
            int hi =0;
            Scanner sc = new Scanner(s);
            sc.useDelimiter(",");
            while(sc.hasNext()) {
                json.append(formatValue(headers.get(hi),String.class));
                json.append(": ");
                json.append(formatValue(sc.next(), types.get(hi++)));
                if(hi < types.size()) json.append(",\n");
            }
            json.append("}\n");
        }
        json.append("]");
        return json.toString();
    }

    private List<Class<?>> inferColumnTypes(String firstRow, List<String> headers) {
        List<Class<?>> columnTypes = new ArrayList<>();
        try (Scanner scanner = new Scanner(firstRow)) {
            scanner.useDelimiter(",");

            for (int i = 0; i < headers.size(); i++) {
                if (scanner.hasNext()) {
                    String value = scanner.next().trim();
                    columnTypes.add(guessType(value));
                } else {
                    // no value is present for a column
                    columnTypes.add(String.class);
                }
            }
        }
        return columnTypes;//각 콜럼 타입을 기록해서, 쓰기에 용이하게 하는?
    }

    private Class<?> guessType(String value) {
        if (value.matches("-?\\d+")) {
            return Integer.class;
        } else if (value.matches("-?\\d+(\\.\\d+)?")) {
            return Double.class;
        } else {
            return String.class;
        }
    }

    private String formatValue(String value, Class<?> type) {
        if (type == Integer.class || type == Double.class) {
            return value;
        } else {
            // Adds double quotes or Replaces any existing double quotes
            return "\"" + value.replace("\"", "\\\"") + "\"";
        }
    }
}