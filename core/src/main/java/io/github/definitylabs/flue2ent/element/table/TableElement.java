package io.github.definitylabs.flue2ent.element.table;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TableElement extends AbstractTableElement<TableRowElement<TableColumnElement>, TableColumnElement> {

    private TableElement(By by) {
        super(by);
    }

    public static TableElement table() {
        return new TableElement(By.tagName(TABLE_TAG));
    }

    public static TableElement table(By by) {
        return new TableElement(by);
    }

    @Override
    protected TableRowElement<TableColumnElement> createRow(WebElement webElement) {
        return new TableRowElement<>(webElement, this);
    }

    @Override
    protected TableColumnElement createColumn(WebElement webElement) {
        return new TableColumnElement(webElement, this);
    }

}
