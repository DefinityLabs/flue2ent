package org.definitylabs.flue2ent.plugin;

import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.JavascriptExecutor;

public class ScrollPlugin implements WebDriverPlugin {

    protected final JavascriptExecutor driver;

    public ScrollPlugin(JavascriptExecutor driver) {
        this.driver = driver;
    }

    public ScrollPlugin top() {
        return to(0, 0);
    }

    public ScrollPlugin up() {
        return by(0, -250);
    }

    public ScrollPlugin down() {
        return by(0, 250);
    }

    public ScrollPlugin bottom() {
        driver.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        return this;
    }

    public ScrollPlugin to(WebElementWrapper element) {
        driver.executeScript("arguments[0].scrollIntoView(true);", element.webElement());
        return this;
    }

    public ScrollPlugin to(int x, int y) {
        driver.executeScript("window.scrollTo(" + x + ", " + y + ");");
        return this;
    }

    public ScrollPlugin by(int x, int y) {
        driver.executeScript("window.scrollBy(" + x + ", " + y + ");");
        return this;
    }

}
