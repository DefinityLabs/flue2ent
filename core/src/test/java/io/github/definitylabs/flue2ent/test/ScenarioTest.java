package io.github.definitylabs.flue2ent.test;

import io.github.definitylabs.flue2ent.Website;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;


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
    public void execute_executesStepsInSameOrder() {
        Scenario scenario = Scenario.startWhen(stepOne)
                .andThen(stepTwo)
                .andThen(stepThree)
                .build();

        scenario.executeAt(website);

        verify(stepOne).execute();
        verify(stepTwo).execute();
        verify(stepThree).execute();

        assertThat(stepsOrder).containsSequence(stepOne, stepTwo, stepThree);
    }

}