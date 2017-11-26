package io.github.definitylabs.flue2ent.element.table;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TableElementTest {

    @Test
    public void table_returnsTableInstance() {
        TableElement table = TableElement.table();

        assertThat(table.by).isEqualTo(By.tagName(AbstractTableElement.TABLE_TAG));
    }

    @Test
    public void tableBy_returnsTableInstance() {
        By byTable = By.cssSelector("table");
        TableElement table = TableElement.table(byTable);

        assertThat(table.by).isSameAs(byTable);
    }

    @Test
    public void createRow_returnsTableRowElement() {
        WebElement mockedWebElement = mock(WebElement.class);

        TableElement table = TableElement.table();
        TableRowElement<TableColumnElement> row = table.createRow(mockedWebElement);

        assertThat(row).isNotNull();
        assertThat(row.webElement()).isSameAs(mockedWebElement);
        assertThat(row.table).isSameAs(table);
    }

    @Test
    public void createColumn_returnsTableColumnElement() {
        WebElement mockedWebElement = mock(WebElement.class);

        TableElement table = TableElement.table();
        TableColumnElement column = table.createColumn(mockedWebElement);

        assertThat(column).isNotNull();
        assertThat(column.webElement()).isSameAs(mockedWebElement);
        assertThat(column.table).isSameAs(table);
    }

}