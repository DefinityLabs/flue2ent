package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

import static org.definitylabs.flue2ent.plugin.screenshot.ImageUtils.createImage;

public class ScreenshotImageResize {

    private final BufferedImage image;

    private ScreenshotImageResize(BufferedImage image) {
        this.image = image;
    }

    public static Function<BufferedImage, ScreenshotImageResize> size() {
        return ScreenshotImageResize::new;
    }

    public ScreenshotImage to(int percent) {
        int width = (int) (image.getWidth() * (percent / 100.0));
        int height = (int) (image.getHeight() * (percent / 100.0));

        BufferedImage resizedImage = createImage(width, height, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return new ScreenshotImage(resizedImage);
    }

}
