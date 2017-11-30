package io.github.definitylabs.flue2ent.element;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class WebElementWrapper {

    protected final WebElement webElement;

    public WebElementWrapper(WebElement webElement) {
        this.webElement = webElement;
    }

    public final WebElement webElement() {
        return webElement;
    }

    public final String text() {
        return webElement.getText();
    }

    public final WebElementWrapper click() {
        webElement.click();
        return this;
    }

    public final boolean isEnabled() {
        return webElement.isEnabled();
    }

    public final boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    public final boolean isSelected() {
        return webElement.isSelected();
    }

    public final WebElementWrapper findElement(By by) {
        return new WebElementWrapper(webElement.findElement(by));
    }

    public final List<WebElementWrapper> findElements(By by) {
        return webElement.findElements(by).stream()
                .map(WebElementWrapper::new)
                .collect(Collectors.toList());
    }

    public final <V> Supplier<V> has(Function<WebElementWrapper, V> isTrue) {
        return () -> isTrue.apply(this);
    }

    public final AbstractObjectAssert<?, WebElementWrapper> assertThis() {
        return assertThat(identity());
    }

    public final <T> AbstractObjectAssert<?, T> assertThat(Function<WebElementWrapper, T> value) {
        return Assertions.assertThat(value.apply(this));

    }

}
