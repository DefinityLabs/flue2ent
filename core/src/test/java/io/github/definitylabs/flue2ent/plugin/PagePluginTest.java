package io.github.definitylabs.flue2ent.plugin;

import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PagePluginTest {

    @Test
    public void getUrl_returnsWebDriverCurrentUrl() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);

        String mockedCurrentUrl = "https://google.com/a/";
        when(mockedDriver.getCurrentUrl()).thenReturn(mockedCurrentUrl);

        PagePlugin pagePlugin = new PagePlugin(mockedDriver);
        String currentUrl = pagePlugin.getUrl();

        verify(mockedDriver).getCurrentUrl();
        assertThat(currentUrl).isEqualTo(mockedCurrentUrl);
    }

    @Test
    public void getTitle_returnsWebDriverTitle() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);

        String mockedTitle = "Page Title";
        when(mockedDriver.getTitle()).thenReturn(mockedTitle);

        PagePlugin pagePlugin = new PagePlugin(mockedDriver);
        String currentUrl = pagePlugin.getTitle();

        verify(mockedDriver).getTitle();
        assertThat(currentUrl).isEqualTo(mockedTitle);
    }

    @Test
    public void isLoad_whenPageIsLoad_returnsTrue() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.executeScript(anyString())).thenReturn("complete");

        PagePlugin pagePlugin = new PagePlugin(mockedDriver);

        boolean load = pagePlugin.isLoad();

        verify(mockedDriver).executeScript("return document.readyState");
        assertThat(load).isTrue();
    }

    @Test
    public void isLoad_whenPageIsNotLoad_returnsFalse() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.executeScript(anyString())).thenReturn("");

        PagePlugin pagePlugin = new PagePlugin(mockedDriver);

        boolean load = pagePlugin.isLoad();

        verify(mockedDriver).executeScript("return document.readyState");
        assertThat(load).isFalse();
    }

}