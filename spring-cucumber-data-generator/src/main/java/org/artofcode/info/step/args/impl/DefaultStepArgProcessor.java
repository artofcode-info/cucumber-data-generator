/*
 * Copyright (c) 2023.  steti93
 */

package org.artofcode.info.step.args.impl;

import lombok.RequiredArgsConstructor;
import org.artofcode.info.step.args.StepArgProcessor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class DefaultStepArgProcessor implements StepArgProcessor {

    private final Environment environment;

    public Object env(final String argument) {
        String result = null;
        result = environment.getProperty(argument);
        if (Objects.isNull(result))
            throw new RuntimeException(format("The specified property: %s was not found!", argument));

        return result;
    }

}
