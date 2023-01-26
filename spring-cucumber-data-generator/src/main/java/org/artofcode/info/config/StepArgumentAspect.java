package org.artofcode.info.config;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Objects;
import java.util.stream.Stream;

/*
 * Copyright (c) 2022.  steti93
 */

@Aspect
@Component
@Log4j2
public class StepArgumentAspect {

    @Autowired
    private Environment environment;

    @Pointcut("@annotation(io.cucumber.java.en.Given)")
    public void givenStepPointCut() {
    }

    @Pointcut("@annotation(io.cucumber.java.en.When)")
    public void whenStepPointCut() {
    }

    @Pointcut("@annotation(io.cucumber.java.en.Then)")
    public void thenStepPointCut() {
    }

    @Pointcut("@annotation(io.cucumber.java.en.And)")
    public void andStepPointCut() {
    }

    @Pointcut("@annotation(io.cucumber.java.en.But)")
    public void butStepPointCut() {
    }

    @Around("givenStepPointCut() || whenStepPointCut()||thenStepPointCut()||andStepPointCut()||butStepPointCut()")
    public Object processStepArguments(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        StopWatch stopWatch = new StopWatch(className + "->" + methodName);
        stopWatch.start(methodName);
        stopWatch.stop();
        log.info(stopWatch.shortSummary());

        Stream methodArgs = Stream.of(joinPoint.getArgs());
        Object[] updatedArgs = methodArgs.map(this::getValueBaseOnPattern).toArray();
        log.info("Proper value from property is {}", updatedArgs);
        return joinPoint.proceed(updatedArgs);
    }

    private Object getValueBaseOnPattern(Object objectArg) {
        Object result = objectArg;
        if (Objects.nonNull(objectArg)) {
            if (objectArg instanceof String) {
                String stringArg = (String) objectArg;
                if (stringArg.matches("^env\\(.*\\)")) {
                    result = getPropertyValue(StringUtils.substringBetween(stringArg, "\"", "\""));
                }
            }
        }
        return result;
    }

    private Object getPropertyValue(final String param) {
        if (Objects.nonNull(param)) {
            return environment.getProperty(param);
        } else throw new RuntimeException("Method parameter is null");
    }

}
