package io.github.definitylabs.flue2ent.element;

import io.github.definitylabs.flue2ent.data.FakeTableElement;
import io.github.definitylabs.flue2ent.element.list.SelectElement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SeleniumElementCreator.class)
public class WebElementConverterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void new_returnsNewInstance() {
        assertThat(new WebElementConverter()).isNotNull();
    }

    @Test
    public void convertTo_whenAttributeIsNotEmptyAndReturnTypeIsNotString_throwsIllegalArgumentException() {
        WebElementWrapper webElementWrapper = mock(WebElementWrapper.class);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("return type should be String");

        WebElementConverter.convertTo(webElementWrapper, "name", Object.class);
    }

    @Test
    public void convertTo_whenAttributeIsNotEmptyAndReturnTypeIsString_returnsGetAttribute() {
        WebElement webElement = mock(WebElement.class);
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);

        String value = "value";
        when(webElement.getAttribute("name")).thenReturn(value);

        String name = (String) WebElementConverter.convertTo(webElementWrapper, "name", String.class);

        assertThat(name).isEqualTo(name);
    }

    @Test
    public void convertTo_whenAttributeIsEmptyAndReturnTypeIsString_returnsElementText() {
        WebElement webElement = mock(WebElement.class);
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);

        String text = "text";
        when(webElement.getText()).thenReturn(text);

        String value = (String) WebElementConverter.convertTo(webElementWrapper, "", String.class);

        assertThat(value).isEqualTo(text);
    }

    @Test
    public void convertTo_whenReturnTypeIsWebElementWrapper_returnsWebElementWrapper() {
        WebElement webElement = mock(WebElement.class);
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);

        String text = "text";
        when(webElement.getText()).thenReturn(text);

        WebElementWrapper value = (WebElementWrapper) WebElementConverter.convertTo(webElementWrapper, "", WebElementWrapper.class);

        assertThat(value).isSameAs(webElementWrapper);
    }

    @Test
    public void convertTo_whenAttributeIsEmptyAndReturnTypeIsSelectElement_returnsSelectElement() throws Exception {
        WebElement webElement = mock(WebElement.class);
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);

        Select select = mock(Select.class);
        PowerMockito.whenNew(Select.class).withAnyArguments().thenReturn(select);

        SelectElement selectElement = (SelectElement) WebElementConverter.convertTo(webElementWrapper, "", SelectElement.class);

        assertThat(selectElement.webElement()).isEqualTo(webElementWrapper);
    }

    @Test
    public void convertTo__returnsSelectElement() throws Exception {
        WebElement rowElementOne = mock(WebElement.class);
        WebElement rowElementTwo = mock(WebElement.class);

        WebElement webElement = mock(WebElement.class);
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);
        when(webElement.findElements(By.className("row"))).thenReturn(Arrays.asList(rowElementOne, rowElementTwo));

        FakeTableElement fakeTableElement = (FakeTableElement) WebElementConverter.convertTo(webElementWrapper, "", FakeTableElement.class);

        assertThat(fakeTableElement.rows()).hasSize(2);
        assertThat(fakeTableElement.rows().get(0).webElement()).isEqualTo(rowElementOne);
        assertThat(fakeTableElement.rows().get(1).webElement()).isEqualTo(rowElementTwo);
    }

    @Test
    public void convertTo_whenReturnTypeDoesNotHaveConstructor_throwsRuntimeException() {
        WebElement webElement = mock(WebElement.class);
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Cannot create instance of " + WebElementDecorator.class.getName());

        WebElementConverter.convertTo(webElementWrapper, "", WebElementDecorator.class);
    }

}