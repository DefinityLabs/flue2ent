package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class ScreenshotImageResize {

    private final BufferedImage image;

    private ScreenshotImageResize(BufferedImage image) {
        this.image = image;
    }

    public static Function<BufferedImage, ScreenshotImageResize> size() {
        return ScreenshotImageResize::new;
    }

    public ScreenshotImage to(int percent) {
        int width = image.getWidth() * (percent / 100);
        int height = image.getHeight() * (percent / 100);

        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return new ScreenshotImage(resizedImage);
    }

}
