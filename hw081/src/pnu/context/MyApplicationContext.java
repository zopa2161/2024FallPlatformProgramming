package pnu.context;

import pnu.annotations.MyAutowired;
import pnu.annotations.MyRepository;
import pnu.annotations.MyController;
import pnu.annotations.MyService;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;


public class MyApplicationContext {
    private Map<Class<?>, Object> beanRegistry = new HashMap<>();//클래스, 인스턴스 넣어두는 맵?

    public void scanAndRegisterBeans(String basePackage) {
        try {
            String path = basePackage.replace('.', '/');
            URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
            if (resource == null) {
                return;
            }
            File directory = new File(resource.getFile());
            for (File d : directory.listFiles()) {//파일을 읽으면서 어노테이션 된 클래스를 찾는??

                for(File file : d.listFiles()) {


                    //System.out.println(file.toURL().toString());
                    //System.out.println(file.getCanonicalPath());
                    //System.out.println(Class.forName("pnu.models.Person.class"));
                    //System.out.println(file.getName());
                    /*
                    if(file.getClass().isAnnotationPresent(MyRepository.class)|| file.getClass().isAnnotationPresent(MyController.class)||
                            file.getClass().isAnnotationPresent(MyRepository.class)) {// 이런식으로 가지고 있는지 없는지 체크하는 구조인가?
                        //위와 같은 방식으로는 찾지 못하고 있어..
                        //파일 내에 적혀있는 클래스, 어노테이션을 읽으려면 다른 방법이 필요해
                        //클래스로더를 이용하는 방식은 특정 클래스만 찾음
                        //제일 확실한 방법은 클래스를 뽑아내서, 어노테이션을 확인하는 방법

                        System.out.println(file.getName());
                        registerBean(file.getClass());

                    }

                     */
                    String className = basePackage+"."+d.getName()+"."+file.getName().replace(".class", "");
                    if(Class.forName(className).isAnnotationPresent(MyController.class)||Class.forName(className).isAnnotationPresent(MyService.class)||
                            Class.forName(className).isAnnotationPresent(MyRepository.class)) {

                        //System.out.println(file.getName());
                        registerBean(Class.forName(className));
                    };

                }


            }


        } catch (Exception e) {
            throw new RuntimeException("Failed to scan and register beans", e);
        }
    }
    
    public void processAutowiring() {
        // Implement your code
        //맵에 있는 모든 빈들을 상대로 어노테이션을 부여?
        //오토와이어드 어노테이션이 붙은 애들이 몇몇 있는데
        //걔들을 타입 맞게 세팅해 줘야하는듯;
        try{
            for(Class<?> c : beanRegistry.keySet()) {
                for(Field field : c.getDeclaredFields()) {
                    if(field.isAnnotationPresent(MyAutowired.class)) {
                        field.setAccessible(true);
                        //System.out.println(field.getName());
                        //System.out.println(field.getType());

                        field.set(beanRegistry.get(c),beanRegistry.get(field.getType()));
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }


    }
    
    public void registerBean(Class<?> beanClass) {

        // Implement your code

        try{
            beanRegistry.put(beanClass,beanClass.getDeclaredConstructor().newInstance());//어떻게?
            //System.out.println(beanRegistry.get(beanClass));

        }catch (Exception e){

        }
    }
    
    public <T> T getBean(Class<T> type) {

        // Implement your code
        return (T)beanRegistry.get(type);//
    }
}
