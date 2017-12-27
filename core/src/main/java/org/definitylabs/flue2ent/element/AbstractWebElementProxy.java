package org.definitylabs.flue2ent.element;

import com.google.common.reflect.AbstractInvocationHandler;
import org.openqa.selenium.By;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import static org.definitylabs.flue2ent.element.ElementLocator.by;
import static org.definitylabs.flue2ent.element.WebElementConverter.convertTo;

public abstract class AbstractWebElementProxy extends AbstractInvocationHandler {

    @Override
    public final Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(FindElementBy.class)) {
            FindElementBy annotation = method.getAnnotation(FindElementBy.class);

            Class<?> returnType = method.getReturnType();
            if (returnType.equals(List.class)) {
                Type genericReturnType = method.getGenericReturnType();
                Type type = ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                Class<?> listItemReturnType = (Class<?>) type;

                List<WebElementWrapper> elements = findElements(by(annotation, method.getParameters(), args));
                return elements.stream()
                        .map(element -> convertTo(element, annotation.andGetAttribute(), listItemReturnType))
                        .collect(Collectors.toList());
            } else {
                WebElementWrapper element = findElement(by(annotation, method.getParameters(), args));
                return convertTo(element, annotation.andGetAttribute(), returnType);
            }
        } else if (method.isDefault()) {
            return invokeDefaultMethod(proxy, method, args);
        } else if (canHandle(method)) {
            return handle(proxy, method, args);
        } else {
            throw new RuntimeException("Method not implemented");
        }
    }

    protected boolean canHandle(Method method) {
        return false;
    }

    protected Object handle(Object proxy, Method method, Object[] args) {
        return null;
    }

    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> declaringClass = method.getDeclaringClass();
        Constructor<? extends MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(Class.class, int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
                .unreflectSpecial(method, declaringClass)
                .bindTo(proxy)
                .invokeWithArguments(args);
    }

    protected abstract WebElementWrapper findElement(By by);

    protected abstract List<WebElementWrapper> findElements(By by);

}
