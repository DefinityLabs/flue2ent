package io.github.definitylabs.flue2ent.test;

import io.github.definitylabs.flue2ent.Website;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AbstractStepTest {

    @Mock
    private Website website;

    private AbstractStep step;

    @Before
    public void beforeEach() {
        step = new AbstractStep() {
            @Override
            public void execute() {

            }
        };
    }

    @Test
    public void at_setsWebsite() {
        Step result = step.at(website);

        assertThat(step.website()).isSameAs(website);
        assertThat(result).isSameAs(step);
    }

}