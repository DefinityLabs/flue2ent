package org.definitylabs.flue2ent.plugin;

import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ScrollPluginTest {

    @Test
    public void top_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        ScrollPlugin scrollPlugin = new ScrollPlugin(mockedDriver);

        ScrollPlugin result = scrollPlugin.top();

        verify(mockedDriver).executeScript("window.scrollTo(0, 0);");

        assertThat(result).isSameAs(scrollPlugin);
    }

    @Test
    public void up_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        ScrollPlugin scrollPlugin = new ScrollPlugin(mockedDriver);

        ScrollPlugin result = scrollPlugin.up();

        verify(mockedDriver).executeScript("window.scrollBy(0, -250);");

        assertThat(result).isSameAs(scrollPlugin);
    }

    @Test
    public void down_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        ScrollPlugin scrollPlugin = new ScrollPlugin(mockedDriver);

        ScrollPlugin result = scrollPlugin.down();

        verify(mockedDriver).executeScript("window.scrollBy(0, 250);");

        assertThat(result).isSameAs(scrollPlugin);
    }

    @Test
    public void bottom_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        ScrollPlugin scrollPlugin = new ScrollPlugin(mockedDriver);

        ScrollPlugin result = scrollPlugin.bottom();

        verify(mockedDriver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        assertThat(result).isSameAs(scrollPlugin);
    }

    @Test
    public void by_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        ScrollPlugin scrollPlugin = new ScrollPlugin(mockedDriver);

        ScrollPlugin result = scrollPlugin.by(10, 20);

        verify(mockedDriver).executeScript("window.scrollBy(10, 20);");

        assertThat(result).isSameAs(scrollPlugin);
    }

    @Test
    public void to_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        ScrollPlugin scrollPlugin = new ScrollPlugin(mockedDriver);

        ScrollPlugin result = scrollPlugin.to(100, 200);

        verify(mockedDriver).executeScript("window.scrollTo(100, 200);");

        assertThat(result).isSameAs(scrollPlugin);
    }

    @Test
    public void to_element_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        ScrollPlugin scrollPlugin = new ScrollPlugin(mockedDriver);

        WebElement webElement = mock(WebElement.class);
        WebElementWrapper element = new WebElementWrapper(webElement);

        ScrollPlugin result = scrollPlugin.to(element);

        verify(mockedDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);

        assertThat(result).isSameAs(scrollPlugin);
    }

}