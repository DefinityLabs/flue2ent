package org.definitylabs.flue2ent.plugin.screenshot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageUtils.class)
public class ScreenshotImageResizeTest {

    @Test
    public void to_returnsScreenshotImageResized() throws Exception {
        BufferedImage image = mock(BufferedImage.class);
        when(image.getWidth()).thenReturn(200);
        when(image.getHeight()).thenReturn(100);
        when(image.getType()).thenReturn(BufferedImage.TYPE_INT_RGB);

        Function<BufferedImage, ScreenshotImageResize> size = ScreenshotImageResize.size();
        ScreenshotImageResize screenshotImageResize = size.apply(image);

        BufferedImage resizedImage = PowerMockito.mock(BufferedImage.class);

        Graphics2D g = mock(Graphics2D.class);
        when(resizedImage.createGraphics()).thenReturn(g);

        PowerMockito.mockStatic(ImageUtils.class);
        PowerMockito.when(ImageUtils.class, "createImage", 100, 50, BufferedImage.TYPE_INT_RGB).thenReturn(resizedImage);

        ScreenshotImage screenshotImage = screenshotImageResize.to(50);

        verify(g).drawImage(image, 0, 0, 100, 50, null);
        verify(g).dispose();

        assertThat(screenshotImage).isEqualTo(new ScreenshotImage(resizedImage));
    }

}