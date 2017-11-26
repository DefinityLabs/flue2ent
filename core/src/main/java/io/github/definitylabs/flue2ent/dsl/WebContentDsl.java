package io.github.definitylabs.flue2ent.dsl;

import io.github.definitylabs.flue2ent.Website;
import org.openqa.selenium.WebDriver;

public abstract class WebContentDsl {

    private Website website;

    protected WebContentDsl() {

    }

    protected final Website website() {
        return website;
    }

    protected final WebDriver driver() {
        return website.getDriver();
    }

    public final void setWebsite(Website website) {
        this.website = website;
        init();
    }

    protected void init() {

    }

}
