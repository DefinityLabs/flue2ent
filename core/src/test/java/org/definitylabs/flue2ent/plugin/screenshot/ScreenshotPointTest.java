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

    @Test
    public void equalsAndHashCode_areWellImplemented() {
        ScreenshotPoint point = new ScreenshotPoint(1, 1);
        assertThat(point).isEqualTo(point);
        assertThat(point).isEqualTo(new ScreenshotPoint(1, 1));
        assertThat(point).isNotEqualTo(new ScreenshotPoint(1, 2));
        assertThat(point).isNotEqualTo(null);
        assertThat(point).isNotEqualTo(new Object());

        assertThat(point.hashCode()).isEqualTo(32);
    }

}