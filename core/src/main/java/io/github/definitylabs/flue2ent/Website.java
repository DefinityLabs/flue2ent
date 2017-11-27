package io.github.definitylabs.flue2ent;

import io.github.definitylabs.flue2ent.dsl.WebContentDsl;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;
import java.util.stream.Collectors;

public class Website {

    private final WebDriver driver;
    private final String url;

    private Website(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
        this.driver.get(url);
    }

    public static WebsiteBuilder with(WebDriver driver) {
        return new WebsiteBuilder(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public WebsiteWaiter justWait() {
        return new WebsiteWaiter(this, new FluentWait<>(driver));
    }

    public WebElementWrapper findElement(By by) {
        return new WebElementWrapper(driver.findElement(by));
    }

    public List<WebElementWrapper> findElements(By by) {
        return driver.findElements(by).stream()
                .map(WebElementWrapper::new)
                .collect(Collectors.toList());
    }

    public <T extends WebContentDsl> T at(T content) {
        content.setWebsite(this);
        return content;
    }

    public static final class WebsiteBuilder {

        private final WebDriver driver;

        private WebsiteBuilder(WebDriver driver) {
            this.driver = driver;
        }

        public Website visit(String url) {
            return new Website(driver, url);
        }

    }

}
