package org.definitylabs.flue2ent.element.table.filter;

import org.definitylabs.flue2ent.element.table.TableColumnElement;
import org.definitylabs.flue2ent.element.table.TableRowElement;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TableFiltersTest {

    @Test
    public void atColumn_returnsFindRowByColumn() {
        int index = 1;
        FindRowByColumn<TableRowElement<TableColumnElement>, TableColumnElement> filter = TableFilters.atColumn(index);

        assertThat(filter).isNotNull();
        assertThat(filter.index).isEqualTo(index);
    }

}