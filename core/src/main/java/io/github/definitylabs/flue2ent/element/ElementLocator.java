package io.github.definitylabs.flue2ent.element;

import org.openqa.selenium.By;

import java.lang.reflect.Parameter;

public class ElementLocator {

    ElementLocator() {

    }

    public static By by(FindElementBy annotation, Parameter[] parameters, Object[] args) {
        if (isNotBlank(annotation.id())) {
            return By.id(replaceParameters(annotation.id(), parameters, args));
        } else if (isNotBlank(annotation.className())) {
            return By.className(replaceParameters(annotation.className(), parameters, args));
        } else if (isNotBlank(annotation.tagName())) {
            return By.tagName(replaceParameters(annotation.tagName(), parameters, args));
        } else if (isNotBlank(annotation.css())) {
            return By.cssSelector(replaceParameters(annotation.css(), parameters, args));
        } else if (isNotBlank(annotation.xpath())) {
            return By.xpath(replaceParameters(annotation.xpath(), parameters, args));
        } else if (isNotBlank(annotation.name())) {
            return By.name(replaceParameters(annotation.name(), parameters, args));
        } else if (isNotBlank(annotation.linkText())) {
            return By.linkText(replaceParameters(annotation.linkText(), parameters, args));
        } else if (isNotBlank(annotation.partialLinkText())) {
            return By.partialLinkText(replaceParameters(annotation.partialLinkText(), parameters, args));
        } else if (isNotBlank(annotation.label())) {
            return ExtendedBy.byLabel(replaceParameters(annotation.label(), parameters, args));
        } else if (isNotBlank(annotation.labelContaining())) {
            return ExtendedBy.byLabelContaining(replaceParameters(annotation.labelContaining(), parameters, args));
        } else if (isNotBlank(annotation.placeholder())) {
            return ExtendedBy.byPlaceholder(replaceParameters(annotation.placeholder(), parameters, args));
        } else if (isNotBlank(annotation.value())) {
            return ExtendedBy.byValue(replaceParameters(annotation.value(), parameters, args));
        } else if (isNotBlank(annotation.button())) {
            return ExtendedBy.byButton(replaceParameters(annotation.button(), parameters, args));
        }
        throw new IllegalArgumentException("element path is not defined");
    }

    private static String replaceParameters(String expression, Parameter[] parameters, Object[] args) {
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

    private static boolean isNotBlank(String text) {
        return !text.isEmpty();
    }

}
