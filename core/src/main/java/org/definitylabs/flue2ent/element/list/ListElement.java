package org.definitylabs.flue2ent.element.list;

import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.By;

public class ListElement extends AbstractListElement<ListItemElement> {

    public ListElement(WebElementWrapper webElement) {
        super(webElement, By.tagName("li"));
    }

    @Override
    protected ListItemElement createListItem(WebElementWrapper webElementWrapper) {
        return new ListItemElement(this, webElementWrapper);
    }

}
