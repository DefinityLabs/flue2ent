package org.definitylabs.flue2ent.plugin;

import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class JavaScriptPluginTest {

    @Test
    public void execute_callsExecuteScript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);

        Object response = new Object();
        when(mockedDriver.executeScript(anyString(), anyString())).thenReturn(response);

        JavaScriptPlugin javaScriptPlugin = new JavaScriptPlugin(mockedDriver);

        String script = "js script";

        Object result = javaScriptPlugin.execute(script, "paramOne");

        verify(mockedDriver).executeScript(script, "paramOne");
        assertThat(result).isSameAs(response);
    }

    @Test
    public void executeAsync_callsExecuteAsyncScript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);

        Object response = new Object();
        when(mockedDriver.executeAsyncScript(anyString(), anyString())).thenReturn(response);

        JavaScriptPlugin javaScriptPlugin = new JavaScriptPlugin(mockedDriver);

        String script = "js script";

        Object result = javaScriptPlugin.executeAsync(script, "paramOne");

        verify(mockedDriver).executeAsyncScript(script, "paramOne");
        assertThat(result).isSameAs(response);
    }

}