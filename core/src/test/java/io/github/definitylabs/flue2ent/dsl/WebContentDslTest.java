package io.github.definitylabs.flue2ent.dsl;

import io.github.definitylabs.flue2ent.Website;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebContentDslTest {

    @Mock
    private WebContentDsl webContentDsl;

    @Test
    public void website_returnsWebsite() {
        Website mockedWebsite = mock(Website.class);

        webContentDsl.setWebsite(mockedWebsite);

        Website website = webContentDsl.website();

        assertThat(website).isSameAs(mockedWebsite);
    }

    @Test
    public void driver_returnsDriver() {
        Website mockedWebsite = mock(Website.class);
        WebDriver mockedDriver = mock(WebDriver.class);

        when(mockedWebsite.getDriver()).thenReturn(mockedDriver);

        webContentDsl.setWebsite(mockedWebsite);

        WebDriver driver = webContentDsl.driver();

        assertThat(driver).isSameAs(mockedDriver);
    }

    @Test
    public void init_isCalledAfterSetWebsite() {
        Website mockedWebsite = mock(Website.class);

        doAnswer(invocationOnMock -> invocationOnMock.callRealMethod()).when(webContentDsl).init();
        webContentDsl.setWebsite(mockedWebsite);

        verify(webContentDsl).init();
    }

    @Test
    public void getResponse_returnsSameInstance() {
        class Content extends WebContentDsl<Content> {

        }

        Content content = new Content();
        Content response = content.getResponse();

        assertThat(response).isSameAs(content);
    }

}