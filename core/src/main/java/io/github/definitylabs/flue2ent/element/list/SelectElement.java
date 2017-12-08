package io.github.definitylabs.flue2ent.element.list;

import io.github.definitylabs.flue2ent.element.SeleniumElementCreator;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SelectElement extends AbstractListElement<SelectOptionElement> {

    private final Select select;

    public SelectElement(WebElement webElement) {
        super(webElement, By.tagName("option"));
        this.select = SeleniumElementCreator.createSelect(webElement);
    }

    public boolean isMultiple() {
        return select.isMultiple();
    }

    public List<SelectOptionElement> selectedItems() {
        return findAll(SelectOptionElement::isSelected);
    }

    public SelectOptionElement selectedItem() {
        return find(SelectOptionElement::isSelected);
    }

    public SelectElement selectByVisibleText(String text) {
        select.selectByVisibleText(text);
        return this;
    }

    public SelectElement selectByIndex(int index) {
        select.selectByIndex(index);
        return this;
    }

    public SelectElement selectByValue(String value) {
        select.selectByValue(value);
        return this;
    }

    public SelectElement deselectAll() {
        select.deselectAll();
        return this;
    }

    public SelectElement deselectByValue(String value) {
        select.deselectByValue(value);
        return this;
    }

    public SelectElement deselectByIndex(int index) {
        select.deselectByIndex(index);
        return this;
    }

    public SelectElement deselectByVisibleText(String text) {
        select.deselectByVisibleText(text);
        return this;
    }

    @Override
    protected SelectOptionElement createListItem(WebElementWrapper webElementWrapper) {
        return new SelectOptionElement(this, webElementWrapper);
    }

}