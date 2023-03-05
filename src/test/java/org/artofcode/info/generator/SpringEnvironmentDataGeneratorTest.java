package org.artofcode.info.generator;

import org.artofcode.info.config.TestConfig;
import org.artofcode.info.step.args.StepArgProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
 * Copyright (c) 2023.  steti93
 */
@SpringJUnitConfig(TestConfig.class)
@TestPropertySource(properties = {"test.value.from.property=3025"})
public class SpringEnvironmentDataGeneratorTest {

    @Autowired
    private StepArgProcessor defaultStepArgProcessor;


    @Test
    void springSPELConcatTest() {
        String testValue = "${'Hello World'.concat('!')}";
        String results = (String) defaultStepArgProcessor.changeArg(testValue);
        assertEquals("Hello World!", results, "The expected env value is not correct");
    }

    @Test
    void springSPELToUpperCase() {
        String testValue = "${'hello world'.toUpperCase()}";
        String results = (String) defaultStepArgProcessor.changeArg(testValue);
        assertEquals("HELLO WORLD", results, "The expected env value is not correct");
    }


    @Test
    void returningTheEnvVariableValuesThenMatches() {
        String testValue = "${env('test.value.from.property')}";
        String results = (String) defaultStepArgProcessor.changeArg(testValue);
        assertEquals("3025", results, "The expected env value is not correct");
    }

    @Test
    void catchingErrorThenProPropertyFound() {
        String testValue = "${env('test.value.from.wrong')}";
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> defaultStepArgProcessor.changeArg(testValue), "Expected to throw, but it didn't");
        assertTrue(thrown.getMessage().contentEquals("The specified property: test.value.from.wrong was not found!"));
    }


    @Test
    void alListOneArgIsRequired() {
        String testValue = "${env()}";
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> defaultStepArgProcessor.changeArg(testValue), "Expected to throw, but it didn't");

        assertTrue(thrown.getMessage().contentEquals("Cannot pre-process a step argument (env): at least one argument is required!"));
    }

    @Test
    void returnSameValueWhenNotMatchingPatter() {
        String testValue = "same value is passed";
        String results = (String) defaultStepArgProcessor.changeArg(testValue);
        assertEquals(testValue, results, "The expected value when not matching is wrong");
    }

    @Test
    void updateAnElementFromAListOfStrings() {
        List<String> stringList = Arrays.asList("${env('test.value.from.property')}");
        List<String> results = (List<String>) defaultStepArgProcessor.changeArg(stringList);
        assertTrue(results.stream().anyMatch("3025"::equals));
    }

    @Test
    void generateValueBaseOnRegEx(){
        String testValue = "${regEx('[0-9]{5}')}";
        String results = (String) defaultStepArgProcessor.changeArg(testValue);
        assertDoesNotThrow(()->Integer.parseInt(results));
    }
}
