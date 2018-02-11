package org.definitylabs.flue2ent.test;

public interface DataStep<D> extends Step {

    void withData(D data);

}
