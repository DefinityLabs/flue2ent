package io.github.definitylabs.flue2ent.data;

import io.github.definitylabs.flue2ent.element.FindElementBy;
import io.github.definitylabs.flue2ent.element.WebElementWrapper;

public interface SubPage {

    @FindElementBy(tagName = "h1")
    WebElementWrapper title();

}
