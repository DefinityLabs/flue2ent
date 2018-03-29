package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.image.BufferedImage;

public interface ScreenshotImageComparator {

    ScreenshotImageDiff compare(BufferedImage referenceImage, BufferedImage image);

}
