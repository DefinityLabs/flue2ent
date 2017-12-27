package org.definitylabs.flue2ent.element.table;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class TableElementConfigurationTest {

    @Test
    public void getRowDefinition_returnsRowDefinition() {
        TableElementConfiguration configuration = new TableElementConfiguration();

        By byTableRow = By.tagName("tr");
        configuration.rowDefined(byTableRow);

        By rowDefinition = configuration.getRowDefinition();

        assertThat(rowDefinition).isSameAs(byTableRow);
    }

    @Test
    public void getColumnDefinition_returnsColumnDefinition() {
        TableElementConfiguration configuration = new TableElementConfiguration();

        By byTableColumn = By.tagName("td");
        configuration.columnDefined(byTableColumn);

        By columnDefinition = configuration.getColumnDefinition();

        assertThat(columnDefinition).isSameAs(byTableColumn);
    }

    @Test
    public void getHeaderDefinition_returnsHeaderDefinition() {
        TableElementConfiguration configuration = new TableElementConfiguration();

        By byTableHeader = By.tagName("th");
        configuration.headerDefined(byTableHeader);

        By headerDefinition = configuration.getHeaderDefinition();

        assertThat(headerDefinition).isSameAs(byTableHeader);
    }

}