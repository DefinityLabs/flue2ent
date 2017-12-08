package io.github.definitylabs.flue2ent.element;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeleniumElementCreatorTest {

    @Mock
    private WebElement webElement;

    @Test
    public void new_returnsNewInstance() {
        assertThat(new SeleniumElementCreator()).isNotNull();
    }

    @Test
    public void createSelect_returnsNewSelectInstance() {
        when(webElement.getTagName()).thenReturn("select");
        when(webElement.getAttribute("multiple")).thenReturn("false");

        Select createdSelect = SeleniumElementCreator.createSelect(webElement);

        assertThat(createdSelect).isNotNull();
    }

}