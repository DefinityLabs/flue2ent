package org.definitylabs.flue2ent.test;

import org.definitylabs.flue2ent.Website;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Scenario {

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
        steps.forEach(step -> step.at(website).execute());
    }

    public final String getTitle() {
        return title;
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
