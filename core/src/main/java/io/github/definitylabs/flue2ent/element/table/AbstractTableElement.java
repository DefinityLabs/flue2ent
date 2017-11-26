package io.github.definitylabs.flue2ent.element.table;

import io.github.definitylabs.flue2ent.dsl.WebContentDsl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractTableElement<R extends TableRowElement<C>, C extends TableColumnElement>
        extends WebContentDsl {

    protected static final String TABLE_TAG = "table";
    protected static final String TABLE_ROW_TAG = "tr";
    protected static final String TABLE_COLUMN_TAG = "td";
    protected static final String TABLE_HEADER_TAG = "th";

    protected final TableElementConfiguration tableElementConfiguration;
    protected final By by;
    private WebElement table;

    protected AbstractTableElement(By by) {
        this(by, configuration ->
                configuration.rowDefined(By.tagName(TABLE_ROW_TAG))
                        .headerDefined(By.tagName(TABLE_HEADER_TAG))
                        .columnDefined(By.tagName(TABLE_COLUMN_TAG))
        );
    }

    protected AbstractTableElement(By by, Consumer<TableElementConfiguration> configure) {
        this.by = by;

        tableElementConfiguration = new TableElementConfiguration();
        configure.accept(tableElementConfiguration);
    }

    protected WebElement webElement() {
        return table;
    }

    @Override
    protected void init() {
        table = driver().findElement(by);
    }

    protected abstract R createRow(WebElement webElement);

    protected abstract C createColumn(WebElement webElement);

    public List<R> rows() {
        By byTableRow = tableElementConfiguration.getRowDefinition();
        return table.findElements(byTableRow).stream()
                .map(this::createRow)
                .collect(Collectors.toList());
    }

    public final R row(int index) {
        return rows().get(index);
    }

    public final boolean contains(Predicate<R> filter) {
        return rows().stream().anyMatch(filter);
    }

    public final R find(Predicate<R> filter) {
        return rows().stream()
                .filter(filter)
                .findFirst().orElse(null);
    }

    public final List<R> findAll(Predicate<R> filter) {
        return rows().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public final Supplier<Boolean> has(Predicate<R> filter) {
        return () -> contains(filter);
    }

}
