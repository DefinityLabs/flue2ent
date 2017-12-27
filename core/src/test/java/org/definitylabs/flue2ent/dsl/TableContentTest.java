package org.definitylabs.flue2ent.dsl;

import org.definitylabs.flue2ent.Website;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TableContentTest {

    @Mock
    private WebElement webElement;

    @Mock
    private Website website;

    @Mock
    private WebDriver webDriver;

    @Before
    public void beforeEach() {
        when(website.getDriver()).thenReturn(webDriver);
    }

    @Test
    public void table_returnsTableInstance() {
        when(webDriver.findElement(By.tagName(TableContent.TABLE_TAG))).thenReturn(webElement);

        TableContent table = TableContent.table();
        table.setWebsite(website);

        assertThat(table.getResponse().webElement().webElement()).isSameAs(webElement);
    }

    @Test
    public void tableBy_returnsTableInstance() {
        By byTable = By.cssSelector("table");

        when(webDriver.findElement(byTable)).thenReturn(webElement);

        TableContent table = TableContent.table(byTable);
        table.setWebsite(website);

        assertThat(table.getResponse().webElement().webElement()).isSameAs(webElement);
    }

}