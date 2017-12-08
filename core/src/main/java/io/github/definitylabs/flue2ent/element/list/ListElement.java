package io.github.definitylabs.flue2ent.element.list;

import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ListElement extends AbstractListElement<ListItemElement> {

    public ListElement(WebElement webElement) {
        super(webElement, By.tagName("li"));
    }

    @Override
    protected ListItemElement createListItem(WebElementWrapper webElementWrapper) {
        return new ListItemElement(this, webElementWrapper);
    }

}
