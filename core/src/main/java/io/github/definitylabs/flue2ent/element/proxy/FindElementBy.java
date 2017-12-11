package io.github.definitylabs.flue2ent.element.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FindElementBy {

    String id() default "";

    String className() default "";

    String tagName() default "";

    String name() default "";

    String linkText() default "";

    String partialLinkText() default "";

    String css() default "";

    String xpath() default "";

    String placeholder() default "";

    String button() default "";

    String label() default "";

    String labelContaining() default "";

    String value() default "";

    String andGetAttribute() default "";

}
