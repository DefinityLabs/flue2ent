package io.github.definitylabs.flue2ent.element.table.filter;

import io.github.definitylabs.flue2ent.element.table.TableColumnElement;
import io.github.definitylabs.flue2ent.element.table.TableRowElement;

import java.util.function.Function;
import java.util.function.Predicate;

public class FindRowByColumn<R extends TableRowElement<C>, C extends TableColumnElement> {

    protected final int index;

    protected FindRowByColumn(int index) {
        this.index = index;
    }

    public Predicate<R> byText(String text) {
        return row -> row.column(index).text().equals(text);
    }

    public Predicate<R> byTextContaining(String text) {
        return row -> row.column(index).text().contains(text);
    }

    public Predicate<R> byTextIgnoringCase(String text) {
        return row -> row.column(index).text().equalsIgnoreCase(text);
    }

    public Predicate<R> matching(Function<C, Boolean> matcher) {
        return row -> matcher.apply(row.column(index));
    }

}
