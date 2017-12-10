package io.github.definitylabs.flue2ent.test;

import io.github.definitylabs.flue2ent.Website;

import java.util.ArrayList;
import java.util.List;

public final class Scenario {

    private final List<Step> steps;

    private Scenario(List<Step> steps) {
        this.steps = steps;
    }

    public static ScenarioBuilder startWhen(Step step) {
        return new ScenarioBuilder().andThen(step);
    }

    public final void executeAt(Website website) {
        steps.forEach(step -> step.at(website).execute());
    }

    public static final class ScenarioBuilder {

        private final List<Step> steps = new ArrayList<>();

        private ScenarioBuilder() {

        }

        public final ScenarioBuilder andThen(Step step) {
            this.steps.add(step);
            return this;
        }

        public final Scenario build() {
            return new Scenario(steps);
        }

    }

}
