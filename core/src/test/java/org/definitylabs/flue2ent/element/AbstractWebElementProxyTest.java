package org.definitylabs.flue2ent.element;

import org.definitylabs.flue2ent.Website;
import org.definitylabs.flue2ent.data.MyPage;
import org.definitylabs.flue2ent.element.list.SelectElement;
import org.definitylabs.flue2ent.element.table.TableElement;
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

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SeleniumElementCreator.class)
public class AbstractWebElementProxyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private AbstractWebElementProxy webElementProxy;
    @Mock
    private Website website;

    @Mock
    private WebElement webElement;

    @Before
    public void beforeEach() throws Exception {
        Select select = mock(Select.class);
        PowerMockito.whenNew(Select.class).withArguments(webElement).thenReturn(select);

        when(webElementProxy.findElement(any(By.class))).thenAnswer(invocationOnMock -> website.findElement((By) invocationOnMock.getArguments()[0]));
        when(webElementProxy.findElements(any(By.class))).thenAnswer(invocationOnMock -> website.findElements((By) invocationOnMock.getArguments()[0]));
        when(webElementProxy.canHandle(any(Method.class))).thenCallRealMethod();
        when(webElementProxy.handle(any(), any(Method.class), any())).thenCallRealMethod();
    }

    @Test
    public void findById_getName_returnsWebElementText() {
        when(website.findElement(By.id("name"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getText()).thenReturn("name");

        MyPage myPage = newInstance(MyPage.class, website);

        String name = myPage.getName();

        assertThat(name).isEqualTo("name");
    }

    @Test
    public void findByName_age_returnsWebElement() {
        when(website.findElement(By.name("age"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper age = myPage.age();

        assertThat(age.webElement()).isEqualTo(webElement);
    }

    @Test
    public void findByCss_birthday_returnsWebElement() {
        when(website.findElement(By.cssSelector(".birthday"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper birthday = myPage.birthday();

        assertThat(birthday.webElement()).isEqualTo(webElement);
    }

    @Test
    public void findByClassName_getBirthday_returnsWebElementValueAttribute() {
        when(website.findElement(By.className("birthday"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getAttribute("value")).thenReturn("birthday");

        MyPage myPage = newInstance(MyPage.class, website);

        String birthday = myPage.getBirthday();

        assertThat(birthday).isEqualTo("birthday");
    }

    @Test
    public void findByXpath_getBirthDate_throwsException() {
        when(website.findElement(By.xpath("//input[name()='birthday']"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getAttribute("value")).thenReturn("birthday");

        MyPage myPage = newInstance(MyPage.class, website);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("return type should be String");

        myPage.getBirthDate();
    }

    @Test
    public void findByWithoutArguments_undefined_throwsException() {
        MyPage myPage = newInstance(MyPage.class, website);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("element path is not defined");

        myPage.undefined();
    }

    @Test
    public void findByWithoutArguments_nothing_whenCanHandleIsTrue_returnsNull() {
        when(webElementProxy.canHandle(any(Method.class))).thenReturn(true);

        MyPage myPage = newInstance(MyPage.class, website);

        String undefined = myPage.nothing();

        assertThat(undefined).isNull();
    }

    @Test
    public void findByTextLink_failedLink_throwsException() {
        when(website.findElement(By.linkText("link"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Cannot create instance of org.definitylabs.flue2ent.element.WebElementDecorator");

        myPage.failedLink();
    }

    @Test
    public void noAnnotation_nothing_throwsException() {
        MyPage myPage = newInstance(MyPage.class, website);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Method not implemented");

        myPage.nothing();
    }

    @Test
    public void findByTagName_results_returnsTableElement() {
        when(website.findElement(By.tagName("table"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        TableElement results = myPage.results();

        assertThat(results.webElement().webElement()).isSameAs(webElement);
    }

    @Test
    public void findByPartialLinkText_address_returnsTableElement() {
        when(website.findElement(By.partialLinkText("Address"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper address = myPage.address();

        assertThat(address.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByButton_submit_returnsWebElement() {
        when(website.findElement(ExtendedBy.byButton("Submit"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper submit = myPage.submit();

        assertThat(submit.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByPlaceholder_email_returnsWebElement() {
        when(website.findElement(ExtendedBy.byPlaceholder("Email"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper email = myPage.email();

        assertThat(email.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByLabel_login_returnsWebElement() {
        when(website.findElement(ExtendedBy.byLabel("Login"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper login = myPage.login();

        assertThat(login.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByLabelContaining_password_returnsWebElement() {
        when(website.findElement(ExtendedBy.byLabelContaining("Password or PIN"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper password = myPage.password();

        assertThat(password.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByValue_genderMale_returnsWebElement() {
        when(website.findElement(ExtendedBy.byValue("MALE"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper genderMale = myPage.genderMale();

        assertThat(genderMale.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByValue_male_returnsWebElement() {
        when(website.findElement(ExtendedBy.byValue("MALE"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper genderMale = myPage.male();

        assertThat(genderMale.webElement()).isSameAs(webElement);
    }

    @Test
    public void list_rows_returnsWebElementList() {
        WebElement selectOne = mock(WebElement.class);
        WebElement selectTwo = mock(WebElement.class);

        when(website.findElements(By.tagName("select"))).thenReturn(Arrays.asList(new WebElementWrapper(selectOne), new WebElementWrapper(selectTwo)));

        MyPage myPage = newInstance(MyPage.class, website);

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

        MyPage myPage = newInstance(MyPage.class, website);

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

        MyPage myPage = newInstance(MyPage.class, website);

        WebElementWrapper sessionOne = myPage.elementByClassNameAndIndex("session", 1);
        WebElementWrapper sessionTwo = myPage.elementByClassNameAndIndex("session", 2);

        assertThat(sessionOne.webElement()).isSameAs(sessionOneElement);
        assertThat(sessionTwo.webElement()).isSameAs(sessionTwoElement);
    }

    @Test
    public void args_elementByClassName_throwsException() {
        MyPage myPage = newInstance(MyPage.class, website);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("All parameters should be annotated with @Param");

        myPage.elementByClassName("session");
    }

    <T> T newInstance(Class<T> type, Website website) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, webElementProxy);
    }

}