package org.definitylabs.flue2ent.plugin;

import org.definitylabs.flue2ent.Website;
import org.openqa.selenium.Dimension;

public class WindowPlugin implements WebsitePlugin {

    private final Website website;

    public WindowPlugin(Website website) {
        this.website = website;
    }

    public Website fullscreen() {
        website.getDriver().manage().window().fullscreen();
        return website;
    }

    public Website maximize() {
        website.getDriver().manage().window().maximize();
        return website;
    }

    public Website size(int width, int height) {
        return size(new Dimension(width, height));
    }

    public Website size(Dimension size) {
        website.getDriver().manage().window().setSize(size);
        return website;
    }

}
