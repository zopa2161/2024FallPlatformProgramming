package framework.dispatch;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

public class Endpoint {
    String httpMethod;
    String urlPattern;
    Pattern urlRegex;
    List<String> pathVariableNames;
    UriParser uriParser;
    Method method;
    Object controllerInstance;
}
