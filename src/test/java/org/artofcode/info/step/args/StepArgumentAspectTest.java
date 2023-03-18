/*
 * Copyright (c) 2023.  steti93
 */

package org.artofcode.info.step.args;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StepArgumentAspectTest {
    @Mock
    private ProceedingJoinPoint joinPointMock;

    @Mock
    private StepArgProcessor stepArgProcessorMock;

    private StepArgumentAspect stepArgumentAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stepArgumentAspect = new StepArgumentAspect(stepArgProcessorMock);
    }

    @Test
    void testProcessStepArguments() throws Throwable {
        // Arrange
        String arg1 = "arg1";
        int arg2 = 2;
        Object[] args = { arg1, arg2 };
        when(joinPointMock.getArgs()).thenReturn(args);
        when(stepArgProcessorMock.changeArg(arg1)).thenReturn("updatedArg1");
        when(stepArgProcessorMock.changeArg(arg2)).thenReturn(3);
        when(joinPointMock.proceed(new Object[] { "updatedArg1", 3 })).thenReturn("result");

        // Act
        Object result = stepArgumentAspect.processStepArguments(joinPointMock);

        // Assert
        verify(joinPointMock).getArgs();
        verify(stepArgProcessorMock).changeArg(arg1);
        verify(stepArgProcessorMock).changeArg(arg2);
        verify(joinPointMock).proceed(new Object[] { "updatedArg1", 3 });
        verifyNoMoreInteractions(joinPointMock, stepArgProcessorMock);
        assertEquals("result", result);
    }

    @Test
    void testGivenStepPointCut() throws Exception {
        assertTrue(StepArgumentAspect.class.getMethod("givenStepPointCut").isAnnotationPresent(Pointcut.class));
    }

    @Test
    void testWhenStepPointCut() throws Exception {
        assertTrue(StepArgumentAspect.class.getMethod("whenStepPointCut").isAnnotationPresent(Pointcut.class));
    }

    @Test
    void testThenStepPointCut() throws Exception {
        assertTrue(StepArgumentAspect.class.getMethod("thenStepPointCut").isAnnotationPresent(Pointcut.class));
    }

    @Test
    void testAndStepPointCut() throws Exception {
        assertTrue(StepArgumentAspect.class.getMethod("andStepPointCut").isAnnotationPresent(Pointcut.class));
    }

    @Test
    void testButStepPointCut() throws Exception {
        assertTrue(StepArgumentAspect.class.getMethod("butStepPointCut").isAnnotationPresent(Pointcut.class));
    }
}
