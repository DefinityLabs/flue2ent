package io.github.definitylabs.flue2ent.element.table;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TableColumnElementTest {

    @Test
    public void table_returnsTableElement() {
        WebElement webElement = mock(WebElement.class);
        AbstractTableElement<TableRowElement<TableColumnElement>, TableColumnElement> mockedTable = mock(AbstractTableElement.class);

        TableColumnElement tableColumnElement = new TableColumnElement(webElement, mockedTable);

        assertThat(tableColumnElement.table).isSameAs(mockedTable);
    }

}