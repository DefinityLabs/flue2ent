package io.github.definitylabs.flue2ent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

}