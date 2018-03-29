package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.image.BufferedImage;
import java.util.function.Function;

public class ScreenshotImageCrop {

    private final BufferedImage image;

    private ScreenshotImageCrop(BufferedImage image) {
        this.image = image;
    }

    public static Function<BufferedImage, ScreenshotImageCrop> cropping() {
        return ScreenshotImageCrop::new;
    }

    public ScreenshotImage area(ScreenshotRectangle rectangle) {
        BufferedImage subImage = image.getSubimage(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        return new ScreenshotImage(subImage);
    }

}
