package framework.dispatch;


import framework.annotations.*;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestDispatcher {
    private List<Endpoint> handlers = new ArrayList<>();
    private final JsonParser jsonParser = new MyJsonParser();
    
    
    public void registerHandlers(Class<?> controllerClass) throws Exception {
        //Implement your code
        Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
        for(Method method : controllerClass.getDeclaredMethods()) {
            if(method.isAnnotationPresent(GetMapping.class)) {
                GetMapping annotation = method.getAnnotation(GetMapping.class);
                String url = annotation.value();
                MyUriParser parser = new MyUriParser();
                Pattern regex = parser.createRegex(url);
                List<String> varName = parser.extractVariableNames(url);

                Endpoint endpoint = new Endpoint();
                endpoint.httpMethod = "GET";
                endpoint.urlPattern = url;
                endpoint.urlRegex = regex;
                endpoint.uriParser = parser;
                endpoint.pathVariableNames = varName;
                endpoint.method = method;
                endpoint.controllerInstance = controllerInstance;
                handlers.add(endpoint);

            }else if(method.isAnnotationPresent(PostMapping.class)) {
                PostMapping annotation = method.getAnnotation(PostMapping.class);
                String url = annotation.value();
                MyUriParser parser = new MyUriParser();
                Pattern regex = parser.createRegex(url);
                List<String> varName = parser.extractVariableNames(url);

                Endpoint endpoint = new Endpoint();
                endpoint.httpMethod = "POST";
                endpoint.urlPattern = url;
                endpoint.urlRegex = regex;
                endpoint.uriParser = parser;
                endpoint.pathVariableNames = varName;
                endpoint.method = method;
                endpoint.controllerInstance = controllerInstance;
                handlers.add(endpoint);
            }
        }
    }
    
    public Object processRequest(String httpMethod, String url, String jsonPayload) throws Exception {
        //Implement your code
        for(Endpoint endpoint : handlers) {
            if(!endpoint.httpMethod.equalsIgnoreCase(httpMethod)) {
                continue;
            }
            Matcher matcher = endpoint.urlRegex.matcher(url);
            if(matcher.matches()) {//요청 패턴이랑 가지고 있던 패턴이랑 맞으면
                Object[] args =  new Object[endpoint.method.getParameterCount()];
                Parameter[] parameters = endpoint.method.getParameters();
                Map<String,String> pathVariables = endpoint.uriParser.extractVariables(url);//파싱된 값

                for(int i =0; i<parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    if(parameter.isAnnotationPresent(PathVariable.class)) {
                        PathVariable annotation = parameter.getAnnotation(PathVariable.class);
                        String variableName = annotation.value();
                        String value = pathVariables.get(variableName);
                        args[i] =  convertStringToType(value, parameter.getType());
                    }
                    else if(parameter.isAnnotationPresent(RequestBody.class)) {
                        args[i] = jsonParser.parseJson(jsonPayload, parameter.getType());
                    }
                }
                return endpoint.method.invoke(endpoint.controllerInstance, args);
            }
        }
        throw new Exception("No handler found for "+ httpMethod + " "+ url);

    }
    
    private Map<String, String> parseQueryString(String queryString) {
        Map<String, String> queryParams = new HashMap<>();
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                String key = kv[0];
                String value = kv[1];
                queryParams.put(key, value);
            }
        }
        return queryParams;
    }

    private Object convertStringToType(String value, Class<?> type) {
        if (value == null) {
            return null;
        }
        if (type == String.class) {
            return value;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        }
        return null;
    }    
}
    