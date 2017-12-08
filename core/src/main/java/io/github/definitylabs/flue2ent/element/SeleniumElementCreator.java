package io.github.definitylabs.flue2ent.element;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SeleniumElementCreator {

    SeleniumElementCreator() {

    }

    public static Select createSelect(WebElement webElement) {
        return new Select(webElement);
    }

}
