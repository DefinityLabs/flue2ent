package io.github.definitylabs.flue2ent.element;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebElementWrapperTest {

    @Mock
    private WebElement webElement;

    @Test
    public void webElement_returnsWebElement() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        WebElement webElement = wrapper.webElement();

        assertThat(webElement).isSameAs(this.webElement);
    }

    @Test
    public void text_returnsWebElementGetText() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        String elementText = "element text";
        when(webElement.getText()).thenReturn(elementText);

        String text = wrapper.text();

        verify(webElement).getText();
        assertThat(text).isEqualTo(elementText);
    }

    @Test
    public void enter_callsWebElementSendKeys() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        String text = "text";
        WebElementWrapper webElementWrapper = wrapper.enter(text);

        verify(webElement).sendKeys(text);
        assertThat(webElementWrapper).isSameAs(wrapper);
    }

    @Test
    public void click_callsWebElementClick() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        WebElementWrapper webElementWrapper = wrapper.click();

        verify(webElement).click();
        assertThat(webElementWrapper).isSameAs(wrapper);
    }

    @Test
    public void submit_callsWebElementSubmit() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        WebElementWrapper webElementWrapper = wrapper.submit();

        verify(webElement).submit();
        assertThat(webElementWrapper).isSameAs(wrapper);
    }

    @Test
    public void isEnabled_returnsWebElementIsEnabled() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        when(webElement.isEnabled()).thenReturn(true);

        boolean isEnabled = wrapper.isEnabled();

        verify(webElement).isEnabled();
        assertThat(isEnabled).isTrue();
    }

    @Test
    public void isDisplayed_returnsWebElementIsEnabled() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        when(webElement.isDisplayed()).thenReturn(true);

        boolean isDisplayed = wrapper.isDisplayed();

        verify(webElement).isDisplayed();
        assertThat(isDisplayed).isTrue();
    }

    @Test
    public void isSelected_returnsWebElementIsEnabled() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        when(webElement.isSelected()).thenReturn(true);

        boolean isSelected = wrapper.isSelected();

        verify(webElement).isSelected();
        assertThat(isSelected).isTrue();
    }

    @Test
    public void findElement_returnsWrappedWebElement() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        WebElement mockedElement = mock(WebElement.class);
        when(webElement.findElement(any())).thenReturn(mockedElement);

        By bySpan = By.tagName("span");
        WebElementWrapper wrappedElement = wrapper.findElement(bySpan);

        verify(webElement).findElement(bySpan);
        assertThat(wrappedElement.webElement()).isSameAs(mockedElement);
    }

    @Test
    public void findElements_returnsWrappedWebElements() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        WebElement mockedElementOne = mock(WebElement.class);
        WebElement mockedElementTwo = mock(WebElement.class);
        when(webElement.findElements(any())).thenReturn(Arrays.asList(mockedElementOne, mockedElementTwo));

        By bySpan = By.tagName("span");
        List<WebElementWrapper> wrappedElements = wrapper.findElements(bySpan);

        verify(webElement).findElements(bySpan);
        assertThat(wrappedElements).hasSize(2);
        assertThat(wrappedElements.get(0).webElement()).isSameAs(mockedElementOne);
        assertThat(wrappedElements.get(1).webElement()).isSameAs(mockedElementTwo);
    }

    @Test
    public void as_returnsDecorator() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        SpecialElement specialElement = wrapper.as(SpecialElement::new);
        assertThat(specialElement).isNotNull();
        assertThat(specialElement.element).isSameAs(wrapper);
    }

    @Test
    public void has_returnsSupplier() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        Supplier<WebElementWrapper> supplier = wrapper.has(element -> element);

        assertThat(supplier.get()).isSameAs(wrapper);
    }

    @Test
    public void assertThis_returnsAssertJObject() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        wrapper.assertThis().isSameAs(wrapper);
    }

    @Test
    public void assertThat_returnsAssertJObject() {
        WebElementWrapper wrapper = new WebElementWrapper(webElement);

        String text = "Text";
        when(webElement.getText()).thenReturn(text);

        wrapper.assertThat(WebElementWrapper::text).isEqualTo(text);
    }

    class SpecialElement extends WebElementDecorator {
        public SpecialElement(WebElementWrapper element) {
            super(element);
        }
    }

}