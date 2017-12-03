package io.github.definitylabs.flue2ent.plugin;

import org.openqa.selenium.JavascriptExecutor;

public class JavaScriptPlugin implements WebDriverPlugin {

    protected final JavascriptExecutor driver;

    public JavaScriptPlugin(JavascriptExecutor driver) {
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
