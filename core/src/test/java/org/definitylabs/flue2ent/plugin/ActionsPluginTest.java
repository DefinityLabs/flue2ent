package org.definitylabs.flue2ent.plugin;

import org.definitylabs.flue2ent.element.SeleniumElementCreator;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Interaction;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SeleniumElementCreator.class)
public class ActionsPluginTest {

    @Mock
    private WebDriver driver;

    @Mock
    private WebElement webElement;

    @Mock
    private Actions actions;

    private WebElementWrapper webElementWrapper;

    @Before
    public void beforeEach() throws Exception {
        PowerMockito.mockStatic(SeleniumElementCreator.class, invocationOnMock -> actions);

        webElementWrapper = new WebElementWrapper(webElement);
    }

    @Test
    public void getActions_returnsActions() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        Actions actionsFromPlugin = actionsPlugin.getActions();

        assertThat(actionsFromPlugin).isSameAs(actions);
    }

    @Test
    public void keyDown_callsKeyDown() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.keyDown("x");

        verify(actions).keyDown("x");
    }

    @Test
    public void keyDown_withElement_callsKeyDown() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.keyDown(webElementWrapper, "x");

        verify(actions).keyDown(webElement, "x");
    }

    @Test
    public void keyUp_callsKeyUp() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.keyUp("x");

        verify(actions).keyUp("x");
    }

    @Test
    public void keyUp_withElement_callsKeyUp() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.keyUp(webElementWrapper, "x");

        verify(actions).keyUp(webElement, "x");
    }

    @Test
    public void enter_callsEnter() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.enter("x");

        verify(actions).sendKeys("x");
    }

    @Test
    public void enter_withElement_callsEnter() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.enter(webElementWrapper, "x");

        verify(actions).sendKeys(webElement, "x");
    }

    @Test
    public void clickAndHold_callsClickAndHold() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.clickAndHold();

        verify(actions).clickAndHold();
    }

    @Test
    public void clickAndHold_withElement_callsClickAndHold() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.clickAndHold(webElementWrapper);

        verify(actions).clickAndHold(webElement);
    }

    @Test
    public void release_callsRelease() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.release();

        verify(actions).release();
    }

    @Test
    public void release_withElement_callsRelease() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.release(webElementWrapper);

        verify(actions).release(webElement);
    }

    @Test
    public void click_callsClick() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.click();

        verify(actions).click();
    }

    @Test
    public void click_withElement_callsClick() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.click(webElementWrapper);

        verify(actions).click(webElement);
    }

    @Test
    public void doubleClick_callsDoubleClick() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.doubleClick();

        verify(actions).doubleClick();
    }

    @Test
    public void doubleClick_withElement_callsDoubleClick() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.doubleClick(webElementWrapper);

        verify(actions).doubleClick(webElement);
    }

    @Test
    public void moveToElement_callsMoveToElement() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.moveToElement(webElementWrapper);

        verify(actions).moveToElement(webElement);
    }

    @Test
    public void moveToElement_withElementAndOffset_callsMoveToElement() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.moveToElement(webElementWrapper, 1, 2);

        verify(actions).moveToElement(webElement, 1, 2);
    }

    @Test
    public void moveByOffset_callsMoveByOffset() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.moveByOffset(1, 2);

        verify(actions).moveByOffset(1, 2);
    }

    @Test
    public void contextClick_callsContextClick() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.contextClick();

        verify(actions).contextClick();
    }

    @Test
    public void contextClick_withElement_callsContextClick() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.contextClick(webElementWrapper);

        verify(actions).contextClick(webElement);
    }

    @Test
    public void dragAndDrop_callsDragAndDrop() throws Exception {
        WebElement toWebElement = mock(WebElement.class);
        WebElementWrapper toWebElementWrapper = new WebElementWrapper(toWebElement);

        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.dragAndDrop(webElementWrapper, toWebElementWrapper);

        verify(actions).dragAndDrop(webElement, toWebElement);
    }

    @Test
    public void dragAndDropBy_callsDragAndDropBy() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.dragAndDropBy(webElementWrapper, 1, 2);

        verify(actions).dragAndDropBy(webElement, 1, 2);
    }

    @Test
    public void pause_callsPause() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.pause(1);

        verify(actions).pause(1);
    }

    @Test
    public void pause_withDuration_callsPause() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.pause(Duration.ZERO);

        verify(actions).pause(Duration.ZERO);
    }

    @Test
    public void tick_withInteraction_callsTick() throws Exception {
        Interaction interaction = mock(Interaction.class);

        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.tick(interaction);

        verify(actions).tick(interaction);
    }

    @Test
    public void tick_withAction_callsTick() throws Exception {
        Action action = mock(Action.class);

        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.tick(action);

        verify(actions).tick(action);
    }

    @Test
    public void build_callsBuild() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.build();

        verify(actions).build();
    }

    @Test
    public void perform_callsPerform() throws Exception {
        ActionsPlugin actionsPlugin = new ActionsPlugin(driver);
        actionsPlugin.perform();

        verify(actions).perform();
    }

}