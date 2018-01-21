package org.definitylabs.flue2ent;

import org.definitylabs.flue2ent.element.FindElementBy;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.definitylabs.flue2ent.element.table.TableElement;
import org.definitylabs.flue2ent.page.PageObject;
import org.definitylabs.flue2ent.plugin.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.definitylabs.flue2ent.Website.from;
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
    public void from_returnsFunction() {
        PageObject<PageObject> content = mock(PageObject.class);

        Website website = mock(Website.class);
        when(website.at(content)).thenReturn(content);

        Function<Website, PageObject> from = Website.from(content);
        PageObject response = from.apply(website);

        verify(website).at(content);
        assertThat(response).isSameAs(content);
    }

    @Test
    public void from_withClass_returnsFunction() {
        TableElement tableElement = mock(TableElement.class);

        Website website = mock(Website.class);
        when(website.at(TableElement.class)).thenReturn(tableElement);

        Function<Website, TableElement> from = Website.from(TableElement.class);
        TableElement response = from.apply(website);

        verify(website).at(TableElement.class);
        assertThat(response).isSameAs(tableElement);
    }

    @Test
    public void element_returnsFunction() {
        By byTable = By.tagName("table");

        WebElementWrapper mockedElement = mock(WebElementWrapper.class);

        Website website = mock(Website.class);
        when(website.findElement(byTable)).thenReturn(mockedElement);

        Function<Website, WebElementWrapper> element = Website.element(byTable);
        WebElementWrapper response = element.apply(website);

        verify(website).findElement(byTable);
        assertThat(response).isSameAs(mockedElement);
    }

    @Test
    public void elements_returnsFunction() {
        By byTable = By.tagName("table");

        WebElementWrapper mockedElement = mock(WebElementWrapper.class);

        Website website = mock(Website.class);
        when(website.findElements(byTable)).thenReturn(Collections.singletonList(mockedElement));

        Function<Website, List<WebElementWrapper>> element = Website.elements(byTable);
        List<WebElementWrapper> response = element.apply(website);

        verify(website).findElements(byTable);
        assertThat(response).hasSize(1);
        assertThat(response).contains(mockedElement);
    }

    @Test
    public void isLoad_returnsFunction() {
        Function<Website, Boolean> isLoad = Website.isLoad();

        Website website = mock(Website.class);
        PagePlugin page = mock(PagePlugin.class);
        when(website.page()).thenReturn(page);
        when(page.isLoad()).thenReturn(true);

        Boolean response = isLoad.apply(website);

        verify(page).isLoad();

        assertThat(response).isTrue();
    }

    @Test
    public void getDriver_returnsWebDriver() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        assertThat(website.getDriver()).isSameAs(driver);
    }

    @Test
    public void getUrl_returnsWebsiteUrl() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        assertThat(website.getUrl()).isEqualTo(TEST_WEBSITE_URL);
    }

    @Test
    public void website_visit_callsDriverGet() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        String url = "http://google.com";
        website.visit(url);

        verify(driver).get(url);
    }

    @Test
    public void justWait_returnsWebsiteWaiter() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        WaiterPlugin waiter = website.justWait();

        assertThat(waiter).isNotNull();
    }

    @Test
    public void actions_returnsActionsPlugin() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        ActionsPlugin actionsPlugin = website.actions();

        assertThat(actionsPlugin).isNotNull();
    }

    @Test
    public void mouse_returnsMousePlugin() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        MousePlugin mousePlugin = website.mouse();

        assertThat(mousePlugin).isNotNull();
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
        PageObject<PageObject> webContentDsl = mock(PageObject.class);
        when(webContentDsl.getResponse()).thenReturn(webContentDsl);

        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);
        PageObject at = website.at(webContentDsl);

        verify(webContentDsl).setWebsite(website);
        assertThat(at).isSameAs(webContentDsl);
    }

    @Test
    public void at_whenWebProxy_returnsWebElementProxy() {
        WebElement titleElement = mock(WebElement.class);
        when(driver.findElement(By.id("title"))).thenReturn(titleElement);
        when(titleElement.getText()).thenReturn("Title");

        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);
        WebPage webPage = website.at(WebPage.class);

        assertThat(webPage.getTitle()).isEqualTo("Title");
    }

    @Test
    public void at_setsDriverAndResponseParameter() {
        WebElementWrapper webElementWrapper = mock(WebElementWrapper.class);

        PageObject<WebElementWrapper> webContentDsl = mock(PageObject.class);
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
    public void get_waitsElementExistAndReturn() {
        WebElementWrapper webElementWrapper = mock(WebElementWrapper.class);

        PageObject<WebElementWrapper> webContentDsl = mock(PageObject.class);
        when(webContentDsl.getResponse())
                .thenThrow(new StaleElementReferenceException("element"))
                .thenThrow(new StaleElementReferenceException("element"))
                .thenReturn(webElementWrapper);

        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        WebElementWrapper elementWrapper = website.get(from(webContentDsl));

        assertThat(elementWrapper).isSameAs(webElementWrapper);
    }

    @Test
    public void hasFound_returnsSupplier() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);
        Supplier<Boolean> hasFound = website.hasFound(w -> true);

        Boolean response = hasFound.get();

        assertThat(response).isTrue();
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

    interface WebPage {
        @FindElementBy(id = "title")
        String getTitle();
    }

}