package io.github.definitylabs.flue2ent.plugin;

import org.openqa.selenium.WebDriver;

public class AlertPlugin implements WebDriverPlugin {

    protected final WebDriver driver;

    public AlertPlugin(WebDriver driver) {
        this.driver = driver;
    }

    public AlertPlugin ok() {
        driver.switchTo().alert().accept();
        return this;
    }

    public AlertPlugin dismiss() {
        driver.switchTo().alert().dismiss();
        return this;
    }

}
