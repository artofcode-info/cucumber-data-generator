package org.artofcode.info.step.args;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/*
 * Copyright (c) 2023.  steti93
 */

@Aspect
@Component
@Log4j2
@AllArgsConstructor
public class StepArgumentAspect {

    private final StepArgProcessor stepArgProcessor;

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
        Stream methodArgs = Stream.of(joinPoint.getArgs());
        Object[] updatedArgs = methodArgs.map(stepArgProcessor::changeArg).toArray();
        return joinPoint.proceed(updatedArgs);
    }
}
