package org.definitylabs.flue2ent;

import org.junit.Test;
import org.openqa.selenium.Dimension;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceTest {

    @Test
    public void iphoneSE_returnsProperData() {
        String name = Device.IPHONE_SE.getName();
        Dimension landscape = Device.IPHONE_SE.landscape();
        Dimension portrait = Device.IPHONE_SE.portrait();
        String toString = Device.IPHONE_SE.toString();

        assertThat(name).isEqualTo("iPhone SE");
        assertThat(landscape.getWidth()).isEqualTo(568);
        assertThat(landscape.getHeight()).isEqualTo(320);
        assertThat(portrait.getWidth()).isEqualTo(320);
        assertThat(portrait.getHeight()).isEqualTo(568);
        assertThat(toString).isEqualTo("iPhone SE (320, 568)");
    }

    @Test
    public void iphone_returnsProperData() {
        String name = Device.IPHONE.getName();
        Dimension landscape = Device.IPHONE.landscape();
        Dimension portrait = Device.IPHONE.portrait();
        String toString = Device.IPHONE.toString();

        assertThat(name).isEqualTo("iPhone");
        assertThat(landscape.getWidth()).isEqualTo(667);
        assertThat(landscape.getHeight()).isEqualTo(375);
        assertThat(portrait.getWidth()).isEqualTo(375);
        assertThat(portrait.getHeight()).isEqualTo(667);
        assertThat(toString).isEqualTo("iPhone (375, 667)");
    }

    @Test
    public void iphonePlus_returnsProperData() {
        String name = Device.IPHONE_PLUS.getName();
        Dimension landscape = Device.IPHONE_PLUS.landscape();
        Dimension portrait = Device.IPHONE_PLUS.portrait();
        String toString = Device.IPHONE_PLUS.toString();

        assertThat(name).isEqualTo("iPhone Plus");
        assertThat(landscape.getWidth()).isEqualTo(736);
        assertThat(landscape.getHeight()).isEqualTo(414);
        assertThat(portrait.getWidth()).isEqualTo(414);
        assertThat(portrait.getHeight()).isEqualTo(736);
        assertThat(toString).isEqualTo("iPhone Plus (414, 736)");
    }

    @Test
    public void iPadMini_returnsProperData() {
        String name = Device.IPAD_MINI.getName();
        Dimension landscape = Device.IPAD_MINI.landscape();
        Dimension portrait = Device.IPAD_MINI.portrait();
        String toString = Device.IPAD_MINI.toString();

        assertThat(name).isEqualTo("iPad Mini (7.9\")");
        assertThat(landscape.getWidth()).isEqualTo(1024);
        assertThat(landscape.getHeight()).isEqualTo(768);
        assertThat(portrait.getWidth()).isEqualTo(768);
        assertThat(portrait.getHeight()).isEqualTo(1024);
        assertThat(toString).isEqualTo("iPad Mini (7.9\") (768, 1024)");
    }

    @Test
    public void iPad_returnsProperData() {
        String name = Device.IPAD.getName();
        Dimension landscape = Device.IPAD.landscape();
        Dimension portrait = Device.IPAD.portrait();
        String toString = Device.IPAD.toString();

        assertThat(name).isEqualTo("iPad (10.5\")");
        assertThat(landscape.getWidth()).isEqualTo(1112);
        assertThat(landscape.getHeight()).isEqualTo(834);
        assertThat(portrait.getWidth()).isEqualTo(834);
        assertThat(portrait.getHeight()).isEqualTo(1112);
        assertThat(toString).isEqualTo("iPad (10.5\") (834, 1112)");
    }

    @Test
    public void iPadPro_returnsProperData() {
        String name = Device.IPAD_PRO.getName();
        Dimension landscape = Device.IPAD_PRO.landscape();
        Dimension portrait = Device.IPAD_PRO.portrait();
        String toString = Device.IPAD_PRO.toString();

        assertThat(name).isEqualTo("iPad Pro (12.9\")");
        assertThat(landscape.getWidth()).isEqualTo(1366);
        assertThat(landscape.getHeight()).isEqualTo(1024);
        assertThat(portrait.getWidth()).isEqualTo(1024);
        assertThat(portrait.getHeight()).isEqualTo(1366);
        assertThat(toString).isEqualTo("iPad Pro (12.9\") (1024, 1366)");
    }

    @Test
    public void desktop800x600_returnsProperData() {
        String name = Device.DESKTOP_800_X_600.getName();
        Dimension landscape = Device.DESKTOP_800_X_600.landscape();
        Dimension portrait = Device.DESKTOP_800_X_600.portrait();
        String toString = Device.DESKTOP_800_X_600.toString();

        assertThat(name).isEqualTo("800 x 600");
        assertThat(landscape.getWidth()).isEqualTo(600);
        assertThat(landscape.getHeight()).isEqualTo(800);
        assertThat(portrait.getWidth()).isEqualTo(800);
        assertThat(portrait.getHeight()).isEqualTo(600);
        assertThat(toString).isEqualTo("800 x 600 (800, 600)");
    }

    @Test
    public void desktop1366x768_returnsProperData() {
        String name = Device.DESKTOP_1366_X_768.getName();
        Dimension landscape = Device.DESKTOP_1366_X_768.landscape();
        Dimension portrait = Device.DESKTOP_1366_X_768.portrait();
        String toString = Device.DESKTOP_1366_X_768.toString();

        assertThat(name).isEqualTo("1366 x 768");
        assertThat(landscape.getWidth()).isEqualTo(768);
        assertThat(landscape.getHeight()).isEqualTo(1366);
        assertThat(portrait.getWidth()).isEqualTo(1366);
        assertThat(portrait.getHeight()).isEqualTo(768);
        assertThat(toString).isEqualTo("1366 x 768 (1366, 768)");
    }

    @Test
    public void desktop1920x1080_returnsProperData() {
        String name = Device.DESKTOP_1920_X_1080.getName();
        Dimension landscape = Device.DESKTOP_1920_X_1080.landscape();
        Dimension portrait = Device.DESKTOP_1920_X_1080.portrait();
        String toString = Device.DESKTOP_1920_X_1080.toString();

        assertThat(name).isEqualTo("1920 x 1080");
        assertThat(landscape.getWidth()).isEqualTo(1080);
        assertThat(landscape.getHeight()).isEqualTo(1920);
        assertThat(portrait.getWidth()).isEqualTo(1920);
        assertThat(portrait.getHeight()).isEqualTo(1080);
        assertThat(toString).isEqualTo("1920 x 1080 (1920, 1080)");
    }

}