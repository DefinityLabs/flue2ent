package io.github.definitylabs.flue2ent;

import io.github.definitylabs.flue2ent.dsl.WebContentDsl;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Website {

    private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

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

    public <R, T extends WebContentDsl<R>> R at(T content) {
        content.setWebsite(this);
        return content.getResponse();
    }

    public Website refresh() {
        driver.navigate().refresh();
        return this;
    }

    public Screenshot screenshot() {
        return new Screenshot((TakesScreenshot) driver);
    }

    public Scroll scroll() {
        return new Scroll((JavascriptExecutor) driver);
    }

    public JavaScript javaScript() {
        return new JavaScript((JavascriptExecutor) driver);
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

    public final class Screenshot {

        private static final String SCREENSHOT_DIRECTORY_NAME = "screenshot";
        private final TakesScreenshot driver;

        private Screenshot(TakesScreenshot driver) {
            this.driver = driver;
        }

        public void takeAsBytes(Consumer<byte[]> consumer) {
            byte[] screenshotAsBytes = driver.getScreenshotAs(OutputType.BYTES);
            consumer.accept(screenshotAsBytes);
        }

        public void takeAsFile(Consumer<File> consumer) {
            File screenshotAsFile = driver.getScreenshotAs(OutputType.FILE);
            consumer.accept(screenshotAsFile);
        }

        public void take() {
            takeAsFile(file -> {
                File screenshotDirectory = new File(SCREENSHOT_DIRECTORY_NAME);
                String fileName = "screenshot_" + timestampFormat.format(new Date()) + ".png";
                File screenshotFile = new File(screenshotDirectory, fileName);

                try {
                    FileUtils.forceMkdir(screenshotDirectory);
                    FileUtils.copyFile(file, screenshotFile);
                } catch (IOException e) {
                    throw new RuntimeException("Cannot save screenshot file", e);
                }
            });
        }

    }

    public final class Scroll {

        private final JavascriptExecutor driver;

        private Scroll(JavascriptExecutor driver) {
            this.driver = driver;
        }

        public Scroll top() {
            return to(0, 0);
        }

        public Scroll up() {
            return by(0, -250);
        }

        public Scroll down() {
            return by(0, 250);
        }

        public Scroll bottom() {
            driver.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            return this;
        }

        public Scroll to(int x, int y) {
            driver.executeScript("window.scrollTo(" + x + ", " + y + ");");
            return this;
        }

        public Scroll by(int x, int y) {
            driver.executeScript("window.scrollBy(" + x + ", " + y + ");");
            return this;
        }

    }

    public final class JavaScript {

        private final JavascriptExecutor driver;

        private JavaScript(JavascriptExecutor driver) {
            this.driver = driver;
        }

        @SuppressWarnings("unchecked")
        public <T> T execute(String script, Object... args) {
            return (T) driver.executeScript(script, args);
        }

        @SuppressWarnings("unchecked")
        public <T> T executeAsync(String script, Object... args) {
            return (T) driver.executeAsyncScript(script, args);
        }

    }

}
