package io.github.definitylabs.flue2ent.element.list;

import io.github.definitylabs.flue2ent.element.SeleniumElementCreator;
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
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SeleniumElementCreator.class)
public class SelectElementTest {

    @Mock
    private WebElement webElement;

    private Select select;

    @Before
    public void beforeEach() throws Exception {
        select = mock(Select.class);
        PowerMockito.whenNew(Select.class).withArguments(webElement).thenReturn(select);
    }

    @Test
    public void isMultiple_callsIsMultiple() {
        SelectElement selectElement = new SelectElement(webElement);
        when(select.isMultiple()).thenReturn(true);

        boolean multiple = selectElement.isMultiple();

        verify(select).isMultiple();
        assertThat(multiple).isTrue();
    }

    @Test
    public void selectedItems_returnsSelectedItemsList() {
        WebElement optionOne = mock(WebElement.class);
        when(optionOne.isSelected()).thenReturn(true);

        WebElement optionTwo = mock(WebElement.class);
        when(optionTwo.isSelected()).thenReturn(true);

        WebElement optionThree = mock(WebElement.class);
        when(optionThree.isSelected()).thenReturn(false);

        when(webElement.findElements(By.tagName("option"))).thenReturn(Arrays.asList(optionOne, optionTwo, optionThree));

        SelectElement selectElement = new SelectElement(webElement);
        List<SelectOptionElement> optionElements = selectElement.selectedItems();

        assertThat(optionElements).hasSize(2);
    }

    @Test
    public void selectedItem_returnsFirstSelectedItem() {
        WebElement optionOne = mock(WebElement.class);
        when(optionOne.isSelected()).thenReturn(true);

        WebElement optionTwo = mock(WebElement.class);
        when(optionTwo.isSelected()).thenReturn(true);

        when(webElement.findElements(By.tagName("option"))).thenReturn(Arrays.asList(optionOne, optionTwo));

        SelectElement selectElement = new SelectElement(webElement);
        SelectOptionElement optionElement = selectElement.selectedItem();

        assertThat(optionElement.webElement().webElement()).isSameAs(optionOne);
    }

    @Test
    public void selectByVisibleText_callsSelectByVisibleText() {
        String text = "text";

        SelectElement selectElement = new SelectElement(webElement);
        SelectElement result = selectElement.selectByVisibleText(text);

        verify(select).selectByVisibleText(text);
        assertThat(result).isSameAs(selectElement);
    }

    @Test
    public void selectByIndex_callsSelectByIndex() {
        int index = 1;

        SelectElement selectElement = new SelectElement(webElement);
        SelectElement result = selectElement.selectByIndex(index);

        verify(select).selectByIndex(index);
        assertThat(result).isSameAs(selectElement);
    }

    @Test
    public void selectByValue_callsSelectByValue() {
        String value = "value";

        SelectElement selectElement = new SelectElement(webElement);
        SelectElement result = selectElement.selectByValue(value);

        verify(select).selectByValue(value);
        assertThat(result).isSameAs(selectElement);
    }

    @Test
    public void deselectAll_callsDeselectAll() {
        SelectElement selectElement = new SelectElement(webElement);
        SelectElement result = selectElement.deselectAll();

        verify(select).deselectAll();
        assertThat(result).isSameAs(selectElement);
    }

    @Test
    public void deselectByValue_callsDeselectByValue() {
        String value = "value";

        SelectElement selectElement = new SelectElement(webElement);
        SelectElement result = selectElement.deselectByValue(value);

        verify(select).deselectByValue(value);
        assertThat(result).isSameAs(selectElement);
    }

    @Test
    public void deselectByIndex_callsDeselectByIndex() {
        int index = 1;

        SelectElement selectElement = new SelectElement(webElement);
        SelectElement result = selectElement.deselectByIndex(index);

        verify(select).deselectByIndex(index);
        assertThat(result).isSameAs(selectElement);
    }

    @Test
    public void deselectByVisibleText_callsDeselectByVisibleText() {
        String text = "text";

        SelectElement selectElement = new SelectElement(webElement);
        SelectElement result = selectElement.deselectByVisibleText(text);

        verify(select).deselectByVisibleText(text);
        assertThat(result).isSameAs(selectElement);
    }

}