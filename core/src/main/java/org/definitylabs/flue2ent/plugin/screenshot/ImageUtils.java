package org.definitylabs.flue2ent.plugin.screenshot;

import java.awt.image.BufferedImage;

class ImageUtils {

    private ImageUtils() {

    }

    static BufferedImage createImage(int width, int height, int type) {
        return new BufferedImage(width, height, type);
    }

}
