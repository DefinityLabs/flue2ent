package org.definitylabs.flue2ent.element;

import org.openqa.selenium.By;

public final class ExtendedBy {

    private ExtendedBy() {

    }

    public static By byValue(String text) {
        return By.xpath("//input[@value='" + text + "']");
    }

    public static By byLabel(String text) {
        return By.xpath("//input[@id=(//label[text()='" + text + "']/@for)]");
    }

    public static By byLabelContaining(String text) {
        return By.xpath("//input[@id=(//label[contains(text(), '" + text + "')]/@for)]");
    }

    public static By byPlaceholder(String text) {
        return By.xpath("//input[@placeholder='" + text + "']");
    }

    public static By byButton(String text) {
        return By.xpath("//button[text()='" + text + "']");
    }

    public static By byButtonContaining(String text) {
        return By.xpath("//button[contains()='" + text + "']");
    }

}
