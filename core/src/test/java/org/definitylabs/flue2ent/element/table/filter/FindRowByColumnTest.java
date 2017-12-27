package org.definitylabs.flue2ent.element.table.filter;

import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.definitylabs.flue2ent.element.table.TableColumnElement;
import org.definitylabs.flue2ent.element.table.TableElement;
import org.definitylabs.flue2ent.element.table.TableRowElement;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.function.Predicate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindRowByColumnTest {

    private FindRowByColumn<TableRowElement<TableColumnElement>, TableColumnElement> findRowByColumn = new FindRowByColumn<>(0);

    private WebElement tableWebElement;
    private TableElement table;
    private WebElement rowWebElement;
    private WebElement columnWebElement;
    private TableRowElement<TableColumnElement> row;

    @Before
    public void beforeEach() {
        tableWebElement = mock(WebElement.class);

        columnWebElement = mock(WebElement.class);

        rowWebElement = mock(WebElement.class);
        when(rowWebElement.findElements(By.tagName("td"))).thenReturn(Collections.singletonList(columnWebElement));

        when(tableWebElement.findElements(By.tagName("tr"))).thenReturn(Collections.singletonList(rowWebElement));

        table = new TableElement(new WebElementWrapper(tableWebElement));
        row = table.row(0);
    }

    @Test
    public void byText_returnsPredicate() {
        when(columnWebElement.getText()).thenReturn("text");

        Predicate<TableRowElement<TableColumnElement>> predicate = findRowByColumn.byText("text");
        predicate.test(row);
    }

    @Test
    public void byTextIgnoringCase_returnsPredicate() {
        when(columnWebElement.getText()).thenReturn("TEXT");

        Predicate<TableRowElement<TableColumnElement>> predicate = findRowByColumn.byTextIgnoringCase("text");
        predicate.test(row);
    }

    @Test
    public void byTextContaining_returnsPredicate() {
        when(columnWebElement.getText()).thenReturn("this is a long text");

        Predicate<TableRowElement<TableColumnElement>> predicate = findRowByColumn.byTextContaining("text");
        predicate.test(row);
    }

    @Test
    public void matching_returnsPredicate() {
        when(columnWebElement.getText()).thenReturn("text");

        Predicate<TableRowElement<TableColumnElement>> predicate = findRowByColumn.matching(column -> column.text().equals("text"));
        predicate.test(row);
    }

}