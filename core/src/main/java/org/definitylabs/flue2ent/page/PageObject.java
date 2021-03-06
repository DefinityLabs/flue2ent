package org.definitylabs.flue2ent.page;

import org.definitylabs.flue2ent.Website;
import org.openqa.selenium.WebDriver;

public abstract class PageObject<T> {

    private Website website;

    protected PageObject() {

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

    @SuppressWarnings("unchecked")
    public T getResponse() {
        return (T) this;
    }

    protected void init() {

    }

}
