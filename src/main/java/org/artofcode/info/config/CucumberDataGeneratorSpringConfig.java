package org.artofcode.info.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/*
 * Copyright (c) 2023.  steti93
 */
@Configuration
@ComponentScan(basePackages = {"org.artofcode.info"})
@EnableAspectJAutoProxy
public class CucumberDataGeneratorSpringConfig {
}
