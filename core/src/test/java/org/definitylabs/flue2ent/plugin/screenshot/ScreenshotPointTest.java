package org.definitylabs.flue2ent.plugin.screenshot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ScreenshotPointTest {

    @Test
    public void getX_returnX() {
        ScreenshotPoint point = new ScreenshotPoint(5, 10);

        assertThat(point.getX()).isEqualTo(5);
    }

    @Test
    public void getY_returnY() {
        ScreenshotPoint point = new ScreenshotPoint(5, 10);

        assertThat(point.getY()).isEqualTo(10);
    }

}