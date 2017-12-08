package io.github.definitylabs.flue2ent;

import io.github.definitylabs.flue2ent.dsl.WebContentDsl;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import io.github.definitylabs.flue2ent.plugin.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebsiteTest {

    private static final String TEST_WEBSITE_URL = "https://google.com";

    @Mock
    private WebDriver driver;

    @Test
    public void visit_returnsWebsite() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        assertThat(website).isNotNull();
    }

    @Test
    public void visit_callsDriverGet() {
        Website.with(driver).visit(TEST_WEBSITE_URL);

        verify(driver).get(TEST_WEBSITE_URL);
    }

    @Test
    public void getDriver_returnsWebDriver() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        assertThat(website.getDriver()).isSameAs(driver);
    }

    @Test
    public void createFluentWait_returnsWait() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        FluentWait<WebDriver> wait = website.createFluentWait();

        assertThat(wait).isNotNull();
    }

    @Test
    public void getUrl_returnsWebsiteUrl() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        assertThat(website.getUrl()).isEqualTo(TEST_WEBSITE_URL);
    }

    @Test
    public void justWait_returnsWebsiteWaiter() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        WaiterPlugin waiter = website.justWait();

        assertThat(waiter).isNotNull();
    }

    @Test
    public void findElement_callsDriverFindElement() {
        By bySelect = By.tagName("select");

        WebElement mockedSelect = mock(WebElement.class);
        when(driver.findElement(bySelect)).thenReturn(mockedSelect);

        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        WebElementWrapper select = website.findElement(bySelect);

        verify(driver).findElement(bySelect);
        assertThat(select.webElement()).isSameAs(mockedSelect);
    }

    @Test
    public void findElements_callsDriverFindElements() {
        By bySelect = By.tagName("select");

        WebElement mockedSelectOne = mock(WebElement.class);
        WebElement mockedSelectTwo = mock(WebElement.class);
        when(driver.findElements(bySelect)).thenReturn(Arrays.asList(mockedSelectOne, mockedSelectTwo));

        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        List<WebElementWrapper> selects = website.findElements(bySelect);

        verify(driver).findElements(bySelect);
        assertThat(selects).hasSize(2);
        assertThat(selects.get(0).webElement()).isSameAs(mockedSelectOne);
        assertThat(selects.get(1).webElement()).isSameAs(mockedSelectTwo);
    }

    @Test
    public void at_setsDriverAndReturnsParameter() {
        WebContentDsl<WebContentDsl> webContentDsl = mock(WebContentDsl.class);
        when(webContentDsl.getResponse()).thenReturn(webContentDsl);

        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);
        WebContentDsl at = website.at(webContentDsl);

        verify(webContentDsl).setWebsite(website);
        assertThat(at).isSameAs(webContentDsl);
    }

    @Test
    public void at_setsDriverAndResponseParameter() {
        WebElementWrapper webElementWrapper = mock(WebElementWrapper.class);

        WebContentDsl<WebElementWrapper> webContentDsl = mock(WebContentDsl.class);
        when(webContentDsl.getResponse()).thenReturn(webElementWrapper);

        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);
        WebElementWrapper at = website.at(webContentDsl);

        verify(webContentDsl).setWebsite(website);
        assertThat(at).isSameAs(webElementWrapper);
    }

    @Test
    public void refresh_callsNavigateRefresh() {
        WebDriver.Navigation navigation = mock(WebDriver.Navigation.class);
        when(driver.navigate()).thenReturn(navigation);

        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);
        website.refresh();

        verify(navigation).refresh();
    }

    @Test
    public void alert_returnsAlertPlugin() {
        RemoteWebDriver mockedWebDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedWebDriver).visit(TEST_WEBSITE_URL);

        AlertPlugin alertPlugin = website.alert();

        assertThat(alertPlugin).isNotNull();
    }

    @Test
    public void screenshot_returnsScreenshotPlugin() {
        RemoteWebDriver mockedWebDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedWebDriver).visit(TEST_WEBSITE_URL);

        ScreenshotPlugin screenshotPlugin = website.screenshot();

        assertThat(screenshotPlugin).isNotNull();
    }

    @Test
    public void javaScript_returnsJavaScriptPlugin() {
        RemoteWebDriver mockedWebDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedWebDriver).visit(TEST_WEBSITE_URL);

        JavaScriptPlugin javaScriptPlugin = website.javaScript();

        assertThat(javaScriptPlugin).isNotNull();
    }

    @Test
    public void scroll_returnsScrollPlugin() {
        RemoteWebDriver mockedWebDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedWebDriver).visit(TEST_WEBSITE_URL);

        ScrollPlugin scrollPlugin = website.scroll();

        assertThat(scrollPlugin).isNotNull();
    }

    @Test
    public void page_returnsPagePlugin() {
        RemoteWebDriver mockedWebDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedWebDriver).visit(TEST_WEBSITE_URL);

        PagePlugin pagePlugin = website.page();

        assertThat(pagePlugin).isNotNull();
    }

}