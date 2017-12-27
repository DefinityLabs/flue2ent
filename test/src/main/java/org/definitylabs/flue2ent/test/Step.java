package org.definitylabs.flue2ent.test;

import org.definitylabs.flue2ent.Website;

public interface Step {

    Step at(Website website);

    void execute();

}
