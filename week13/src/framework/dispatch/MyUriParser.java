package framework.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUriParser implements UriParser {

    private Pattern regex;
    private List<String> variableNames;
    @Override
    public Pattern createRegex(String template) {
        String regexStr = template.replaceAll("\\{([^/}]+)\\}","([^/]+)");
        this.regex = Pattern.compile(regexStr);
        return this.regex;
    }

    @Override
    public List<String> extractVariableNames(String template) {
        List<String> vars = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\{([^/}]+)\\}").matcher(template);
        while (matcher.find()) {
            vars.add(matcher.group(1));
        }
        this.variableNames = vars;
        return vars;
    }

    @Override
    public Map<String, String> extractVariables(String url) {
        Matcher matcher = regex.matcher(url);
        Map<String, String> vars = new HashMap<>();
        if(matcher.matches()) {
            for(int i =0 ;i< variableNames.size();i++) {
                vars.put(variableNames.get(i), matcher.group(i+1));
            }
        }
        return vars;
    }


}