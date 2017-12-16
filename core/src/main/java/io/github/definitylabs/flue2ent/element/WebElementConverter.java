package io.github.definitylabs.flue2ent.element;

public class WebElementConverter {

    WebElementConverter() {

    }

    public static Object convertTo(WebElementWrapper element, String attributeName, Class<?> returnType) {
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

}
