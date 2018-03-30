package org.definitylabs.flue2ent.plugin.screenshot;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.definitylabs.flue2ent.plugin.screenshot.ScreenshotRectangle.rectangle;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScreenshotImageCropTest {

    @Test
    public void area_returnsNewCroppedImage() {
        Function<BufferedImage, ScreenshotImageCrop> cropping = ScreenshotImageCrop.cropping();

        BufferedImage subImage = mock(BufferedImage.class);

        BufferedImage image = mock(BufferedImage.class);
        when(image.getSubimage(0, 0, 100, 20)).thenReturn(subImage);

        ScreenshotImageCrop crop = cropping.apply(image);
        ScreenshotImage screenshot = crop.area(rectangle(0, 0, 100, 20));

        assertThat(screenshot).isEqualTo(new ScreenshotImage(subImage));
    }

}