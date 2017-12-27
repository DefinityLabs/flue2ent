package org.definitylabs.flue2ent.element.table;

import org.openqa.selenium.By;

public class TableElementConfiguration {

    private By rowDefinition;
    private By columnDefinition;
    private By headerDefinition;

    protected TableElementConfiguration() {

    }

    public By getRowDefinition() {
        return rowDefinition;
    }

    public By getColumnDefinition() {
        return columnDefinition;
    }

    public By getHeaderDefinition() {
        return headerDefinition;
    }

    public TableElementConfiguration rowDefined(By by) {
        rowDefinition = by;
        return this;
    }

    public TableElementConfiguration columnDefined(By by) {
        columnDefinition = by;
        return this;
    }

    public TableElementConfiguration headerDefined(By by) {
        headerDefinition = by;
        return this;
    }

}
