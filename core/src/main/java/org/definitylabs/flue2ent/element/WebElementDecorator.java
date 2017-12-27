package org.definitylabs.flue2ent.element;

public abstract class WebElementDecorator {

    protected final WebElementWrapper webElement;

    protected WebElementDecorator(WebElementWrapper element) {
        this.webElement = element;
    }

    public WebElementWrapper webElement() {
        return webElement;
    }

}
