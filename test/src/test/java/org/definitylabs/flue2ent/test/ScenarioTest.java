package org.definitylabs.flue2ent.test;

import org.definitylabs.flue2ent.Website;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DynamicTest;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Website website;

    private List<Step> stepsOrder;
    @Mock
    private AbstractDataStep<Object> stepFour;

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

    @Test
    public void executeAt_withData_whenStepIsNotDataStep_callsExecute() {
        Scenario scenario = Scenario.title("Title").given(stepOne).build();

        Object data = new Object();
        scenario.executeAt(website, data);

        verify(stepOne).execute();

        assertThat(scenario.getTitle()).isEqualTo("Title");
    }

    @Test
    public void executeAt_withData_whenStepIsDataStep_callsExecute() {
        Scenario scenario = Scenario.title("Title").given(stepFour).build();

        Object data = new Object();
        scenario.executeAt(website, data);

        verify(stepFour).execute();

        assertThat(scenario.getTitle()).isEqualTo("Title");
        assertThat(stepFour.data()).isSameAs(data);
    }

    @Test
    public void test_returnsDynamicTest() throws Throwable {
        Scenario scenario = Scenario.title("Title").given(stepOne).build();
        DynamicTest test = scenario.test(website);

        assertThat(test.getDisplayName()).isEqualTo(scenario.getTitle());

        test.getExecutable().execute();

        verify(stepOne).execute();
    }

    @Test
    public void test_withData_returnsDynamicTest() throws Throwable {
        Scenario scenario = Scenario.title("Title [{data.name}]").given(stepFour).then(stepOne).build();
        DataObject dataOne = new DataObject("dataOne");
        DataObject dataTwo = new DataObject("dataTwo");
        List<DynamicTest> tests = scenario.test(website, Stream.of(dataOne, dataTwo));

        assertThat(tests).hasSize(2);
        assertThat(tests.get(0).getDisplayName()).isEqualTo("Title [dataOne]");
        assertThat(tests.get(1).getDisplayName()).isEqualTo("Title [dataTwo]");

        tests.get(0).getExecutable().execute();
        verify(stepFour).execute();
        verify(stepOne).execute();
        assertThat(stepFour.data()).isSameAs(dataOne);

        tests.get(1).getExecutable().execute();
        verify(stepFour, times(2)).execute();
        verify(stepOne, times(2)).execute();
        assertThat(stepFour.data()).isSameAs(dataTwo);
    }

    @Test
    public void test_withData_withoutVariable_returnsDynamicTest() throws Throwable {
        Scenario scenario = Scenario.title("Title").given(stepFour).then(stepOne).build();
        DataObject dataOne = new DataObject("dataOne");
        DataObject dataTwo = new DataObject("dataTwo");
        List<DynamicTest> tests = scenario.test(website, Stream.of(dataOne, dataTwo));

        assertThat(tests).hasSize(2);
        assertThat(tests.get(0).getDisplayName()).isEqualTo("Title");
        assertThat(tests.get(1).getDisplayName()).isEqualTo("Title");
    }

    @Test
    public void test_withData_whenPropertyDoesNotExists_throwsRuntimeException() {
        Scenario scenario = Scenario.title("Title [{data.wrongProperty}]").then(stepOne).build();
        DataObject dataOne = new DataObject("dataOne");

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Error getting property from data: data.wrongProperty");

        scenario.test(website, Stream.of(dataOne));
    }

    public static class DataObject {
        private final String name;

        private DataObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


}