package org.definitylabs.flue2ent.element.table;

import org.definitylabs.flue2ent.element.WebElementDecorator;
import org.definitylabs.flue2ent.element.WebElementWrapper;

public class TableColumnElement extends WebElementDecorator {

    protected final AbstractTableElement<? extends TableRowElement, ? extends TableColumnElement> table;

    protected TableColumnElement(WebElementWrapper webElement,
                                 AbstractTableElement<? extends TableRowElement, ? extends TableColumnElement> table) {
        super(webElement);
        this.table = table;
    }

    public String text() {
        return webElement().text();
    }

}
