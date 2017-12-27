package org.definitylabs.flue2ent.element.simple;

import org.definitylabs.flue2ent.element.AbstractWebElementProxy;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.By;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class SimpleWebElementDecoratorProxy extends AbstractWebElementProxy {

    private final WebElementWrapper webElement;

    private SimpleWebElementDecoratorProxy(WebElementWrapper webElement) {
        this.webElement = webElement;
    }

    @SuppressWarnings("unchecked")
    public static <T extends SimpleWebElementDecorator> T newInstance(Class<T> type, WebElementWrapper webElement) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new SimpleWebElementDecoratorProxy(webElement));
    }

    @Override
    protected boolean canHandle(Method method) {
        return method.getName().equals("webElement") && method.getParameters().length == 0;
    }

    @Override
    protected Object handle(Object proxy, Method method, Object[] args) {
        return webElement;
    }

    @Override
    protected WebElementWrapper findElement(By by) {
        return webElement.findElement(by);
    }

    @Override
    protected List<WebElementWrapper> findElements(By by) {
        return webElement.findElements(by);
    }

}
