package org.definitylabs.flue2ent.plugin.screenshot;

import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.openqa.selenium.*;

import java.awt.Point;
import java.awt.Rectangle;

public class ScreenshotRectangle {
    private final Rectangle rectangle;

    private ScreenshotRectangle(int x, int y, int width, int height) {
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public static ScreenshotRectangle rectangle(int x, int y, int width, int height) {
        return new ScreenshotRectangle(x, y, width, height);
    }

    public static ScreenshotRectangle element(WebElementWrapper webElementWrapper) {
        return element(webElementWrapper.webElement());
    }

    public static ScreenshotRectangle element(WebElement webElement) {
        org.openqa.selenium.Point location = webElement.getLocation();
        org.openqa.selenium.Dimension size = webElement.getSize();
        return new ScreenshotRectangle(location.getX(), location.getY(), size.getWidth(), size.getHeight());
    }

    public int getX() {
        return rectangle.x;
    }

    public int getY() {
        return rectangle.y;
    }

    public int getWidth() {
        return rectangle.width;
    }

    public int getHeight() {
        return rectangle.height;
    }

    public boolean contains(ScreenshotPoint point) {
        return rectangle.contains(new Point(point.getX(), point.getY()));
    }
}
