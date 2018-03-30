package org.definitylabs.flue2ent.plugin.screenshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.definitylabs.flue2ent.plugin.screenshot.ScreenshotRectangle.rectangle;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ScreenshotImageHighlighterFactory.class, ImageUtils.class})
public class ScreenshotImageDiffTest {

    @Mock
    private BufferedImage referenceImage;

    @Mock
    private BufferedImage image;

    @Before
    public void beforeEach() {
        when(referenceImage.getWidth()).thenReturn(10);
        when(referenceImage.getHeight()).thenReturn(10);
        when(referenceImage.getType()).thenReturn(BufferedImage.TYPE_INT_RGB);
        when(image.getWidth()).thenReturn(10);
        when(image.getHeight()).thenReturn(10);
        when(image.getType()).thenReturn(BufferedImage.TYPE_INT_RGB);
    }

    @Test
    public void getPoints_returnsPoints() {
        HashSet<ScreenshotPoint> notSamePoints = new HashSet<>();

        ScreenshotPoint pointOne = new ScreenshotPoint(2, 4);
        notSamePoints.add(pointOne);

        ScreenshotPoint pointTwo = new ScreenshotPoint(3, 5);
        notSamePoints.add(pointTwo);

        ScreenshotImageDiff diff = new ScreenshotImageDiff(referenceImage, image, notSamePoints);

        assertThat(diff.getPoints()).contains(pointTwo, pointTwo);
    }

    @Test
    public void isEqual_whenThereArePoints_returnsFalse() {
        HashSet<ScreenshotPoint> notSamePoints = new HashSet<>();

        ScreenshotPoint pointOne = new ScreenshotPoint(2, 4);
        notSamePoints.add(pointOne);

        ScreenshotPoint pointTwo = new ScreenshotPoint(3, 5);
        notSamePoints.add(pointTwo);

        ScreenshotImageDiff diff = new ScreenshotImageDiff(referenceImage, image, notSamePoints);

        assertThat(diff.isEqual()).isFalse();
    }

    @Test
    public void isEqual_whenThereAreNoPoints_returnsTrue() {
        HashSet<ScreenshotPoint> notSamePoints = new HashSet<>();

        ScreenshotImageDiff diff = new ScreenshotImageDiff(referenceImage, image, notSamePoints);

        assertThat(diff.isEqual()).isTrue();
    }

    @Test
    public void ignoring_whenThereArePoints_returnsFalse() {
        HashSet<ScreenshotPoint> notSamePoints = new HashSet<>();

        ScreenshotPoint pointOne = new ScreenshotPoint(2, 2);
        notSamePoints.add(pointOne);

        ScreenshotPoint pointTwo = new ScreenshotPoint(5, 5);
        notSamePoints.add(pointTwo);

        ScreenshotImageDiff diff = new ScreenshotImageDiff(referenceImage, image, notSamePoints);
        ScreenshotImageDiff diffIgnoringRectangle = diff.ignoring(rectangle(4, 4, 4, 4));

        assertThat(diffIgnoringRectangle.getPoints()).containsOnly(pointOne);
    }

    @Test
    public void image_whenThereArePoints_returnsFalse() throws Exception {
        HashSet<ScreenshotPoint> notSamePoints = new HashSet<>();

        BufferedImage diffImage = PowerMockito.mock(BufferedImage.class);
        PowerMockito.mockStatic(ImageUtils.class);
        PowerMockito.when(ImageUtils.class, "createImage", 10, 10, BufferedImage.TYPE_INT_RGB).thenReturn(diffImage);

        ScreenshotImageHighlighter highlighter = mock(ScreenshotImageHighlighter.class);

        PowerMockito.mockStatic(ScreenshotImageHighlighterFactory.class);
        PowerMockito.when(ScreenshotImageHighlighterFactory.class, "highlightingArea").thenReturn(highlighter);

        Graphics2D g = mock(Graphics2D.class);
        when(diffImage.createGraphics()).thenReturn(g);

        ScreenshotImageDiff diff = new ScreenshotImageDiff(referenceImage, image, notSamePoints);

        ScreenshotImage screenshotImage = diff.image();

        verify(g).drawImage(image, 0, 0, 10, 10, null);
        verify(highlighter).highlight(g, notSamePoints);
        verify(g).dispose();

        assertThat(screenshotImage).isEqualTo(new ScreenshotImage(diffImage));
    }

}