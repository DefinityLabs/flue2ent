package io.github.definitylabs.flue2ent.plugin;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class PagePlugin implements WebDriverPlugin {

    protected final WebDriver driver;

    public PagePlugin(WebDriver driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isLoad() {
        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }

}
