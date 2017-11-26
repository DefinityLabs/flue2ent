package io.github.definitylabs.flue2ent.element.table;

import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.WebElement;

public class TableColumnElement extends WebElementWrapper {

    protected final AbstractTableElement<? extends TableRowElement, ? extends TableColumnElement> table;

    protected TableColumnElement(WebElement webElement,
                                 AbstractTableElement<? extends TableRowElement, ? extends TableColumnElement> table) {
        super(webElement);
        this.table = table;
    }

}
