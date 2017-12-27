package org.definitylabs.flue2ent.data;

import org.definitylabs.flue2ent.element.FindElementBy;
import org.definitylabs.flue2ent.element.Param;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.definitylabs.flue2ent.element.simple.SimpleWebElementDecorator;

import java.util.List;

public interface FakeTableElement extends SimpleWebElementDecorator {

    @FindElementBy(className = "row")
    List<WebElementWrapper> rows();

    @FindElementBy(css = ".header .row:nth-child({index})")
    WebElementWrapper headerRow(@Param("index") int index);

    WebElementWrapper webElement(int index);

    WebElementWrapper undefined();

}
