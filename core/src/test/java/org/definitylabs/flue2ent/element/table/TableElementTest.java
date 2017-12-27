package org.definitylabs.flue2ent.element.table;

import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TableElementTest {

    @Test
    public void createRow_returnsTableRowElement() {
        WebElementWrapper mockedWebElement = mock(WebElementWrapper.class);

        TableElement table = new TableElement(mockedWebElement);
        TableRowElement<TableColumnElement> row = table.createRow(mockedWebElement);

        assertThat(row).isNotNull();
        assertThat(row.webElement()).isSameAs(mockedWebElement);
        assertThat(row.table).isSameAs(table);
    }

    @Test
    public void createColumn_returnsTableColumnElement() {
        WebElementWrapper mockedWebElement = mock(WebElementWrapper.class);

        TableElement table = new TableElement(mockedWebElement);
        TableColumnElement column = table.createColumn(mockedWebElement);

        assertThat(column).isNotNull();
        assertThat(column.webElement()).isSameAs(mockedWebElement);
        assertThat(column.table).isSameAs(table);
    }

}