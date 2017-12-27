package org.definitylabs.flue2ent.element.list;

import org.definitylabs.flue2ent.element.WebElementDecorator;
import org.definitylabs.flue2ent.element.WebElementWrapper;

public class ListItemElement extends WebElementDecorator {

    protected final AbstractListElement<? extends ListItemElement> listElement;

    public ListItemElement(AbstractListElement<? extends ListItemElement> listElement, WebElementWrapper element) {
        super(element);
        this.listElement = listElement;
    }

    public String text() {
        return webElement().text();
    }

}
