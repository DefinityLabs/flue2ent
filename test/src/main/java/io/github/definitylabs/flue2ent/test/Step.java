package io.github.definitylabs.flue2ent.test;

import io.github.definitylabs.flue2ent.Website;

public interface Step {

    Step at(Website website);

    void execute();

}
