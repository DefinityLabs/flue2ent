package org.definitylabs.flue2ent.plugin.screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@FunctionalInterface
public interface ScreenshotImageComparatorFunction {

    default ScreenshotImageDiff to(File referenceImageFile) {
        BufferedImage referenceImage;

        try {
            referenceImage = ImageIO.read(referenceImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return to(referenceImage);
    }


    ScreenshotImageDiff to(BufferedImage referenceImage);

}
