package io.github.definitylabs.flue2ent.element;

public abstract class WebElementDecorator {
    protected final WebElementWrapper element;

    protected WebElementDecorator(WebElementWrapper element) {
        this.element = element;
    }
}
