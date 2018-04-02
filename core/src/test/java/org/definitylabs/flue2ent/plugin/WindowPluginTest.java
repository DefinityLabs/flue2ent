package org.definitylabs.flue2ent.plugin;

import org.definitylabs.flue2ent.Website;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class WindowPluginTest {

    private Website website;
    private WebDriver.Window window;
    private WindowPlugin windowPlugin;

    @Before
    public void beforeEach() {
        website = mock(Website.class);

        WebDriver driver = mock(WebDriver.class);
        when(website.getDriver()).thenReturn(driver);

        WebDriver.Options manage = mock(WebDriver.Options.class);
        when(driver.manage()).thenReturn(manage);

        window = mock(WebDriver.Window.class);
        when(manage.window()).thenReturn(window);

        windowPlugin = new WindowPlugin(website);
    }

    @Test
    public void fullscreen_callsFullscreen() {
        windowPlugin.fullscreen();

        verify(window).fullscreen();
    }

    @Test
    public void maximize_callsMaximize() {
        windowPlugin.maximize();

        verify(window).maximize();
    }

    @Test
    public void size_callsSize() {
        windowPlugin.size(100, 200);

        verify(window).setSize(eq(new Dimension(100, 200)));
    }

}