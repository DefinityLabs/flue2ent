package org.definitylabs.flue2ent;

import org.openqa.selenium.Dimension;

public enum Device {

    IPHONE_SE("iPhone SE", 320, 568),
    IPHONE("iPhone", 375, 667),
    IPHONE_PLUS("iPhone Plus", 414, 736),
    IPAD_MINI("iPad Mini (7.9\")", 768, 1024),
    IPAD("iPad (10.5\")", 834, 1112),
    IPAD_PRO("iPad Pro (12.9\")", 1024, 1366),
    DESKTOP_800_X_600("800 x 600", 800, 600),
    DESKTOP_1366_X_768("1366 x 768", 1366, 768),
    DESKTOP_1920_X_1080("1920 x 1080", 1920, 1080);

    private final String name;
    private final Dimension portraitSize;
    private final Dimension landscapeSize;

    Device(String name, int width, int height) {
        this.name = name;
        this.portraitSize = new Dimension(width, height);
        this.landscapeSize = new Dimension(height, width);
    }

    public String getName() {
        return name;
    }

    public Dimension portrait() {
        return portraitSize;
    }

    public Dimension landscape() {
        return landscapeSize;
    }

    @Override
    public String toString() {
        return name + " " + portraitSize.toString();
    }

}
