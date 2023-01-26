package org.artofcode.info.generator;

/*
 * Copyright (c) 2022.  steti93
 */

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

@RequiredArgsConstructor
@Component
public class SpringEnvironmentDataGenerator implements SpringBaseDataGenerator {

    private final String ENV_GENERATOR_PATTERN = "env\\((.*?)\\)";

    private final Environment environment;

    @Override
    public Object generate(String matchedValue) {
        Pattern pattern = Pattern.compile(ENV_GENERATOR_PATTERN);
        Matcher matcher = pattern.matcher(matchedValue);
        String result = null;
        if (matcher.find()) {
            var property = matcher.group(1);
            result = environment.getProperty(property);
            if (Objects.isNull(result))
                throw new RuntimeException(format("The specified property: %s was not found!", property));
        } else {
            result = matchedValue;
        }
        return result;
    }

    @Override
    public String getPattern() {
        return ENV_GENERATOR_PATTERN;
    }
}
