package io.github.definitylabs.flue2ent.dsl;

import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import io.github.definitylabs.flue2ent.element.table.TableElement;
import org.openqa.selenium.By;

public class TableContent extends PageObjectDsl<TableElement> {

    protected static final String TABLE_TAG = "table";

    private final By by;
    private TableElement table;

    private TableContent(By by) {
        this.by = by;
    }

    public static TableContent table() {
        return new TableContent(By.tagName(TABLE_TAG));
    }

    public static TableContent table(By by) {
        return new TableContent(by);
    }

    @Override
    protected void init() {
        table = new TableElement(new WebElementWrapper(driver().findElement(by)));
    }

    @Override
    public TableElement getResponse() {
        return table;
    }

}
