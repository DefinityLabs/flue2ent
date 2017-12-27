package org.definitylabs.flue2ent.element.simple;

import org.definitylabs.flue2ent.data.FakeTableElement;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleWebElementDecoratorProxyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private WebElement webElement;

    @Test
    public void webElement_returnsWebElementWrapperFromDecorator() {
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);
        FakeTableElement fakeTableElement = SimpleWebElementDecoratorProxy.newInstance(FakeTableElement.class, webElementWrapper);
        WebElementWrapper fakeTableWebElement = fakeTableElement.webElement();

        assertThat(fakeTableWebElement).isSameAs(webElementWrapper);
    }

    @Test
    public void findElement_headerRow_returnsNestedElement() {
        WebElement rowElement = mock(WebElement.class);
        when(webElement.findElement(By.cssSelector(".header .row:nth-child(0)"))).thenReturn(rowElement);

        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);
        FakeTableElement fakeTableElement = SimpleWebElementDecoratorProxy.newInstance(FakeTableElement.class, webElementWrapper);

        WebElementWrapper rowElementWrapper = fakeTableElement.headerRow(0);

        assertThat(rowElementWrapper.webElement()).isSameAs(rowElement);
    }

    @Test
    public void findElements_rows_returnsWebElementList() {
        WebElement rowElementOne = mock(WebElement.class);
        WebElement rowElementTwo = mock(WebElement.class);
        when(webElement.findElements(By.className("row"))).thenReturn(Arrays.asList(rowElementOne, rowElementTwo));

        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);
        FakeTableElement fakeTableElement = SimpleWebElementDecoratorProxy.newInstance(FakeTableElement.class, webElementWrapper);

        List<WebElementWrapper> rows = fakeTableElement.rows();

        assertThat(rows).hasSize(2);
        assertThat(rows.get(0).webElement()).isSameAs(rowElementOne);
        assertThat(rows.get(1).webElement()).isSameAs(rowElementTwo);
    }

    @Test
    public void findElement_webElement_returnsNestedElement() {
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);
        FakeTableElement fakeTableElement = SimpleWebElementDecoratorProxy.newInstance(FakeTableElement.class, webElementWrapper);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Method not implemented");

        fakeTableElement.webElement(0);
    }

    @Test
    public void findElement_undefined_returnsNestedElement() {
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);
        FakeTableElement fakeTableElement = SimpleWebElementDecoratorProxy.newInstance(FakeTableElement.class, webElementWrapper);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Method not implemented");

        fakeTableElement.undefined();
    }

}