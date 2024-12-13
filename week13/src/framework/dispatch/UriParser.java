package framework.dispatch;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface UriParser {
    Pattern createRegex(String template);
    List<String> extractVariableNames(String template);
    Map<String, String> extractVariables(String url);
}