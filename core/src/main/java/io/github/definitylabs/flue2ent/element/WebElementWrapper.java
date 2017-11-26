package io.github.definitylabs.flue2ent.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

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

    public WebElementWrapper findElement(By by) {
        return new WebElementWrapper(webElement.findElement(by));
    }

    public List<WebElementWrapper> findElements(By by) {
        return webElement.findElements(by).stream()
                .map(WebElementWrapper::new)
                .collect(Collectors.toList());
    }

}
