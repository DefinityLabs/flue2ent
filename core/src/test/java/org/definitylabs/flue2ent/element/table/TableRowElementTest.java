package org.definitylabs.flue2ent.element.table;

import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TableRowElementTest {

    @Mock
    private WebElement webElement;

    private WebElementWrapper webElementWrapper;

    private AbstractTableElement<TableRowElement<TableColumnElement>, TableColumnElement> table;

    @Before
    public void beforeEach() {
        webElementWrapper = new WebElementWrapper(webElement);

        table = new AbstractTableElement<TableRowElement<TableColumnElement>, TableColumnElement>(webElementWrapper) {
            @Override
            protected TableRowElement<TableColumnElement> createRow(WebElementWrapper webElement) {
                return new TableRowElement<>(webElement, this);
            }

            @Override
            protected TableColumnElement createColumn(WebElementWrapper webElement) {
                return new TableColumnElement(webElement, this);
            }
        };
    }

    @Test
    public void headers_returnsHeadersList() {
        WebElement headerOne = mock(WebElement.class);
        WebElement headerTwo = mock(WebElement.class);

        when(webElement.findElements(By.tagName(AbstractTableElement.TABLE_HEADER_TAG)))
                .thenReturn(Arrays.asList(headerOne, headerTwo));

        TableRowElement<TableColumnElement> tableRow = new TableRowElement<>(webElementWrapper, table);

        List<TableColumnElement> headers = tableRow.headers();

        assertThat(headers).hasSize(2);
        assertThat(headers.get(0).webElement().webElement()).isSameAs(headerOne);
        assertThat(headers.get(1).webElement().webElement()).isSameAs(headerTwo);
    }

    @Test
    public void columns_returnsColumnsList() {
        WebElement columnOne = mock(WebElement.class);
        WebElement columnTwo = mock(WebElement.class);

        when(webElement.findElements(By.tagName(AbstractTableElement.TABLE_COLUMN_TAG)))
                .thenReturn(Arrays.asList(columnOne, columnTwo));

        TableRowElement<TableColumnElement> tableRow = new TableRowElement<>(webElementWrapper, table);

        List<TableColumnElement> columns = tableRow.columns();

        assertThat(columns).hasSize(2);
        assertThat(columns.get(0).webElement().webElement()).isSameAs(columnOne);
        assertThat(columns.get(1).webElement().webElement()).isSameAs(columnTwo);
    }

    @Test
    public void column_returnsColumnByIndex() {
        WebElement columnOne = mock(WebElement.class);
        WebElement columnTwo = mock(WebElement.class);

        when(webElement.findElements(By.tagName(AbstractTableElement.TABLE_COLUMN_TAG)))
                .thenReturn(Arrays.asList(columnOne, columnTwo));

        TableRowElement<TableColumnElement> tableRow = new TableRowElement<>(webElementWrapper, table);

        assertThat(tableRow.column(0).webElement().webElement()).isSameAs(columnOne);
        assertThat(tableRow.column(1).webElement().webElement()).isSameAs(columnTwo);
    }

    @Test
    public void contains_whenFilterMatchesColumnElement_returnsTrue() {
        WebElement columnOne = mock(WebElement.class);
        WebElement columnTwo = mock(WebElement.class);

        when(webElement.findElements(By.tagName(AbstractTableElement.TABLE_COLUMN_TAG)))
                .thenReturn(Arrays.asList(columnOne, columnTwo));

        TableRowElement<TableColumnElement> tableRow = new TableRowElement<>(webElementWrapper, table);
        boolean contains = tableRow.contains(row -> row.webElement().webElement() == columnTwo);

        assertThat(contains).isTrue();
    }

    @Test
    public void contains_whenFilterDoesNotMatchColumnElement_returnsFalse() {
        WebElement columnOne = mock(WebElement.class);
        WebElement columnTwo = mock(WebElement.class);

        when(webElement.findElements(By.tagName(AbstractTableElement.TABLE_COLUMN_TAG)))
                .thenReturn(Arrays.asList(columnOne, columnTwo));

        TableRowElement<TableColumnElement> tableRow = new TableRowElement<>(webElementWrapper, table);
        boolean contains = tableRow.contains(row -> false);

        assertThat(contains).isFalse();
    }

    @Test
    public void find_whenFilterMatchesColumnElement_returnsTableRowElement() {
        WebElement columnOne = mock(WebElement.class);
        WebElement columnTwo = mock(WebElement.class);

        when(webElement.findElements(By.tagName(AbstractTableElement.TABLE_COLUMN_TAG)))
                .thenReturn(Arrays.asList(columnOne, columnTwo));

        TableRowElement<TableColumnElement> tableRow = new TableRowElement<>(webElementWrapper, table);
        TableColumnElement tableColumn = tableRow.find(column -> column.webElement().webElement() == columnTwo);

        assertThat(tableColumn.webElement().webElement()).isSameAs(columnTwo);
    }

    @Test
    public void find_whenFilterDoesNotMatchColumnElement_returnsNull() {
        WebElement columnOne = mock(WebElement.class);
        WebElement columnTwo = mock(WebElement.class);

        when(webElement.findElements(By.tagName(AbstractTableElement.TABLE_COLUMN_TAG)))
                .thenReturn(Arrays.asList(columnOne, columnTwo));

        TableRowElement<TableColumnElement> tableRow = new TableRowElement<>(webElementWrapper, table);
        TableColumnElement tableColumn = tableRow.find(column -> false);

        assertThat(tableColumn).isNull();
    }

    @Test
    public void findAll_returnsMatchedColumns() {
        WebElement columnOne = mock(WebElement.class);
        WebElement columnTwo = mock(WebElement.class);

        when(webElement.findElements(By.tagName(AbstractTableElement.TABLE_COLUMN_TAG)))
                .thenReturn(Arrays.asList(columnOne, columnTwo));

        TableRowElement<TableColumnElement> tableRow = new TableRowElement<>(webElementWrapper, table);
        List<TableColumnElement> tableColumns = tableRow.findAll(column -> column.webElement().webElement() == columnTwo);

        assertThat(tableColumns).hasSize(1);
        assertThat(tableColumns.get(0).webElement().webElement()).isSameAs(columnTwo);
    }

}