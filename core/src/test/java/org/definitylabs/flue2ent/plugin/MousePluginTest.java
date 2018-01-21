package org.definitylabs.flue2ent.plugin;

import org.definitylabs.flue2ent.Website;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MousePluginTest {

    @Mock
    private Website website;

    @Mock
    private WebElement webElement;

    @Mock
    private Action action;

    @Mock
    private ActionsPlugin actionsPlugin;

    private WebElementWrapper webElementWrapper;

    @Before
    public void beforeEach() {
        when(website.actions()).thenReturn(actionsPlugin);
        when(actionsPlugin.click(any())).thenReturn(actionsPlugin);
        when(actionsPlugin.moveToElement(any())).thenReturn(actionsPlugin);
        when(actionsPlugin.doubleClick(any())).thenReturn(actionsPlugin);
        when(actionsPlugin.build()).thenReturn(action);
        when(actionsPlugin.click()).thenReturn(actionsPlugin);
        webElementWrapper = new WebElementWrapper(webElement);
    }

    @Test
    public void click_callsClick() throws Exception {
        MousePlugin mousePlugin = new MousePlugin(website);
        mousePlugin.click(webElementWrapper);

        verify(actionsPlugin).click(webElementWrapper);
        verify(actionsPlugin).perform();
    }

    @Test
    public void doubleClick_callsDoubleClick() throws Exception {
        MousePlugin mousePlugin = new MousePlugin(website);
        mousePlugin.doubleClick(webElementWrapper);

        verify(actionsPlugin).doubleClick(webElementWrapper);
        verify(actionsPlugin).perform();
    }

    @Test
    public void moveTo_callsMoveTo() throws Exception {
        MousePlugin mousePlugin = new MousePlugin(website);
        mousePlugin.moveTo(webElementWrapper);

        verify(actionsPlugin).moveToElement(webElementWrapper);
        verify(actionsPlugin).perform();
    }

    @Test
    public void moveAndClick_callsMoveAndClick() throws Exception {
        MousePlugin mousePlugin = new MousePlugin(website);
        mousePlugin.moveAndClick(webElementWrapper);

        verify(actionsPlugin).moveToElement(webElementWrapper);
        verify(actionsPlugin).click();
        verify(actionsPlugin).build();
        verify(action).perform();
    }

}