package org.definitylabs.flue2ent.element.list;

import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListItemElementTest {

    @Test
    public void text_callsWebElementText() {
        AbstractListElement<? extends ListItemElement> listElement = mock(AbstractListElement.class);
        WebElement webElement = mock(WebElement.class);

        String elementText = "element text";
        when(webElement.getText()).thenReturn(elementText);

        ListItemElement listItemElement = new ListItemElement(listElement, new WebElementWrapper(webElement));

        String text = listItemElement.text();

        assertThat(text).isEqualTo(elementText);
    }

}