package io.github.definitylabs.flue2ent.page;

import io.github.definitylabs.flue2ent.Website;
import io.github.definitylabs.flue2ent.element.ExtendedBy;
import io.github.definitylabs.flue2ent.element.WebElementDecorator;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.By;

import java.lang.reflect.*;
import java.util.List;
import java.util.stream.Collectors;

public class PageObjectProxy implements InvocationHandler {

    private final Website website;

    private PageObjectProxy(Website website) {
        this.website = website;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> type, Website website) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new PageObjectProxy(website));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
        } else {
            throw new RuntimeException("Method not implemented");
        }
    }

    private Object convertTo(WebElementWrapper element, String attributeName, Class<?> returnType) {
        if (!attributeName.isEmpty()) {
            if (!returnType.equals(String.class)) {
                throw new IllegalArgumentException("return type should be String");
            }
            return element.getAttribute(attributeName);
        }

        if (WebElementDecorator.class.isAssignableFrom(returnType)) {
            return element.as(webElement -> {
                try {
                    return (WebElementDecorator) returnType.getConstructor(WebElementWrapper.class).newInstance(webElement);
                } catch (Throwable e) {
                    throw new RuntimeException("Cannot create instance of " + returnType.getName(), e);
                }
            });
        } else if (returnType.equals(String.class)) {
            return element.text();
        }
        return element;
    }

    private By by(FindElementBy annotation, Parameter[] parameters, Object[] args) {
        if (!annotation.id().isEmpty()) {
            return By.id(replaceParameters(annotation.id(), parameters, args));
        } else if (!annotation.className().isEmpty()) {
            return By.className(replaceParameters(annotation.className(), parameters, args));
        } else if (!annotation.tagName().isEmpty()) {
            return By.tagName(replaceParameters(annotation.tagName(), parameters, args));
        } else if (!annotation.css().isEmpty()) {
            return By.cssSelector(replaceParameters(annotation.css(), parameters, args));
        } else if (!annotation.xpath().isEmpty()) {
            return By.xpath(replaceParameters(annotation.xpath(), parameters, args));
        } else if (!annotation.name().isEmpty()) {
            return By.name(replaceParameters(annotation.name(), parameters, args));
        } else if (!annotation.linkText().isEmpty()) {
            return By.linkText(replaceParameters(annotation.linkText(), parameters, args));
        } else if (!annotation.partialLinkText().isEmpty()) {
            return By.partialLinkText(replaceParameters(annotation.partialLinkText(), parameters, args));
        } else if (!annotation.label().isEmpty()) {
            return ExtendedBy.byLabel(replaceParameters(annotation.label(), parameters, args));
        } else if (!annotation.labelContaining().isEmpty()) {
            return ExtendedBy.byLabelContaining(replaceParameters(annotation.labelContaining(), parameters, args));
        } else if (!annotation.placeholder().isEmpty()) {
            return ExtendedBy.byPlaceholder(replaceParameters(annotation.placeholder(), parameters, args));
        } else if (!annotation.value().isEmpty()) {
            return ExtendedBy.byValue(replaceParameters(annotation.value(), parameters, args));
        } else if (!annotation.button().isEmpty()) {
            return ExtendedBy.byButton(replaceParameters(annotation.button(), parameters, args));
        }
        throw new IllegalArgumentException("element path is not defined");
    }

    private String replaceParameters(String expression, Parameter[] parameters, Object[] args) {
        String newExpression = expression;
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (!parameter.isAnnotationPresent(Param.class)) {
                throw new IllegalArgumentException("All parameters should be annotated with @Param");
            }

            String parameterName = parameter.getAnnotation(Param.class).value();
            newExpression = newExpression.replace("{" + parameterName + "}", String.valueOf(args[i]));
        }
        return newExpression;
    }

}
