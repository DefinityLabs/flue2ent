package io.github.definitylabs.flue2ent.plugin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlertPluginTest {

    @Mock
    private WebDriver driver;

    @Mock
    private WebDriver.TargetLocator switchTo;

    @Mock
    private Alert alert;

    @Test
    public void ok_callsSwitchToAlertAccept() {
        AlertPlugin alertPlugin = new AlertPlugin(driver);

        when(driver.switchTo()).thenReturn(switchTo);
        when(switchTo.alert()).thenReturn(alert);

        AlertPlugin result = alertPlugin.ok();

        verify(alert).accept();
        assertThat(result).isSameAs(alertPlugin);
    }

    @Test
    public void dismiss_callsSwitchToAlertDismiss() {
        AlertPlugin alertPlugin = new AlertPlugin(driver);

        when(driver.switchTo()).thenReturn(switchTo);
        when(switchTo.alert()).thenReturn(alert);

        AlertPlugin result = alertPlugin.dismiss();

        verify(alert).dismiss();
        assertThat(result).isSameAs(alertPlugin);
    }

}