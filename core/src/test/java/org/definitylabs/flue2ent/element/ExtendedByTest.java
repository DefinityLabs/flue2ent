package org.definitylabs.flue2ent.element;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedByTest {

    @Test
    public void new_returnsNewInstance() {
        assertThat(new ExtendedBy()).isNotNull();
    }

    @Test
    public void byValue_returnsByXpath() {
        By byValue = ExtendedBy.byValue("value");

        assertThat(byValue).isEqualTo(By.xpath("//input[@value='value']"));
    }

    @Test
    public void byLabel_returnsByXpath() {
        By byLabel = ExtendedBy.byLabel("label");

        assertThat(byLabel).isEqualTo(By.xpath("//input[@id=(//label[text()='label']/@for)]"));
    }

    @Test
    public void byLabelContaining_returnsByXpath() {
        By byLabelContaining = ExtendedBy.byLabelContaining("label");

        assertThat(byLabelContaining).isEqualTo(By.xpath("//input[@id=(//label[contains(text(), 'label')]/@for)]"));
    }

    @Test
    public void byPlaceholder_returnsByXpath() {
        By byPlaceholder = ExtendedBy.byPlaceholder("placeholder");

        assertThat(byPlaceholder).isEqualTo(By.xpath("//input[@placeholder='placeholder']"));
    }

    @Test
    public void byButton_returnsByXpath() {
        By byButton = ExtendedBy.byButton("text");

        assertThat(byButton).isEqualTo(By.xpath("//button[text()='text']"));
    }

}