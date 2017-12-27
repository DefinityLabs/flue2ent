package org.definitylabs.flue2ent;

import org.definitylabs.flue2ent.dsl.PageObjectDsl;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.definitylabs.flue2ent.page.PageObjectProxy;
import org.definitylabs.flue2ent.plugin.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
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

    public static <R, T extends PageObjectDsl<R>> Function<Website, R> from(T content) {
        return website -> website.at(content);
    }

    public static <T> Function<Website, T> from(Class<T> type) {
        return website -> website.at(type);
    }

    public static Function<Website, WebElementWrapper> element(By by) {
        return website -> website.findElement(by);
    }

    public static Function<Website, List<WebElementWrapper>> elements(By by) {
        return website -> website.findElements(by);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public WebElementWrapper findElement(By by) {
        return new WebElementWrapper(driver.findElement(by));
    }

    public List<WebElementWrapper> findElements(By by) {
        return driver.findElements(by).stream()
                .map(WebElementWrapper::new)
                .collect(Collectors.toList());
    }

    public <R, T extends PageObjectDsl<R>> R at(T content) {
        content.setWebsite(this);
        return content.getResponse();
    }

    public <T> T at(Class<T> type) {
        return PageObjectProxy.newInstance(type, this);
    }

    public Website refresh() {
        driver.navigate().refresh();
        return this;
    }

    public <T> T get(Function<Website, T> webElement) {
        return justWait().upTo(3, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class).until(webElement);
    }

    public final <V> Supplier<V> has(Function<Website, V> isTrue) {
        return () -> isTrue.apply(this);
    }

    @SuppressWarnings("unchecked")
    public <D, T extends WebDriverPlugin> T driverPlugin(Function<D, T> plugin) {
        return plugin.apply((D) driver);
    }

    public <T extends WebsitePlugin> T plugin(Function<Website, T> plugin) {
        return plugin.apply(this);
    }

    public WaiterPlugin justWait() {
        return plugin(WaiterPlugin::new);
    }

    public PagePlugin page() {
        return driverPlugin(PagePlugin::new);
    }

    public AlertPlugin alert() {
        return driverPlugin(AlertPlugin::new);
    }

    public ScreenshotPlugin screenshot() {
        return driverPlugin(ScreenshotPlugin::new);
    }

    public ScrollPlugin scroll() {
        return driverPlugin(ScrollPlugin::new);
    }

    public JavaScriptPlugin javaScript() {
        return driverPlugin(JavaScriptPlugin::new);
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
