package pnu.dispacher;

import pnu.annotation.MyExceptionHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ExceptionHandlingProxy implements InvocationHandler {

    private final Object target;

    public ExceptionHandlingProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try{
            return method.invoke(target, args);

        }catch(InvocationTargetException e){
            Throwable cause = e.getCause();

            Method handler = findExceptionHandler(cause);
            if(handler != null){
                return handler.invoke(target, cause);
            }

            throw cause;
        }

    }

    private Method findExceptionHandler(Throwable cause) {
        // Implement your code
        for(Method m : target.getClass().getDeclaredMethods()){
            if(m.isAnnotationPresent(MyExceptionHandler.class)){
                MyExceptionHandler handler = m.getAnnotation(MyExceptionHandler.class);
                for(Class<? extends Throwable> ex : handler.value()){
                    if(ex.equals(cause.getClass())){
                        return m;
                    }

                }
            }
        }
        return null;
    }

    public static Object createProxy(Object target) {
        // Implement your code
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new ExceptionHandlingProxy(target));
        return proxy;
    }
}