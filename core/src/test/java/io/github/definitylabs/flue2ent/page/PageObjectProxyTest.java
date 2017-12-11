package io.github.definitylabs.flue2ent.page;

import io.github.definitylabs.flue2ent.Website;
import io.github.definitylabs.flue2ent.element.ExtendedBy;
import io.github.definitylabs.flue2ent.element.SeleniumElementCreator;
import io.github.definitylabs.flue2ent.element.WebElementDecorator;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import io.github.definitylabs.flue2ent.element.list.SelectElement;
import io.github.definitylabs.flue2ent.element.table.TableElement;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SeleniumElementCreator.class)
public class PageObjectProxyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
    public void findById_getName_returnsWebElementText() {
        when(website.findElement(By.id("name"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getText()).thenReturn("name");

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        String name = myPage.getName();

        assertThat(name).isEqualTo("name");
    }

    @Test
    public void findByName_age_returnsWebElement() {
        when(website.findElement(By.name("age"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper age = myPage.age();

        assertThat(age.webElement()).isEqualTo(webElement);
    }

    @Test
    public void findByCss_birthday_returnsWebElement() {
        when(website.findElement(By.cssSelector(".birthday"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper birthday = myPage.birthday();

        assertThat(birthday.webElement()).isEqualTo(webElement);
    }

    @Test
    public void findByClassName_getBirthday_returnsWebElementValueAttribute() {
        when(website.findElement(By.className("birthday"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getAttribute("value")).thenReturn("birthday");

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        String birthday = myPage.getBirthday();

        assertThat(birthday).isEqualTo("birthday");
    }

    @Test
    public void findByXpath_getBirthDate_throwsException() {
        when(website.findElement(By.xpath("//input[name()='birthday']"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getAttribute("value")).thenReturn("birthday");

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("return type should be String");

        myPage.getBirthDate();
    }

    @Test
    public void findByWithoutArguments_undefined_throwsException() {
        when(website.findElement(By.xpath("//input[name()='birthday']"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getAttribute("value")).thenReturn("birthday");

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("element path is not defined");

        myPage.undefined();
    }

    @Test
    public void findByTextLink_failedLink_throwsException() {
        when(website.findElement(By.linkText("link"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Cannot create instance of io.github.definitylabs.flue2ent.element.WebElementDecorator");

        myPage.failedLink();
    }

    @Test
    public void noAnnotation_nothing_throwsException() {
        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Method not implemented");

        myPage.nothing();
    }

    @Test
    public void findByTagName_results_returnsTableElement() {
        when(website.findElement(By.tagName("table"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        TableElement results = myPage.results();

        assertThat(results.webElement().webElement()).isSameAs(webElement);
    }

    @Test
    public void findByPartialLinkText_address_returnsTableElement() {
        when(website.findElement(By.partialLinkText("Address"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper address = myPage.address();

        assertThat(address.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByButton_submit_returnsWebElement() {
        when(website.findElement(ExtendedBy.byButton("Submit"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper submit = myPage.submit();

        assertThat(submit.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByPlaceholder_email_returnsWebElement() {
        when(website.findElement(ExtendedBy.byPlaceholder("Email"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper email = myPage.email();

        assertThat(email.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByLabel_login_returnsWebElement() {
        when(website.findElement(ExtendedBy.byLabel("Login"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper login = myPage.login();

        assertThat(login.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByLabelContaining_password_returnsWebElement() {
        when(website.findElement(ExtendedBy.byLabelContaining("Password or PIN"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper password = myPage.password();

        assertThat(password.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByValue_genderMale_returnsWebElement() {
        when(website.findElement(ExtendedBy.byValue("MALE"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper genderMale = myPage.genderMale();

        assertThat(genderMale.webElement()).isSameAs(webElement);
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
    public void list_rows_returnsWebElementList() {
        WebElement selectOne = mock(WebElement.class);
        WebElement selectTwo = mock(WebElement.class);

        when(website.findElements(By.tagName("select"))).thenReturn(Arrays.asList(new WebElementWrapper(selectOne), new WebElementWrapper(selectTwo)));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        List<SelectElement> selects = myPage.selects();

        assertThat(selects).hasSize(2);
        assertThat(selects.get(0).webElement().webElement()).isSameAs(selectOne);
        assertThat(selects.get(1).webElement().webElement()).isSameAs(selectTwo);
    }

    @Test
    public void list_links_returnsWebElementList() {
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

    @Test
    public void args_elementByClassNameAndIndex_returnsWebElement() {
        WebElement sessionOneElement = mock(WebElement.class);
        WebElement sessionTwoElement = mock(WebElement.class);

        when(website.findElement(By.cssSelector(".session:nth-child(1)"))).thenReturn(new WebElementWrapper(sessionOneElement));
        when(website.findElement(By.cssSelector(".session:nth-child(2)"))).thenReturn(new WebElementWrapper(sessionTwoElement));

        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        WebElementWrapper sessionOne = myPage.elementByClassNameAndIndex("session", 1);
        WebElementWrapper sessionTwo = myPage.elementByClassNameAndIndex("session", 2);

        assertThat(sessionOne.webElement()).isSameAs(sessionOneElement);
        assertThat(sessionTwo.webElement()).isSameAs(sessionTwoElement);
    }

    @Test
    public void args_elementByClassName_throwsException() {
        MyPage myPage = PageObjectProxy.newInstance(MyPage.class, website);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("All parameters should be annotated with @Param");

        myPage.elementByClassName("session");
    }

    interface MyPage {

        @FindElementBy(id = "name")
        String getName();

        @FindElementBy(placeholder = "Email")
        WebElementWrapper email();

        @FindElementBy(name = "age")
        WebElementWrapper age();

        @FindElementBy(css = ".birthday")
        WebElementWrapper birthday();

        @FindElementBy(className = "birthday", andGetAttribute = "value")
        String getBirthday();

        @FindElementBy(xpath = "//input[name()='birthday']", andGetAttribute = "value")
        Date getBirthDate();

        @FindElementBy
        String undefined();

        @FindElementBy(tagName = "table")
        TableElement results();

        @FindElementBy(tagName = "select")
        List<SelectElement> selects();

        @FindElementBy(tagName = "a")
        List<String> links();

        @FindElementBy(linkText = "link")
        WebElementDecorator failedLink();

        @FindElementBy(button = "Submit")
        WebElementWrapper submit();

        @FindElementBy(partialLinkText = "Address")
        WebElementWrapper address();

        @FindElementBy(label = "Login")
        WebElementWrapper login();

        @FindElementBy(labelContaining = "Password or PIN")
        WebElementWrapper password();

        @FindElementBy(value = "MALE")
        WebElementWrapper genderMale();

        @FindElementBy(css = ".{className}:nth-child({index})")
        WebElementWrapper elementByClassNameAndIndex(@Param("className") String className, @Param("index") int index);

        @FindElementBy(css = ".{className}")
        WebElementWrapper elementByClassName(String className);

        @PageObject
        SubPage subPage();

        String nothing();

    }

    interface SubPage {

        @FindElementBy(tagName = "h1")
        WebElementWrapper title();

    }

}