package org.definitylabs.flue2ent.plugin.screenshot;

import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScreenshotRectangleTest {

    @Test
    public void rectangle_returnsRectangle() {
        ScreenshotRectangle rectangle = ScreenshotRectangle.rectangle(2, 4, 5, 10);

        assertThat(rectangle.getX()).isEqualTo(2);
        assertThat(rectangle.getY()).isEqualTo(4);
        assertThat(rectangle.getWidth()).isEqualTo(5);
        assertThat(rectangle.getHeight()).isEqualTo(10);
    }

    @Test
    public void element_whenElementIsWebElement_returnsRectangle() {
        WebElement webElement = mock(WebElement.class);
        when(webElement.getLocation()).thenReturn(new Point(0, 5));
        when(webElement.getSize()).thenReturn(new Dimension(100, 30));

        ScreenshotRectangle rectangle = ScreenshotRectangle.element(webElement);

        assertThat(rectangle.getX()).isEqualTo(0);
        assertThat(rectangle.getY()).isEqualTo(5);
        assertThat(rectangle.getWidth()).isEqualTo(100);
        assertThat(rectangle.getHeight()).isEqualTo(30);
    }

    @Test
    public void element_whenElementIsWrapper_returnsRectangle() {
        WebElement webElement = mock(WebElement.class);
        when(webElement.getLocation()).thenReturn(new Point(5, 10));
        when(webElement.getSize()).thenReturn(new Dimension(200, 35));

        WebElementWrapper webElementWrapper = new WebElementWrapper(webElement);

        ScreenshotRectangle rectangle = ScreenshotRectangle.element(webElementWrapper);

        assertThat(rectangle.getX()).isEqualTo(5);
        assertThat(rectangle.getY()).isEqualTo(10);
        assertThat(rectangle.getWidth()).isEqualTo(200);
        assertThat(rectangle.getHeight()).isEqualTo(35);
    }

    @Test
    public void contains_whenPointIsInsideRectangle_returnsTrue() {
        ScreenshotRectangle rectangle = ScreenshotRectangle.rectangle(2, 4, 5, 10);

        boolean contains = rectangle.contains(new ScreenshotPoint(4, 5));

        assertThat(contains).isTrue();
    }

    @Test
    public void contains_whenPointIsOutsideRectangle_returnsFalse() {
        ScreenshotRectangle rectangle = ScreenshotRectangle.rectangle(2, 4, 5, 10);

        boolean contains = rectangle.contains(new ScreenshotPoint(0, 1));

        assertThat(contains).isFalse();
    }

}