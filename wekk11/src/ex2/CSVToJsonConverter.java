package ex2;

import java.util.List;

public interface CSVToJsonConverter {
    String convertToJson(List<String> csvLines, List<String> headers);
}