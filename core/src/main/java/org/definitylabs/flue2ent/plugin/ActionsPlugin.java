package org.definitylabs.flue2ent.plugin;

import org.definitylabs.flue2ent.element.SeleniumElementCreator;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Interaction;

import java.time.Duration;

public class ActionsPlugin implements WebDriverPlugin {

    private final Actions actions;

    public ActionsPlugin(WebDriver driver) {
        actions = SeleniumElementCreator.createActions(driver);
    }

    public Actions getActions() {
        return actions;
    }

    public ActionsPlugin keyDown(CharSequence key) {
        actions.keyDown(key);
        return this;
    }

    public ActionsPlugin keyDown(WebElementWrapper target, CharSequence key) {
        actions.keyDown(target.webElement(), key);
        return this;
    }

    public ActionsPlugin keyUp(CharSequence key) {
        actions.keyUp(key);
        return this;
    }

    public ActionsPlugin keyUp(WebElementWrapper target, CharSequence key) {
        actions.keyUp(target.webElement(), key);
        return this;
    }

    public ActionsPlugin enter(CharSequence... keys) {
        actions.sendKeys(keys);
        return this;
    }

    public ActionsPlugin enter(WebElementWrapper target, CharSequence... keys) {
        actions.sendKeys(target.webElement(), keys);
        return this;
    }

    public ActionsPlugin clickAndHold(WebElementWrapper target) {
        actions.clickAndHold(target.webElement());
        return this;
    }

    public ActionsPlugin clickAndHold() {
        actions.clickAndHold();
        return this;
    }

    public ActionsPlugin release(WebElementWrapper target) {
        actions.release(target.webElement());
        return this;
    }

    public ActionsPlugin release() {
        actions.release();
        return this;
    }

    public ActionsPlugin click(WebElementWrapper target) {
        actions.click(target.webElement());
        return this;
    }

    public ActionsPlugin click() {
        actions.click();
        return this;
    }

    public ActionsPlugin doubleClick(WebElementWrapper target) {
        actions.doubleClick(target.webElement());
        return this;
    }

    public ActionsPlugin doubleClick() {
        actions.doubleClick();
        return this;
    }

    public ActionsPlugin moveToElement(WebElementWrapper target) {
        actions.moveToElement(target.webElement());
        return this;
    }

    public ActionsPlugin moveToElement(WebElementWrapper target, int xOffset, int yOffset) {
        actions.moveToElement(target.webElement(), xOffset, yOffset);
        return this;
    }

    public ActionsPlugin moveByOffset(int xOffset, int yOffset) {
        actions.moveByOffset(xOffset, yOffset);
        return this;
    }

    public ActionsPlugin contextClick(WebElementWrapper target) {
        actions.contextClick(target.webElement());
        return this;
    }

    public ActionsPlugin contextClick() {
        actions.contextClick();
        return this;
    }

    public ActionsPlugin dragAndDrop(WebElementWrapper source, WebElementWrapper target) {
        actions.dragAndDrop(source.webElement(), target.webElement());
        return this;
    }

    public ActionsPlugin dragAndDropBy(WebElementWrapper source, int xOffset, int yOffset) {
        actions.dragAndDropBy(source.webElement(), xOffset, yOffset);
        return this;
    }

    public ActionsPlugin pause(long pause) {
        actions.pause(pause);
        return this;
    }

    public ActionsPlugin pause(Duration duration) {
        actions.pause(duration);
        return this;
    }

    public ActionsPlugin tick(Interaction... actions) {
        this.actions.tick(actions);
        return this;
    }

    public ActionsPlugin tick(Action action) {
        actions.tick(action);
        return this;
    }

    public Action build() {
        return actions.build();
    }

    public void perform() {
        actions.perform();
    }

}
