package io.github.definitylabs.flue2ent.element.list;

import io.github.definitylabs.flue2ent.element.WebElementDecorator;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.By;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractListElement<T extends ListItemElement> extends WebElementDecorator {

    protected final By byItem;

    protected AbstractListElement(WebElementWrapper webElement, By byItem) {
        super(webElement);
        this.byItem = byItem;
    }

    public final List<T> items() {
        return webElement().findElements(byItem).stream()
                .map(this::createListItem)
                .collect(Collectors.toList());
    }

    public final boolean contains(Predicate<T> filter) {
        return items().stream().anyMatch(filter);
    }

    public final T find(Predicate<T> filter) {
        return items().stream()
                .filter(filter)
                .findFirst().orElse(null);
    }

    public final List<T> findAll(Predicate<T> filter) {
        return items().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public final Supplier<Boolean> hasItem(Predicate<T> filter) {
        return () -> contains(filter);
    }

    protected abstract T createListItem(WebElementWrapper webElementWrapper);

}
