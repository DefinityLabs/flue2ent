package org.definitylabs.flue2ent.test;

import org.definitylabs.flue2ent.Website;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ScenarioTest {

    @Mock
    private AbstractStep stepOne;

    @Mock
    private AbstractStep stepTwo;

    @Mock
    private AbstractStep stepThree;

    @Mock
    private Website website;

    private List<Step> stepsOrder;

    @Before
    public void beforeEach() {
        stepsOrder = new ArrayList<>();

        Answer answer = invocationOnMock -> {
            stepsOrder.add((Step) invocationOnMock.getMock());
            return null;
        };

        doAnswer(answer).when(stepOne).execute();
        doAnswer(answer).when(stepTwo).execute();
        doAnswer(answer).when(stepThree).execute();
    }

    @Test
    public void executeAt_given_executesStepsInSameOrder() {
        Scenario scenario = Scenario.title("Title").given(stepOne)
                .when(stepTwo)
                .then(stepThree)
                .build();

        scenario.executeAt(website);

        verify(stepOne).execute();
        verify(stepTwo).execute();
        verify(stepThree).execute();

        assertThat(scenario.getTitle()).isEqualTo("Title");
        assertThat(stepsOrder).containsSequence(stepOne, stepTwo, stepThree);
    }

    @Test
    public void executeAt_whenGiven_executesStepsInSameOrder() {
        Scenario scenario = Scenario.title("Title").when(stepTwo)
                .given(stepOne)
                .then(stepThree)
                .build();

        scenario.executeAt(website);

        verify(stepOne).execute();
        verify(stepTwo).execute();
        verify(stepThree).execute();

        assertThat(scenario.getTitle()).isEqualTo("Title");
        assertThat(stepsOrder).containsSequence(stepOne, stepTwo, stepThree);
    }

    @Test
    public void executeAt_when_executesStepsInSameOrder() {
        Scenario scenario = Scenario.title("Title").when(stepOne)
                .then(stepThree)
                .build();

        scenario.executeAt(website);

        verify(stepOne).execute();
        verify(stepThree).execute();

        assertThat(scenario.getTitle()).isEqualTo("Title");
        assertThat(stepsOrder).containsSequence(stepOne, stepThree);
    }

    @Test
    public void executeAt_and_executesStepsInSameOrder() {
        Scenario scenario = Scenario.title("Title").given(stepOne).and(stepOne)
                .when(stepTwo).and(stepThree)
                .then(stepThree).and(stepTwo)
                .build();

        scenario.executeAt(website);

        verify(stepOne, times(2)).execute();
        verify(stepTwo, times(2)).execute();
        verify(stepThree, times(2)).execute();

        assertThat(scenario.getTitle()).isEqualTo("Title");
        assertThat(stepsOrder).containsSequence(stepOne, stepOne, stepTwo, stepThree, stepThree, stepTwo);
    }

}