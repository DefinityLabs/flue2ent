package io.github.definitylabs.flue2ent.element;


import io.github.definitylabs.flue2ent.data.MyPage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementLocatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void new_returnsNewInstance() {
        assertThat(new ElementLocator()).isNotNull();
    }

    @Test
    public void by_whenFindById_returnsById() throws Exception {
        Method method = getMethod("getName");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(By.id("name"));
    }

    @Test
    public void by_whenFindByPlaceholder_returnsByPlaceholder() throws Exception {
        Method method = getMethod("email");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(ExtendedBy.byPlaceholder("Email"));
    }

    @Test
    public void by_whenFindByName_returnsByName() throws Exception {
        Method method = getMethod("age");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(By.name("age"));
    }

    @Test
    public void by_whenFindByCss_returnsByCss() throws Exception {
        Method method = getMethod("birthday");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(By.cssSelector(".birthday"));
    }

    @Test
    public void by_whenFindByClassName_returnsByClassName() throws Exception {
        Method method = getMethod("getBirthday");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(By.className("birthday"));
    }

    @Test
    public void by_whenFindByXPath_returnsByXPath() throws Exception {
        Method method = getMethod("getBirthDate");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(By.xpath("//input[name()='birthday']"));
    }

    @Test
    public void by_whenFindByTagName_returnsByTagName() throws Exception {
        Method method = getMethod("results");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(By.tagName("table"));
    }

    @Test
    public void by_whenFindByLinkText_returnsByLinkText() throws Exception {
        Method method = getMethod("failedLink");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(By.linkText("link"));
    }

    @Test
    public void by_whenFindByPartialLinkText_returnsByPartialLinkText() throws Exception {
        Method method = getMethod("address");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(By.partialLinkText("Address"));
    }

    @Test
    public void by_whenFindByLabel_returnsByLabel() throws Exception {
        Method method = getMethod("login");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(ExtendedBy.byLabel("Login"));
    }

    @Test
    public void by_whenFindByLabelContaining_returnsByLabelContaining() throws Exception {
        Method method = getMethod("password");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(ExtendedBy.byLabelContaining("Password or PIN"));
    }

    @Test
    public void by_whenFindByValue_returnsByValue() throws Exception {
        Method method = getMethod("genderMale");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(ExtendedBy.byValue("MALE"));
    }

    @Test
    public void by_whenFindByCssWithParameters_returnsByCss() throws Exception {
        Method method = getMethod("elementByClassNameAndIndex", String.class, int.class);
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[]{"tr", 2});

        assertThat(by).isEqualTo(By.cssSelector(".tr:nth-child(2)"));
    }

    @Test
    public void by_whenFindByCssWithParametersWithoutAnnotation_throwsIllegalArgumentException() throws Exception {
        Method method = getMethod("elementByClassName", String.class);
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("All parameters should be annotated with @Param");

        ElementLocator.by(findElementBy, method.getParameters(), new Object[]{"tr"});
    }

    @Test
    public void by_whenFindBySubmit_returnsBySubmit() throws Exception {
        Method method = getMethod("submit");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        By by = ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);

        assertThat(by).isEqualTo(ExtendedBy.byButton("Submit"));
    }

    @Test
    public void by_withoutParameters_throwsIllegalArgumentException() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("element path is not defined");

        Method method = getMethod("undefined");
        FindElementBy findElementBy = method.getAnnotation(FindElementBy.class);

        ElementLocator.by(findElementBy, method.getParameters(), new Object[0]);
    }

    private Method getMethod(String methodName, Class<?>... types) throws Exception {
        return MyPage.class.getMethod(methodName, types);
    }

}