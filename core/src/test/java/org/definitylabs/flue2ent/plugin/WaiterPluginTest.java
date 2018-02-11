package org.definitylabs.flue2ent.plugin;

import org.definitylabs.flue2ent.Website;
import org.definitylabs.flue2ent.element.SeleniumElementCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SeleniumElementCreator.class)
public class WaiterPluginTest {

    @Mock
    private Website website;

    @Mock
    private FluentWait<WebDriver> wait;

    @Before
    public void beforeEach() throws Exception {
        PowerMockito.mockStatic(SeleniumElementCreator.class, invocationOnMock -> wait);
    }

    @Test
    public void upTo_callsWaitWithTimeout() {
        WaiterPlugin waiter = new WaiterPlugin(website);

        WaiterPlugin waiterPlugin = waiter.upTo(10, TimeUnit.SECONDS);

        verify(wait).withTimeout(10, TimeUnit.SECONDS);
        assertThat(waiterPlugin).isSameAs(waiter);
    }

    @Test
    public void pollingEvery_callsWaitPullingEvery() {
        WaiterPlugin waiter = new WaiterPlugin(website);

        WaiterPlugin waiterPlugin = waiter.pollingEvery(2, TimeUnit.SECONDS);

        verify(wait).pollingEvery(2, TimeUnit.SECONDS);
        assertThat(waiterPlugin).isSameAs(waiter);
    }

    @Test
    public void withMessage_callsWaitWithMessage() {
        WaiterPlugin waiter = new WaiterPlugin(website);

        String message = "I can't wait";
        WaiterPlugin waiterPlugin = waiter.withMessage(message);

        verify(wait).withMessage(message);
        assertThat(waiterPlugin).isSameAs(waiter);
    }

    @Test
    public void ignoring_callsWaitIgnoring() {
        WaiterPlugin waiter = new WaiterPlugin(website);

        WaiterPlugin waiterPlugin = waiter.ignoring(IllegalArgumentException.class, NullPointerException.class);

        verify(wait).ignoreAll(Arrays.asList(IllegalArgumentException.class, NullPointerException.class));
        assertThat(waiterPlugin).isSameAs(waiter);
    }

    @Test
    public void until_callsWaitUntil() {
        WaiterPlugin waiter = new WaiterPlugin(website);

        when(wait.until(any())).thenAnswer(invocationOnMock -> {
            Function<WebDriver, WebElement> function = invocationOnMock.getArgumentAt(0, Function.class);
            return function.apply(null);
        });

        WebElement mockedElement = mock(WebElement.class);
        Function<Website, WebElement> function = mock(Function.class);
        when(function.apply(any())).thenReturn(mockedElement);

        WebElement element = waiter.until(function);

        verify(wait).until(any());
        verify(function).apply(website);
        assertThat(element).isSameAs(mockedElement);
    }

    @Test
    public void expectedCondition_until_callsWaitUntil() {
        WaiterPlugin waiter = new WaiterPlugin(website);

        WebElement mockedElement = mock(WebElement.class);
        when(wait.until(any())).thenReturn(mockedElement);

        ExpectedCondition<WebElement> expectedCondition = driver -> mockedElement;

        WebElement element = waiter.until(expectedCondition);

        verify(wait).until(same(expectedCondition));
        assertThat(element).isSameAs(mockedElement);
    }

    @Test
    public void supplier_until_callsWaitUntil() {
        WaiterPlugin waiter = new WaiterPlugin(website);

        when(wait.until(any())).thenAnswer(invocationOnMock -> {
            Function<WebDriver, WebElement> function = invocationOnMock.getArgumentAt(0, Function.class);
            return function.apply(null);
        });

        WebElement mockedElement = mock(WebElement.class);
        Supplier<WebElement> supplier = mock(Supplier.class);
        when(supplier.get()).thenReturn(mockedElement);

        WebElement element = waiter.until(supplier);

        verify(wait).until(any());
        verify(supplier).get();
        assertThat(element).isSameAs(mockedElement);
    }

    @Test
    public void driver_until_callsWaitUntil() {
        WaiterPlugin waiter = new WaiterPlugin(website);

        WebElement mockedElement = mock(WebElement.class);
        when(wait.until(any())).thenReturn(mockedElement);

        Function<WebDriver, WebElement> function = driver -> mockedElement;

        WebElement element = waiter.driver().until(function);

        verify(wait).until(same(function));
        assertThat(element).isSameAs(mockedElement);
    }

}