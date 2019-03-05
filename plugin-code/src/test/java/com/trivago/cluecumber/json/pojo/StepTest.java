package com.trivago.cluecumber.json.pojo;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepTest {

    private Step step;

    @Before
    public void setup() {
        step = mock(Step.class);
        when(step.returnNameWithArguments()).thenCallRealMethod();
    }

    @Test
    public void returnNameWithArgumentsEmptyTest() {
        List<Argument> arguments = new ArrayList<>();
        when(step.getArguments()).thenReturn(arguments);
        when(step.getName()).thenReturn("");
        assertThat(step.returnNameWithArguments(), is(""));
    }

    @Test
    public void returnNameWithArgumentsNoArgumentsTest() {
        when(step.getName()).thenReturn("some name");
        assertThat(step.returnNameWithArguments(), is("some name"));
    }

    @Test
    public void returnNameWithArguments() {
        when(step.getName()).thenReturn("This is a name with an argument inside.");
        Result result = mock(Result.class);
        List<Argument> arguments = new ArrayList<>();
        Argument argument = new Argument();
        argument.setVal("argument");
        arguments.add(argument);
        when(step.getArguments()).thenReturn(arguments);
        step.setResult(result);
        assertThat(step.returnNameWithArguments(), is("This is a name with an <strong>argument</strong> inside."));
    }

    @Test
    public void totalDurationTest() {
        Step step = new Step();

        List<com.trivago.cluecumber.json.pojo.ResultMatch> beforeSteps = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch before = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result beforeResult = new Result();
        beforeResult.setDuration(1000000000L);
        before.setResult(beforeResult);
        beforeSteps.add(before);
        step.setBefore(beforeSteps);

        Result stepResult = new Result();
        stepResult.setDuration(5000000000L);
        step.setResult(stepResult);

        List<com.trivago.cluecumber.json.pojo.ResultMatch> afterSteps = new ArrayList<>();
        com.trivago.cluecumber.json.pojo.ResultMatch after = new com.trivago.cluecumber.json.pojo.ResultMatch();
        Result afterResult = new Result();
        afterResult.setDuration(2000000000L);
        after.setResult(afterResult);
        afterSteps.add(after);
        step.setAfter(afterSteps);

        MatcherAssert.assertThat(step.getTotalDuration(), is(8000000000L));
        MatcherAssert.assertThat(step.returnTotalDurationString(), is("0m 08s 000ms"));
    }

}