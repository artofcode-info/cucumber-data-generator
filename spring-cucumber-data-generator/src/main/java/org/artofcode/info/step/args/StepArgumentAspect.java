package org.artofcode.info.step.args;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*
 * Copyright (c) 2022.  steti93
 */

@Aspect
@Component
@Log4j2
public class StepArgumentAspect {

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
//        Stream methodArgs = Stream.of(joinPoint.getArgs());
////        Object[] updatedArgs = methodArgs.map(this::getValueBaseOnPattern).toArray();
//        return joinPoint.proceed(updatedArgs);
        return null;
    }

//    private Object getValueBaseOnPattern(Object objectArg) {
//        Object result = objectArg;
//        if (Objects.nonNull(objectArg)) {
//            if (objectArg instanceof String) {
//               //TODO Add here the factory method
//                }
//            }
//        }
//        return result;
//    }


}
