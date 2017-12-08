package io.github.definitylabs.flue2ent.element.list;

import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectOptionElementTest {

    @Mock
    private SelectElement selectElement;

    @Mock
    private WebElement webElement;

    @Mock
    private WebElementWrapper webElementWrapper;

    @Before
    public void beforeEach() {
        webElementWrapper = new WebElementWrapper(webElement);
    }

    @Test
    public void select_returnsSelectOptionElement() {
        String value = "name";
        when(webElement.getAttribute("value")).thenReturn(value);

        SelectOptionElement selectOptionElement = new SelectOptionElement(selectElement, webElementWrapper);
        SelectOptionElement result = selectOptionElement.select();

        verify(selectElement).selectByValue(value);
        assertThat(result).isSameAs(selectOptionElement);
    }

    @Test
    public void isSelected_returnsTrue() {
        when(webElement.isSelected()).thenReturn(true);

        SelectOptionElement selectOptionElement = new SelectOptionElement(selectElement, webElementWrapper);
        boolean selected = selectOptionElement.isSelected();

        verify(webElement).isSelected();
        assertThat(selected).isTrue();
    }

}