package io.github.definitylabs.flue2ent.element.list;

import io.github.definitylabs.flue2ent.element.WebElementWrapper;

public class SelectOptionElement extends ListItemElement {

    public SelectOptionElement(SelectElement selectElement, WebElementWrapper element) {
        super(selectElement, element);
    }

    public SelectOptionElement select() {
        listElement().selectByValue(webElement.getAttribute("value"));
        return this;
    }

    public boolean isSelected() {
        return webElement.isSelected();
    }

    protected SelectElement listElement() {
        return (SelectElement) listElement;
    }

}
