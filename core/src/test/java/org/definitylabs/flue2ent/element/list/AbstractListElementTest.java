package org.definitylabs.flue2ent.element.list;

import org.assertj.core.api.Assertions;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Before;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractListElementTest {

    @Mock
    private WebElement webElement;

    private By byItem;
    private AbstractListElement<ListItemElement> list;

    @Before
    public void beforeEach() {
        byItem = By.tagName("li");
        list = new AbstractListElement<ListItemElement>(new WebElementWrapper(webElement), byItem) {
            @Override
            protected ListItemElement createListItem(WebElementWrapper webElementWrapper) {
                return new ListItemElement(this, webElementWrapper);
            }
        };
    }

    @Test
    public void items_returnsItemsList() {
        WebElement itemOne = mock(WebElement.class);
        WebElement itemTwo = mock(WebElement.class);

        when(webElement.findElements(byItem)).thenReturn(Arrays.asList(itemOne, itemTwo));

        List<ListItemElement> items = list.items();

        assertThat(items).hasSize(2);
        assertThat(items.get(0)).isInstanceOf(ListItemElement.class);
        Assertions.assertThat(items.get(0).webElement().webElement()).isSameAs(itemOne);
        assertThat(items.get(1)).isInstanceOf(ListItemElement.class);
        Assertions.assertThat(items.get(1).webElement().webElement()).isSameAs(itemTwo);
    }

    @Test
    public void contains_returnsTrue() {
        WebElement itemOne = mock(WebElement.class);
        when(itemOne.getText()).thenReturn("text");

        WebElement itemTwo = mock(WebElement.class);
        when(itemTwo.getText()).thenReturn("other");

        when(webElement.findElements(byItem)).thenReturn(Arrays.asList(itemOne, itemTwo));

        boolean contains = list.contains(item -> item.webElement().text().equals("text"));

        assertThat(contains).isTrue();
    }

    @Test
    public void find_returnsFirstItem() {
        WebElement itemOne = mock(WebElement.class);
        when(itemOne.getText()).thenReturn("text");

        WebElement itemTwo = mock(WebElement.class);
        when(itemTwo.getText()).thenReturn("other");

        when(webElement.findElements(byItem)).thenReturn(Arrays.asList(itemOne, itemTwo));

        ListItemElement listItemElement = list.find(item -> item.webElement().text().equals("text"));

        Assertions.assertThat(listItemElement.webElement().webElement()).isSameAs(itemOne);
    }

    @Test
    public void findAll_returnsFirstAndSecondItem() {
        WebElement itemOne = mock(WebElement.class);
        when(itemOne.getText()).thenReturn("text");

        WebElement itemTwo = mock(WebElement.class);
        when(itemTwo.getText()).thenReturn("text");

        WebElement itemThree = mock(WebElement.class);
        when(itemThree.getText()).thenReturn("other");

        when(webElement.findElements(byItem)).thenReturn(Arrays.asList(itemOne, itemTwo, itemThree));

        List<ListItemElement> listItems = list.findAll(item -> item.webElement().text().equals("text"));

        assertThat(listItems).hasSize(2);
        Assertions.assertThat(listItems.get(0).webElement().webElement()).isSameAs(itemOne);
        Assertions.assertThat(listItems.get(1).webElement().webElement()).isSameAs(itemTwo);
    }

    @Test
    public void hasItem_returnsSupplier() {
        WebElement itemOne = mock(WebElement.class);
        when(itemOne.getText()).thenReturn("text");

        WebElement itemTwo = mock(WebElement.class);
        when(itemTwo.getText()).thenReturn("other");

        when(webElement.findElements(byItem)).thenReturn(Arrays.asList(itemOne, itemTwo));

        Supplier<Boolean> hasItem = list.hasItem(item -> item.webElement().text().equals("text"));

        Boolean contains = hasItem.get();

        assertThat(contains).isTrue();
    }

}