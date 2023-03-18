# cucumber-data-generator

**Cucumber-data-generator** is an open source **java-11** library with the main scope to simplify generation of test
data from the feature file.

Project is compatible with java 8 and higher versions, is base on following main open source libraries:

* io.cucumber
* org.springframework.boot **(version: 2.7.9)**
* com.github.curious-odd-man.rgxgen

### The project contains one module:

* spring-cucumber-data-generator

### Test coverage:

![test coverage](.github/badges/jacoco.svg)

### How to configure:

In order to configure the library in you project you will have to do following steps:

1. [x] Add the maven dependency to you test or development project:

        <dependency>
            <groupId>org.artofcode.info</groupId>
            <artifactId>cucumber-data-generator</artifactId>
            <version>0.1-SNAPSHOT</version>
         </dependency>

3. [x] Extend your Spring configuration class to import
   `CucumberDataGeneratorSpringConfig.class` the import annotation will be like following:
   `@Import({CucumberDataGeneratorSpringConfig.class})`
4. [x] Enjoy and generate your data from feature file with following patterns: `${regEx('[0-9]{5}')} `

### Example of use:

To get the value from an environment variable:

    ${env('test.value.from.property')}

To generate a random value base on regular expression:

    ${regEx('[0-9]{5}')}

Combine multiple functions for example get the value base on property name and concat with a string:

    ${env('test.value.from.property').concat('22')}

### The branch name must follow the pattern:

* **feature**/{username}-{itemID}-{informative message}
* **bugfix**/{username}-{itemID}-{informative message}
* **issue**/{username}-{itemID}-{informative message}

### Commit message should have the following format:

{issueId} - Informative message of the feature
