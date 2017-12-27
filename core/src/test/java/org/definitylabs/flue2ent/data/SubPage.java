package org.definitylabs.flue2ent.data;

import org.definitylabs.flue2ent.element.FindElementBy;
import org.definitylabs.flue2ent.element.WebElementWrapper;

public interface SubPage {

    @FindElementBy(tagName = "h1")
    WebElementWrapper title();

}
