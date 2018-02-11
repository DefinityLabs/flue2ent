package org.definitylabs.flue2ent.element;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

public final class SeleniumElementCreator {

    private SeleniumElementCreator() {

    }

    public static Select createSelect(WebElement webElement) {
        return new Select(webElement);
    }

    public static FluentWait<WebDriver> createFluentWait(WebDriver driver) {
        return new FluentWait<>(driver);
    }

    public static Actions createActions(WebDriver driver) {
        return new Actions(driver);
    }

}
