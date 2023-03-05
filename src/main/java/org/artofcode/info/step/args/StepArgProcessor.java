/*
 * Copyright (c) 2023.  steti93
 */

package org.artofcode.info.step.args;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableTypeRegistry;
import io.cucumber.datatable.DataTableTypeRegistryTableConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2023.  steti93
 */

public interface StepArgProcessor {

    String EXPRESSION_IS_SUBJECT_OF_CHANGE = "(.*)\\$\\{(.*)\\}(.*)";

    String ARGUMENT_REGEX = "\\$\\{(.*?\\(.*?\\))\\}";

    default Boolean isStepArgSubjectOfChange(final Object args) {
        if (Objects.nonNull(args)) {
            if (args instanceof String) {
                return ((String) args).matches(EXPRESSION_IS_SUBJECT_OF_CHANGE);
            }
            if (args instanceof List || args instanceof Map || args instanceof DataTable) {
                return true;
            }
        }
        return false;
    }

    default Object changeArg(final Object args) {
        if (isStepArgSubjectOfChange(args)) {
            return changeArgument(args);
        } else {
            return args;
        }
    }

    default Object changeArgument(Object argument) {
        if (argument instanceof String) {
            return parseAndChangeArgument((String) argument);
        }
        if (argument instanceof List) {
            List<?> argsAsCollection = (List<?>) argument;
            if (!argsAsCollection.isEmpty()) {
                Object firstItem = argsAsCollection.get(0);
                if (firstItem instanceof String) {
                    return argsAsCollection.stream().map(this::changeArgument).collect(Collectors.toList());
                }
            }
        }
        if (argument instanceof Map) {
            Map<?, ?> argAsMap = (Map<?, ?>) argument;
            if (!argAsMap.isEmpty()) {
                final Map<Object, Object> argCopy = new HashMap<>();
                argAsMap.forEach((k, v) -> argCopy.put(k, changeArgument(v)));
                return argCopy;
            }
        }
        if (argument instanceof DataTable) {
            List<List<String>> argumentsAsCollection = ((DataTable) argument).asLists();
            List<List<String>> updatedArgList = argumentsAsCollection.stream().map(listElement -> (List<String>) changeArg(listElement)).collect(Collectors.toList());
            return DataTable.create(updatedArgList, new DataTableTypeRegistryTableConverter(new DataTableTypeRegistry(Locale.getDefault())));
        }

        return argument;
    }

    default String parseAndChangeArgument(final String argument) {
        if (!StringUtils.isEmpty(argument)) {
            final Matcher matcher = Pattern.compile(ARGUMENT_REGEX).matcher(argument);
            List<String> matchedExpressions = new ArrayList<>();
            while (matcher.find()) {
                matchedExpressions.add(matcher.group(1));
            }
            String evaluatedExp = argument;
            for (String expression : matchedExpressions) {
                evaluatedExp = evaluatedExp.replaceFirst(ARGUMENT_REGEX, evaluateExpression(expression));
            }
            return evaluatedExp;
        }
        return "";
    }

    default String evaluateExpression(final String expression) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        EvaluationContext evaluationContext = new StandardEvaluationContext(this);
        final Expression exp = expressionParser.parseExpression(expression);
        return (String) exp.getValue(evaluationContext);
    }
}
