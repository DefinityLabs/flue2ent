package io.github.definitylabs.flue2ent.element.table;

import io.github.definitylabs.flue2ent.element.WebElementDecorator;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.By;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TableRowElement<C extends TableColumnElement> extends WebElementDecorator {

    protected final AbstractTableElement<? extends TableRowElement, C> table;

    protected TableRowElement(WebElementWrapper webElement, AbstractTableElement<? extends TableRowElement, C> table) {
        super(webElement);
        this.table = table;
    }

    public final List<C> headers() {
        By byTableHeader = table.tableElementConfiguration.getHeaderDefinition();
        return webElement.findElements(byTableHeader).stream()
                .map(table::createColumn)
                .collect(Collectors.toList());
    }

    public final List<C> columns() {
        By byTableColumn = table.tableElementConfiguration.getColumnDefinition();
        return webElement.findElements(byTableColumn).stream()
                .map(table::createColumn)
                .collect(Collectors.toList());
    }

    public final C column(int index) {
        return columns().get(index);
    }

    public final boolean contains(Predicate<C> filter) {
        return columns().stream().anyMatch(filter);
    }

    public final C find(Predicate<C> filter) {
        return columns().stream()
                .filter(filter)
                .findFirst().orElse(null);
    }

    public final List<C> findAll(Predicate<C> filter) {
        return columns().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

}
