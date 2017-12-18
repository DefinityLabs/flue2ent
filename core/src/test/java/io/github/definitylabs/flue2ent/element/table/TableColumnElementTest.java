package io.github.definitylabs.flue2ent.element.table;

import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TableColumnElementTest {

    @Test
    public void table_returnsTableElement() {
        WebElementWrapper webElement = mock(WebElementWrapper.class);
        AbstractTableElement<TableRowElement<TableColumnElement>, TableColumnElement> mockedTable = mock(AbstractTableElement.class);

        TableColumnElement tableColumnElement = new TableColumnElement(webElement, mockedTable);

        assertThat(tableColumnElement.table).isSameAs(mockedTable);
    }

    @Test
    public void text_returnsWebElementText() {
        WebElement webElement = mock(WebElement.class);
        String elementText = "element text";
        when(webElement.getText()).thenReturn(elementText);

        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);
        AbstractTableElement<TableRowElement<TableColumnElement>, TableColumnElement> mockedTable = mock(AbstractTableElement.class);

        TableColumnElement tableColumnElement = new TableColumnElement(webElementWrapper, mockedTable);
        String text = tableColumnElement.text();

        assertThat(text).isEqualTo(elementText);
    }

}