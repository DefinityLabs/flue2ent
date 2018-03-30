package org.definitylabs.flue2ent.plugin.screenshot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScreenshotImagePixelComparatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void compare_whenTheImagesAreEqual_returnsTrue() {
        ScreenshotImageComparator comparator = ScreenshotImagePixelComparator.pixelByPixel();

        BufferedImage referenceImage = mock(BufferedImage.class);
        when(referenceImage.getWidth()).thenReturn(10);
        when(referenceImage.getHeight()).thenReturn(10);
        when(referenceImage.getRGB(anyInt(), anyInt())).thenReturn(0);

        BufferedImage image = mock(BufferedImage.class);
        when(image.getWidth()).thenReturn(10);
        when(image.getHeight()).thenReturn(10);
        when(referenceImage.getRGB(anyInt(), anyInt())).thenReturn(0);

        ScreenshotImageDiff diff = comparator.compare(referenceImage, image);
        assertThat(diff.isEqual()).isTrue();
    }

    @Test
    public void compare_whenWidthIsNotSame_throwsRuntimeException() {
        ScreenshotImageComparator comparator = ScreenshotImagePixelComparator.pixelByPixel();

        BufferedImage referenceImage = mock(BufferedImage.class);
        when(referenceImage.getWidth()).thenReturn(10);
        when(referenceImage.getHeight()).thenReturn(10);

        BufferedImage image = mock(BufferedImage.class);
        when(image.getWidth()).thenReturn(5);
        when(image.getHeight()).thenReturn(10);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("The screenshot size is not the same");

        comparator.compare(referenceImage, image);
    }

    @Test
    public void compare_whenHeightIsNotSame_throwsRuntimeException() {
        ScreenshotImageComparator comparator = ScreenshotImagePixelComparator.pixelByPixel();

        BufferedImage referenceImage = mock(BufferedImage.class);
        when(referenceImage.getWidth()).thenReturn(10);
        when(referenceImage.getHeight()).thenReturn(10);

        BufferedImage image = mock(BufferedImage.class);
        when(image.getWidth()).thenReturn(10);
        when(image.getHeight()).thenReturn(5);

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("The screenshot size is not the same");

        comparator.compare(referenceImage, image);
    }

    @Test
    public void compare_whenTheImagesAreNotEqual_returnsFalse() {
        ScreenshotImageComparator comparator = ScreenshotImagePixelComparator.pixelByPixel();

        BufferedImage referenceImage = mock(BufferedImage.class);
        when(referenceImage.getWidth()).thenReturn(10);
        when(referenceImage.getHeight()).thenReturn(10);
        when(referenceImage.getRGB(anyInt(), anyInt())).thenReturn(0);

        BufferedImage image = mock(BufferedImage.class);
        when(image.getWidth()).thenReturn(10);
        when(image.getHeight()).thenReturn(10);
        when(referenceImage.getRGB(anyInt(), anyInt())).thenReturn(0);
        when(referenceImage.getRGB(4, 4)).thenReturn(1);
        when(referenceImage.getRGB(5, 5)).thenReturn(1);
        when(referenceImage.getRGB(6, 6)).thenReturn(1);

        ScreenshotImageDiff diff = comparator.compare(referenceImage, image);
        assertThat(diff.isEqual()).isFalse();
        assertThat(diff.getPoints()).contains(
                new ScreenshotPoint(4, 4),
                new ScreenshotPoint(5, 5),
                new ScreenshotPoint(6, 6)
        );
    }

}