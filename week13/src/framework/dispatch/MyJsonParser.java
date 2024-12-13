package framework.dispatch;

import javax.print.attribute.standard.NumberOfInterveningJobs;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MyJsonParser implements JsonParser {//맵을 json으로 바꾸는?/

    //Implement your code

    public <T> T parseJson(String json, Class<T> clazz) throws Exception{//json을 받아서 맵으로 만들고
        HashMap<String,Object> jsonMap = (HashMap<String, Object>) parse(json);
        return convertMapToObject(jsonMap,clazz);//이걸 다시 해당 클래스 인스턴스 형태로 리턴
        
    }

    private <T> T convertMapToObject(Map<String,Object> map, Class<T> clazz) throws Exception{
        T obj = clazz.getDeclaredConstructor().newInstance();
        for(Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = map.get(fieldName);

            field.set(obj, value);
        }
        return obj;
    }

    public Object parse(String json){
        HashMap<String,Object> jsonMap = new HashMap<>();
        json = json.substring(1, json.length()-1);
        Scanner sc = new Scanner(json);



        for(String key: sc.nextLine().split(",")){
            String[] tokens = key.split(":",2);

            String k = tokens[0].trim();
            k = k.substring(1, k.length()-1);
            String v= tokens[1].trim();
            if(v.charAt(0) == '\"'){
                jsonMap.put(k,v);
            }
            else{
                try{
                    jsonMap.put(k,Integer.parseInt(v));
                }
                catch(Exception e){
                    jsonMap.put(k,Float.parseFloat(v));
                }
            }
        }
        return jsonMap;
    }






}