package io.github.definitylabs.flue2ent.element.table;

import io.github.definitylabs.flue2ent.Website;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractTableElementTest {

    @Mock
    private WebDriver driver;

    @Mock
    private Website website;

    @Mock
    private WebElement tableWebElement;

    private By byTable;
    private AbstractTableElement<TableRowElement<TableColumnElement>, TableColumnElement> table;

    @Before
    public void beforeEach() {
        byTable = By.tagName("table");

        this.table = new AbstractTableElement<TableRowElement<TableColumnElement>, TableColumnElement>(byTable) {
            @Override
            protected TableRowElement<TableColumnElement> createRow(WebElement webElement) {
                return new TableRowElement<>(webElement, this);
            }

            @Override
            protected TableColumnElement createColumn(WebElement webElement) {
                return new TableColumnElement(webElement, this);
            }
        };

        when(driver.findElement(byTable)).thenReturn(tableWebElement);

        when(website.getDriver()).thenReturn(driver);
        this.table.setWebsite(website);
    }

    @Test
    public void init_callsDriverFindElement() {
        verify(driver).findElement(byTable);
    }

    @Test
    public void webElement_returnsTableWebElement() {
        WebElement webElement = table.webElement();

        assertThat(webElement).isSameAs(tableWebElement);
    }

    @Test
    public void rows_returnsTableRowsList() {
        WebElement rowOne = mock(WebElement.class);
        WebElement rowTwo = mock(WebElement.class);

        when(tableWebElement.findElements(By.tagName(AbstractTableElement.TABLE_ROW_TAG)))
                .thenReturn(Arrays.asList(rowOne, rowTwo));

        List<TableRowElement<TableColumnElement>> rows = table.rows();

        assertThat(rows).hasSize(2);
        assertThat(rows.get(0).webElement()).isSameAs(rowOne);
        assertThat(rows.get(1).webElement()).isSameAs(rowTwo);
    }

    @Test
    public void row_returnsTableRowByIndex() {
        WebElement rowOne = mock(WebElement.class);
        WebElement rowTwo = mock(WebElement.class);

        when(tableWebElement.findElements(By.tagName(AbstractTableElement.TABLE_ROW_TAG)))
                .thenReturn(Arrays.asList(rowOne, rowTwo));

        TableRowElement<TableColumnElement> tableRowOne = table.row(0);
        TableRowElement<TableColumnElement> tableRowTwo = table.row(1);

        assertThat(tableRowOne.webElement()).isSameAs(rowOne);
        assertThat(tableRowTwo.webElement()).isSameAs(rowTwo);
    }

    @Test
    public void contains_whenFilterMatchesRowElement_returnsTrue() {
        WebElement rowOne = mock(WebElement.class);
        WebElement rowTwo = mock(WebElement.class);

        when(tableWebElement.findElements(By.tagName(AbstractTableElement.TABLE_ROW_TAG)))
                .thenReturn(Arrays.asList(rowOne, rowTwo));

        boolean contains = table.contains(row -> row.webElement() == rowTwo);

        assertThat(contains).isTrue();
    }

    @Test
    public void contains_whenFilterDoesNotMatchRowElement_returnsFalse() {
        WebElement rowOne = mock(WebElement.class);
        WebElement rowTwo = mock(WebElement.class);

        when(tableWebElement.findElements(By.tagName(AbstractTableElement.TABLE_ROW_TAG)))
                .thenReturn(Arrays.asList(rowOne, rowTwo));

        boolean contains = table.contains(row -> false);

        assertThat(contains).isFalse();
    }

    @Test
    public void find_whenFilterMatchesRowElement_returnsTableRowElement() {
        WebElement rowOne = mock(WebElement.class);
        WebElement rowTwo = mock(WebElement.class);

        when(tableWebElement.findElements(By.tagName(AbstractTableElement.TABLE_ROW_TAG)))
                .thenReturn(Arrays.asList(rowOne, rowTwo));

        TableRowElement<TableColumnElement> tableRow = table.find(row -> row.webElement() == rowTwo);

        assertThat(tableRow.webElement()).isSameAs(rowTwo);
    }

    @Test
    public void find_whenFilterDoesNotMatchRowElement_returnsNull() {
        WebElement rowOne = mock(WebElement.class);
        WebElement rowTwo = mock(WebElement.class);

        when(tableWebElement.findElements(By.tagName(AbstractTableElement.TABLE_ROW_TAG)))
                .thenReturn(Arrays.asList(rowOne, rowTwo));

        TableRowElement<TableColumnElement> tableRow = table.find(row -> false);

        assertThat(tableRow).isNull();
    }

    @Test
    public void findAll_returnsMatchedRows() {
        WebElement rowOne = mock(WebElement.class);
        WebElement rowTwo = mock(WebElement.class);

        when(tableWebElement.findElements(By.tagName(AbstractTableElement.TABLE_ROW_TAG)))
                .thenReturn(Arrays.asList(rowOne, rowTwo));

        List<TableRowElement<TableColumnElement>> rows = table.findAll(row -> row.webElement() == rowTwo);

        assertThat(rows).hasSize(1);
        assertThat(rows.get(0).webElement()).isSameAs(rowTwo);
    }

    @Test
    public void has_whenFilterMatchesRowElement_returnsSupplierTrue() {
        WebElement rowOne = mock(WebElement.class);
        WebElement rowTwo = mock(WebElement.class);

        when(tableWebElement.findElements(By.tagName(AbstractTableElement.TABLE_ROW_TAG)))
                .thenReturn(Arrays.asList(rowOne, rowTwo));

        Supplier<Boolean> has = table.has(row -> row.webElement() == rowTwo);

        assertThat(has.get()).isTrue();
    }

    @Test
    public void has_whenFilterDoesNotMatchRowElement_returnsSupplierFalse() {
        WebElement rowOne = mock(WebElement.class);
        WebElement rowTwo = mock(WebElement.class);

        when(tableWebElement.findElements(By.tagName(AbstractTableElement.TABLE_ROW_TAG)))
                .thenReturn(Arrays.asList(rowOne, rowTwo));

        Supplier<Boolean> has = table.has(row -> false);

        assertThat(has.get()).isFalse();
    }

}