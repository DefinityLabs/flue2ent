package org.definitylabs.flue2ent.test;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.definitylabs.flue2ent.Website;
import org.junit.jupiter.api.DynamicTest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public final class Scenario {

    private static final String VARIABLE_REGEX = "\\{([^}]+)}";
    private static final String CONTAINS_VARIABLE_REGEX = ".*\\{[^}]+}.*";

    private final String title;
    private final List<Step> steps;

    private Scenario(String title, List<Step> steps) {
        this.title = title;
        this.steps = steps;
    }

    public static ScenarioBuilder title(String title) {
        return new ScenarioBuilder(title);
    }

    public final void executeAt(Website website) {
        executeAt(website, null);
    }

    @SuppressWarnings("unchecked")
    public final <D> void executeAt(Website website, D data) {
        steps.forEach(step -> {
            step.at(website);
            if (step instanceof DataStep) {
                ((DataStep) step).withData(data);
            }
            step.execute();
        });
    }

    public final String getTitle() {
        return title;
    }

    public final DynamicTest test(Website website) {
        return dynamicTest(title, () -> executeAt(website));
    }

    public final <D> List<DynamicTest> test(Website website, Stream<D> data) {
        return data.map(item ->
                dynamicTest(defineTitle(title, item), () -> executeAt(website, item))
        ).collect(Collectors.toList());
    }

    private String defineTitle(String title, Object data) {
        String newTitle = title;

        newTitle = newTitle.replace("{data}", String.valueOf(data));
        newTitle = newTitle.replace("{data.", "{");

        if (newTitle.matches(CONTAINS_VARIABLE_REGEX)) {
            PropertyUtilsBean propertyUtils = new PropertyUtilsBean();

            Pattern variablePattern = Pattern.compile(VARIABLE_REGEX);
            Matcher matcher = variablePattern.matcher(newTitle);
            while (matcher.find()) {
                String name = matcher.group(1);
                try {
                    Object value = propertyUtils.getProperty(data, name);
                    newTitle = newTitle.replace(matcher.group(), String.valueOf(value));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException("Error getting property from data: data." + name, e);
                }
            }
        }

        return newTitle;
    }

    public static final class ScenarioBuilder {

        private final String title;
        private final List<Step> givenSteps = new ArrayList<>();
        private final List<Step> whenSteps = new ArrayList<>();
        private final List<Step> thenSteps = new ArrayList<>();

        private ScenarioBuilder(String title) {
            this.title = title;
        }

        public final StepGroup given(Step step) {
            givenSteps.add(step);
            return new StepGroup(givenSteps);
        }

        public final StepGroup when(Step step) {
            whenSteps.add(step);
            return new StepGroup(whenSteps);
        }

        public final StepGroup then(Step step) {
            thenSteps.add(step);
            return new StepGroup(thenSteps);
        }

        public final Scenario build() {
            return new Scenario(title, Stream.of(givenSteps, whenSteps, thenSteps)
                    .flatMap(List::stream)
                    .collect(Collectors.toList()));
        }

        public final class StepGroup {

            private final List<Step> steps;

            private StepGroup(List<Step> steps) {
                this.steps = steps;
            }

            public StepGroup and(Step step) {
                this.steps.add(step);
                return this;
            }

            public StepGroup given(Step step) {
                return ScenarioBuilder.this.given(step);
            }

            public StepGroup when(Step step) {
                return ScenarioBuilder.this.when(step);
            }

            public StepGroup then(Step step) {
                return ScenarioBuilder.this.then(step);
            }

            public Scenario build() {
                return ScenarioBuilder.this.build();
            }

        }

    }

}
