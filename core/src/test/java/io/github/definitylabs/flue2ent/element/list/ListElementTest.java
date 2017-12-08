package io.github.definitylabs.flue2ent.element.list;

import io.github.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ListElementTest {

    @Mock
    private WebElement webElement;

    @Test
    public void new_returnsListElement() {
        ListElement listElement = new ListElement(webElement);

        assertThat(listElement.byItem).isEqualTo(By.tagName("li"));
    }

    @Test
    public void createListItem_returnsNewListItemElement() {
        ListElement listElement = new ListElement(webElement);

        WebElement webElementItem = mock(WebElement.class);
        WebElementWrapper webElementWrapper = new WebElementWrapper(webElementItem);

        ListItemElement listItem = listElement.createListItem(webElementWrapper);
        assertThat(listItem.webElement()).isSameAs(webElementWrapper);
    }

}