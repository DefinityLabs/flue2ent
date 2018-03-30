package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class ScreenshotImagePixelComparator implements ScreenshotImageComparator {

    private ScreenshotImagePixelComparator() {

    }

    public static ScreenshotImageComparator pixelByPixel() {
        return new ScreenshotImagePixelComparator();
    }

    @Override
    public ScreenshotImageDiff compare(BufferedImage referenceImage, BufferedImage image) {
        if (image.getWidth() != referenceImage.getWidth()
                || image.getHeight() != referenceImage.getHeight()) {
            throw new RuntimeException("The screenshot size is not the same");
        }

        Set<ScreenshotPoint> notSamePoints = new HashSet<>();

        for (int y = 0; y < referenceImage.getHeight(); y++) {
            for (int x = 0; x < referenceImage.getWidth(); x++) {
                if (image.getRGB(x, y) != referenceImage.getRGB(x, y)) {
                    notSamePoints.add(new ScreenshotPoint(x, y));
                }
            }
        }

        return new ScreenshotImageDiff(referenceImage, image, notSamePoints);
    }

}
