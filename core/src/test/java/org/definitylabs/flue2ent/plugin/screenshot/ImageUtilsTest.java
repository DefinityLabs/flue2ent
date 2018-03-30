package org.definitylabs.flue2ent.plugin.screenshot;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.definitylabs.flue2ent.plugin.screenshot.ImageUtils.createImage;

public class ImageUtilsTest {

    @Test
    public void createImage_returnsBufferedImage() {
        BufferedImage image = createImage(10, 20, BufferedImage.TYPE_INT_RGB);

        assertThat(image.getWidth()).isEqualTo(10);
        assertThat(image.getHeight()).isEqualTo(20);
        assertThat(image.getType()).isEqualTo(BufferedImage.TYPE_INT_RGB);
    }

}