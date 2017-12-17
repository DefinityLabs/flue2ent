package io.github.definitylabs.flue2ent.page;

import io.github.definitylabs.flue2ent.Website;
import io.github.definitylabs.flue2ent.element.AbstractWebElementProxy;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.By;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class PageObjectProxy extends AbstractWebElementProxy {

    private final Website website;

    private PageObjectProxy(Website website) {
        this.website = website;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> type, Website website) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new PageObjectProxy(website));
    }

    @Override
    protected boolean canHandle(Method method) {
        return method.isAnnotationPresent(PageObject.class);
    }

    @Override
    protected Object handle(Object proxy, Method method, Object[] args) {
        return PageObjectProxy.newInstance(method.getReturnType(), website);
    }

    @Override
    protected WebElementWrapper findElement(By by) {
        return website.findElement(by);
    }

    @Override
    protected List<WebElementWrapper> findElements(By by) {
        return website.findElements(by);
    }

}
