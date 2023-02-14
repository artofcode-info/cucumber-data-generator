/*
 * Copyright (c) 2023.  steti93
 */

package org.artofcode.info.step.args.impl;

import lombok.RequiredArgsConstructor;
import org.artofcode.info.step.args.StepArgProcessor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class DefaultStepArgProcessor implements StepArgProcessor {

    private final Environment environment;

    public Object env(String... param) {
        final Iterator<String> stringIterator = Arrays.asList(param).iterator();
        if (!stringIterator.hasNext()) {
            throw new IllegalArgumentException("Cannot pre-process a step argument (env): at least one argument is required!");
        }
        final String envRegex = stringIterator.next();
        String result = environment.getProperty(envRegex);
        if (Objects.isNull(result))
            throw new RuntimeException(format("The specified property: %s was not found!", envRegex));
        return result;
    }
}