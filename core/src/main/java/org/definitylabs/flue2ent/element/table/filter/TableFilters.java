package org.definitylabs.flue2ent.element.table.filter;

import org.definitylabs.flue2ent.element.table.TableColumnElement;
import org.definitylabs.flue2ent.element.table.TableRowElement;

public final class TableFilters {

    private TableFilters() {

    }

    public static <R extends TableRowElement<C>, C extends TableColumnElement> FindRowByColumn<R, C> atColumn(int index) {
        return new FindRowByColumn<>(index);
    }

}
