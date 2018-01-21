package org.definitylabs.flue2ent.data;

import org.definitylabs.flue2ent.element.FindElementBy;
import org.definitylabs.flue2ent.element.Param;
import org.definitylabs.flue2ent.element.WebElementDecorator;
import org.definitylabs.flue2ent.element.WebElementWrapper;
import org.definitylabs.flue2ent.element.list.SelectElement;
import org.definitylabs.flue2ent.element.table.TableElement;
import org.definitylabs.flue2ent.page.PageObjectRef;

import java.util.Date;
import java.util.List;

public interface MyPage {

    @FindElementBy(id = "name")
    String getName();

    @FindElementBy(placeholder = "Email")
    WebElementWrapper email();

    @FindElementBy(name = "age")
    WebElementWrapper age();

    @FindElementBy(css = ".birthday")
    WebElementWrapper birthday();

    @FindElementBy(className = "birthday", andGetAttribute = "value")
    String getBirthday();

    @FindElementBy(xpath = "//input[name()='birthday']", andGetAttribute = "value")
    Date getBirthDate();

    @FindElementBy
    String undefined();

    @FindElementBy(tagName = "table")
    TableElement results();

    @FindElementBy(tagName = "select")
    List<SelectElement> selects();

    @FindElementBy(tagName = "a")
    List<String> links();

    @FindElementBy(linkText = "link")
    WebElementDecorator failedLink();

    @FindElementBy(button = "Submit")
    WebElementWrapper submit();

    @FindElementBy(buttonContaining = "Confirm")
    WebElementWrapper confirm();

    @FindElementBy(partialLinkText = "Address")
    WebElementWrapper address();

    @FindElementBy(label = "Login")
    WebElementWrapper login();

    @FindElementBy(labelContaining = "Password or PIN")
    WebElementWrapper password();

    @FindElementBy(value = "MALE")
    WebElementWrapper genderMale();

    default WebElementWrapper male() {
        return genderMale();
    }

    @FindElementBy(css = ".{className}:nth-child({index})")
    WebElementWrapper elementByClassNameAndIndex(@Param("className") String className, @Param("index") int index);

    @FindElementBy(css = ".{className}")
    WebElementWrapper elementByClassName(String className);

    @PageObjectRef
    SubPage subPage();

    String nothing();

}
