package org.definitylabs.flue2ent.page;

import org.definitylabs.flue2ent.Website;
import org.definitylabs.flue2ent.data.MyPage;
import org.definitylabs.flue2ent.data.SubPage;
import org.definitylabs.flue2ent.element.SeleniumElementCreator;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SeleniumElementCreator.class)
public class PageObjectProxyTest {

    @Mock
    private Website website;

    @Mock
    private WebElement webElement;

    @Before
    public void beforeEach() throws Exception {
        Select select = mock(Select.class);
        PowerMockito.whenNew(Select.class).withArguments(webElement).thenReturn(select);
    }

    @Test
    public void webProxy_subPage_returnsWebElement() {
        WebElement h1Element = mock(WebElement.class);

        when(website.findElement(By.cssSelector(".subPage"))).thenReturn(new WebElementWrapper(webElement));
        when(website.findElement(By.tagName("h1"))).thenReturn(new WebElementWrapper(h1Element));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        SubPage subPage = myPage.subPage();

        assertThat(subPage.title().webElement()).isSameAs(h1Element);
    }

    @Test
    public void findByCssUsingProxy_nothing_returnsWebElement() {
        WebElement h1Element = mock(WebElement.class);

        when(website.findElement(By.cssSelector(".subPage"))).thenReturn(new WebElementWrapper(webElement));
        when(website.findElement(By.tagName("h1"))).thenReturn(new WebElementWrapper(h1Element));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        SubPage subPage = myPage.subPage();

        assertThat(subPage.title().webElement()).isSameAs(h1Element);
    }

    @Test
    public void findByElement_returnsWebElement() {
        when(website.findElement(By.name("age"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper age = myPage.age();

        assertThat(age.webElement()).isEqualTo(webElement);
    }

    @Test
    public void findByElements_links_returnsWebElementList() {
        WebElement linkOne = mock(WebElement.class);
        when(linkOne.getText()).thenReturn("Link One");

        WebElement linkTwo = mock(WebElement.class);
        when(linkTwo.getText()).thenReturn("Link Two");

        when(website.findElements(By.tagName("a"))).thenReturn(Arrays.asList(new WebElementWrapper(linkOne), new WebElementWrapper(linkTwo)));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        List<String> links = myPage.links();

        assertThat(links).hasSize(2);
        assertThat(links.get(0)).isEqualTo("Link One");
        assertThat(links.get(1)).isEqualTo("Link Two");
    }

}