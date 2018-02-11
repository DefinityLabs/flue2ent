package org.definitylabs.flue2ent.test;

public abstract class AbstractDataStep<D> extends AbstractStep implements DataStep<D> {

    private D data;

    @Override
    public final void withData(D data) {
        this.data = data;
    }

    protected final D data() {
        return data;
    }

}
