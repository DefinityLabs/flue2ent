package io.github.definitylabs.flue2ent;

import io.github.definitylabs.flue2ent.dsl.WebContentDsl;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebsiteTest {

    private static final String TEST_WEBSITE_URL = "https://google.com";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
    public void getUrl_returnsWebsiteUrl() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        assertThat(website.getUrl()).isEqualTo(TEST_WEBSITE_URL);
    }

    @Test
    public void getCurrentUrl_returnsWebDriverCurrentUrl() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        String mockedCurrentUrl = TEST_WEBSITE_URL + "/a/";
        when(driver.getCurrentUrl()).thenReturn(mockedCurrentUrl);

        String currentUrl = website.getCurrentUrl();

        verify(driver).getCurrentUrl();
        assertThat(currentUrl).isEqualTo(mockedCurrentUrl);
    }

    @Test
    public void getTitle_returnsWebDriverTitle() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        String mockedTitle = "Page Title";
        when(driver.getTitle()).thenReturn(mockedTitle);

        String currentUrl = website.getTitle();

        verify(driver).getTitle();
        assertThat(currentUrl).isEqualTo(mockedTitle);
    }

    @Test
    public void justWait_returnsWebsiteWaiter() {
        Website website = Website.with(driver).visit(TEST_WEBSITE_URL);

        WebsiteWaiter waiter = website.justWait();

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
    public void screenshot_takeAsBytes_executesConsumer() {
        Consumer<byte[]> mockedConsumer = mock(Consumer.class);

        byte[] screenshotBytes = new byte[0];

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.BYTES)).thenReturn(screenshotBytes);

        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);
        website.screenshot().takeAsBytes(mockedConsumer);

        verify(mockedConsumer).accept(screenshotBytes);
    }

    @Test
    public void screenshot_takeAsFile_executesConsumer() {
        Consumer<File> mockedConsumer = mock(Consumer.class);

        File file = mock(File.class);

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.FILE)).thenReturn(file);

        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);
        website.screenshot().takeAsFile(mockedConsumer);

        verify(mockedConsumer).accept(file);
    }

    @Test
    public void screenshot_take_savesFileToScreenshotDirectory() throws IOException {
        File file = File.createTempFile("temporary", ".png");

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.FILE)).thenReturn(file);

        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);
        website.screenshot().take();

        File folder = new File("screenshot");
        assertThat(folder.exists()).isTrue();
        assertThat(folder.isDirectory()).isTrue();
        assertThat(folder.listFiles()).hasSize(1);

        FileUtils.forceDeleteOnExit(folder);
    }

    @Test
    public void screenshot_take_whenCopyThrowsIOException_throwsRuntimeException() throws IOException {
        File file = mock(File.class);

        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        when(mockedDriver.getScreenshotAs(OutputType.FILE)).thenReturn(file);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Cannot save screenshot file");

        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);

        try {
            website.screenshot().take();
        } finally {
            File folder = new File("screenshot");
            FileUtils.forceDeleteOnExit(folder);
        }
    }

    @Test
    public void scroll_top_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);

        Website.Scroll scroll = website.scroll();
        Website.Scroll result = scroll.top();

        verify(mockedDriver).executeScript("window.scrollTo(0, 0);");

        assertThat(result).isSameAs(scroll);
    }

    @Test
    public void scroll_up_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);

        Website.Scroll scroll = website.scroll();
        Website.Scroll result = scroll.up();

        verify(mockedDriver).executeScript("window.scrollBy(0, -250);");

        assertThat(result).isSameAs(scroll);
    }

    @Test
    public void scroll_down_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);

        Website.Scroll scroll = website.scroll();
        Website.Scroll result = scroll.down();

        verify(mockedDriver).executeScript("window.scrollBy(0, 250);");

        assertThat(result).isSameAs(scroll);
    }

    @Test
    public void scroll_bottom_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);

        Website.Scroll scroll = website.scroll();
        Website.Scroll result = scroll.bottom();

        verify(mockedDriver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        assertThat(result).isSameAs(scroll);
    }

    @Test
    public void scroll_by_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);

        Website.Scroll scroll = website.scroll();
        Website.Scroll result = scroll.by(10, 20);

        verify(mockedDriver).executeScript("window.scrollBy(10, 20);");

        assertThat(result).isSameAs(scroll);
    }

    @Test
    public void scroll_to_executesJavascript() {
        RemoteWebDriver mockedDriver = mock(RemoteWebDriver.class);
        Website website = Website.with(mockedDriver).visit(TEST_WEBSITE_URL);

        Website.Scroll scroll = website.scroll();
        Website.Scroll result = scroll.to(100, 200);

        verify(mockedDriver).executeScript("window.scrollTo(100, 200);");

        assertThat(result).isSameAs(scroll);
    }

}