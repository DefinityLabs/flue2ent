package org.definitylabs.flue2ent.plugin;

import org.definitylabs.flue2ent.Website;
import org.definitylabs.flue2ent.element.WebElementWrapper;

public class MousePlugin implements WebsitePlugin {

    private final Website website;

    public MousePlugin(Website website) {
        this.website = website;
    }

    public WebElementWrapper click(WebElementWrapper webElement) {
        website.actions().click(webElement).perform();
        return webElement;
    }

    public WebElementWrapper doubleClick(WebElementWrapper webElement) {
        website.actions().doubleClick(webElement).perform();
        return webElement;
    }

    public WebElementWrapper moveTo(WebElementWrapper webElement) {
        website.actions().moveToElement(webElement).perform();
        return webElement;
    }

    public WebElementWrapper moveAndClick(WebElementWrapper webElement) {
        website.actions().moveToElement(webElement).click().build().perform();
        return webElement;
    }

}
