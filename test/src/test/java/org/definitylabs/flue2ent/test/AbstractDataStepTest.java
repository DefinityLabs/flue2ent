package org.definitylabs.flue2ent.test;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractDataStepTest {

    private AbstractDataStep<Object> step;

    @Before
    public void beforeEach() {
        step = new AbstractDataStep<Object>() {
            @Override
            public void execute() {

            }
        };
    }

    @Test
    public void withData_setsData() {
        Object data = new Object();

        step.withData(data);

        assertThat(step.data()).isSameAs(data);
    }

}