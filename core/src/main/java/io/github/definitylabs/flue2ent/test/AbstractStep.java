package io.github.definitylabs.flue2ent.test;

import io.github.definitylabs.flue2ent.Website;

public abstract class AbstractStep implements Step {

    private Website website;

    @Override
    public final Step at(Website website) {
        this.website = website;
        return this;
    }

    protected final Website website() {
        return website;
    }

}
