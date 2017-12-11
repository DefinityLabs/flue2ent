package io.github.definitylabs.flue2ent.element.proxy;

import io.github.definitylabs.flue2ent.Website;
import io.github.definitylabs.flue2ent.element.ExtendedBy;
import io.github.definitylabs.flue2ent.element.WebElementDecorator;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import io.github.definitylabs.flue2ent.element.table.TableElement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebElementProxyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private Website website;
    @Mock
    private WebElement webElement;

    @Test
    public void findById_getName_returnsWebElementText() {
        when(website.findElement(By.id("name"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getText()).thenReturn("name");

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        String name = myPage.getName();

        assertThat(name).isEqualTo("name");
    }

    @Test
    public void findByName_age_returnsWebElement() {
        when(website.findElement(By.name("age"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        WebElementWrapper age = myPage.age();

        assertThat(age.webElement()).isEqualTo(webElement);
    }

    @Test
    public void findByCss_birthday_returnsWebElement() {
        when(website.findElement(By.cssSelector(".birthday"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        WebElementWrapper birthday = myPage.birthday();

        assertThat(birthday.webElement()).isEqualTo(webElement);
    }

    @Test
    public void findByClassName_getBirthday_returnsWebElementValueAttribute() {
        when(website.findElement(By.className("birthday"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getAttribute("value")).thenReturn("birthday");

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        String birthday = myPage.getBirthday();

        assertThat(birthday).isEqualTo("birthday");
    }

    @Test
    public void findByXpath_getBirthDate_throwsException() {
        when(website.findElement(By.xpath("//input[name()='birthday']"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getAttribute("value")).thenReturn("birthday");

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("return type should be String");

        myPage.getBirthDate();
    }

    @Test
    public void findByWithoutArguments_undefined_throwsException() {
        when(website.findElement(By.xpath("//input[name()='birthday']"))).thenReturn(new WebElementWrapper(webElement));
        when(webElement.getAttribute("value")).thenReturn("birthday");

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("element path is not defined");

        myPage.undefined();
    }

    @Test
    public void findByTextLink_failedLink_throwsException() {
        when(website.findElement(By.linkText("link"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Cannot create instance of io.github.definitylabs.flue2ent.element.WebElementDecorator");

        myPage.failedLink();
    }

    @Test
    public void noAnnotation_nothing_throwsException() {
        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Method not implemented");

        myPage.nothing();
    }

    @Test
    public void findByTagName_results_returnsTableElement() {
        when(website.findElement(By.tagName("table"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        TableElement results = myPage.results();

        assertThat(results.webElement().webElement()).isSameAs(webElement);
    }

    @Test
    public void findByPartialLinkText_address_returnsTableElement() {
        when(website.findElement(By.partialLinkText("Address"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        WebElementWrapper address = myPage.address();

        assertThat(address.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByButton_submit_returnsWebElement() {
        when(website.findElement(ExtendedBy.byButton("Submit"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        WebElementWrapper submit = myPage.submit();

        assertThat(submit.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByPlaceholder_email_returnsWebElement() {
        when(website.findElement(ExtendedBy.byPlaceholder("Email"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        WebElementWrapper email = myPage.email();

        assertThat(email.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByLabel_login_returnsWebElement() {
        when(website.findElement(ExtendedBy.byLabel("Login"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        WebElementWrapper login = myPage.login();

        assertThat(login.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByLabelContaining_password_returnsWebElement() {
        when(website.findElement(ExtendedBy.byLabelContaining("Password or PIN"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        WebElementWrapper password = myPage.password();

        assertThat(password.webElement()).isSameAs(webElement);
    }

    @Test
    public void findByValue_genderMale_returnsWebElement() {
        when(website.findElement(ExtendedBy.byValue("MALE"))).thenReturn(new WebElementWrapper(webElement));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        WebElementWrapper genderMale = myPage.genderMale();

        assertThat(genderMale.webElement()).isSameAs(webElement);
    }

    @Test
    public void webProxy_subPage_returnsWebElement() {
        WebElement h1Element = mock(WebElement.class);

        when(website.findElement(By.cssSelector(".subPage"))).thenReturn(new WebElementWrapper(webElement));
        when(website.findElement(By.tagName("h1"))).thenReturn(new WebElementWrapper(h1Element));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        SubPage subPage = myPage.subPage();

        assertThat(subPage.title().webElement()).isSameAs(h1Element);
    }

    @Test
    public void findByCssUsingProxy_nothing_returnsWebElement() {
        WebElement h1Element = mock(WebElement.class);

        when(website.findElement(By.cssSelector(".subPage"))).thenReturn(new WebElementWrapper(webElement));
        when(website.findElement(By.tagName("h1"))).thenReturn(new WebElementWrapper(h1Element));

        MyPage myPage = WebElementProxy.newInstance(MyPage.class, website);

        SubPage subPage = myPage.subPage();

        assertThat(subPage.title().webElement()).isSameAs(h1Element);
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

        @WebProxy
        SubPage subPage();

        String nothing();

    }

    interface SubPage {

        @FindElementBy(tagName = "h1")
        WebElementWrapper title();

    }

}