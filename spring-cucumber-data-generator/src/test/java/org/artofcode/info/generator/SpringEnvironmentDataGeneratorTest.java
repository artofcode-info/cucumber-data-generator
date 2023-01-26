package org.artofcode.info.generator;

import org.artofcode.info.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestConfig.class)
@TestPropertySource(properties = {"test.value.from.property=3025"})
public class SpringEnvironmentDataGeneratorTest {

    @Autowired
    private SpringEnvironmentDataGenerator springEnvironmentDataGenerator;

    @Test
    void returningTheEnvVariableValuesThenMatches() {
        String testValue = "env(test.value.from.property)";
        String results = (String) springEnvironmentDataGenerator.generate(testValue);
        assertEquals("3025", results, "The expected env value is not correct");
    }

    @Test
    void catchingErrorThenProPropertyFound() {
        String testValue = "env(test.value.from.wrong)";
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> springEnvironmentDataGenerator.generate(testValue),
                "Expected to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contentEquals("The specified property: test.value.from.wrong was not found!"));
    }

    @Test
    void returnSameValueWhenNotMatchingPatter() {
        String testValue = "ent(test.value.from.wrong)";
        String results = (String) springEnvironmentDataGenerator.generate(testValue);
        assertEquals(testValue, results, "The expected value when not matching is wrong");
    }
}
