package framework.dispatch;

public interface JsonParser {
    <T> T parseJson(String json, Class<T> clazz) throws Exception;
}
