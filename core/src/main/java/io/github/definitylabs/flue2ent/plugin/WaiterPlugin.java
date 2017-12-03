package io.github.definitylabs.flue2ent.plugin;

import io.github.definitylabs.flue2ent.Website;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public class WaiterPlugin implements WebsitePlugin {

    protected final Website website;
    protected final FluentWait<WebDriver> wait;

    public WaiterPlugin(Website website) {
        this.website = website;
        this.wait = website.createFluentWait();
    }

    public final WaiterPlugin upTo(int duration, TimeUnit unit) {
        wait.withTimeout(duration, unit);
        return this;
    }

    public final WaiterPlugin pollingEvery(int duration, TimeUnit unit) {
        wait.pollingEvery(duration, unit);
        return this;
    }

    public final WaiterPlugin withMessage(String message) {
        wait.withMessage(message);
        return this;
    }

    @SafeVarargs
    public final WaiterPlugin ignoring(Class<? extends Throwable>... exceptionType) {
        wait.ignoreAll(Arrays.asList(exceptionType));
        return this;
    }

    public final <V> V until(Function<Website, V> isTrue) {
        return wait.until(driver -> isTrue.apply(website));
    }

    public final <V> V until(Supplier<V> isTrue) {
        return wait.until(driver -> isTrue.get());
    }

    public WebsiteDriverWaiter driver() {
        return new WebsiteDriverWaiter();
    }

    public class WebsiteDriverWaiter {

        protected WebsiteDriverWaiter() {

        }

        public final <V> V until(Function<WebDriver, V> isTrue) {
            return wait.until(isTrue);
        }

    }

}
