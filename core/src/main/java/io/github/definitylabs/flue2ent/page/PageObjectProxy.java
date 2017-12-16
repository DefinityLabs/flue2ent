package io.github.definitylabs.flue2ent.page;

import com.google.common.reflect.AbstractInvocationHandler;
import io.github.definitylabs.flue2ent.Website;
import io.github.definitylabs.flue2ent.element.FindElementBy;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.definitylabs.flue2ent.element.ElementLocator.by;
import static io.github.definitylabs.flue2ent.element.WebElementConverter.convertTo;

public class PageObjectProxy extends AbstractInvocationHandler {

    private final Website website;

    private PageObjectProxy(Website website) {
        this.website = website;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> type, Website website) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new PageObjectProxy(website));
    }

    @Override
    public Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(FindElementBy.class)) {
            FindElementBy annotation = method.getAnnotation(FindElementBy.class);

            Class<?> returnType = method.getReturnType();
            if (returnType.equals(List.class)) {
                Type genericReturnType = method.getGenericReturnType();
                Type type = ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                Class<?> listItemReturnType = (Class<?>) type;

                List<WebElementWrapper> elements = website.findElements(by(annotation, method.getParameters(), args));
                return elements.stream()
                        .map(element -> convertTo(element, annotation.andGetAttribute(), listItemReturnType))
                        .collect(Collectors.toList());
            } else {
                WebElementWrapper element = website.findElement(by(annotation, method.getParameters(), args));
                return convertTo(element, annotation.andGetAttribute(), returnType);
            }
        } else if (method.isAnnotationPresent(PageObject.class)) {
            return PageObjectProxy.newInstance(method.getReturnType(), website);
        } else if (method.isDefault()) {
            return invokeDefaultMethod(proxy, method, args);
        } else {
            throw new RuntimeException("Method not implemented");
        }
    }

    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> declaringClass = method.getDeclaringClass();
        Constructor<? extends MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
                .unreflectSpecial(method, declaringClass)
                .bindTo(proxy)
                .invokeWithArguments(args);
    }



}
