package io.github.definitylabs.flue2ent.element.table;

import io.github.definitylabs.flue2ent.element.WebElementWrapper;

public class TableElement extends AbstractTableElement<TableRowElement<TableColumnElement>, TableColumnElement> {

    public TableElement(WebElementWrapper webElement) {
        super(webElement);
    }

    @Override
    protected TableRowElement<TableColumnElement> createRow(WebElementWrapper webElement) {
        return new TableRowElement<>(webElement, this);
    }

    @Override
    protected TableColumnElement createColumn(WebElementWrapper webElement) {
        return new TableColumnElement(webElement, this);
    }

}
