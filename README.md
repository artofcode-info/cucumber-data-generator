# "Cucumber-data-generator" 
Is an open-source Java 11 library designed to simplify the generation of test data from feature files.

The project is compatible with Java 11 and is based on the following open-source libraries:

* **io.cucumber**
* **org.springframework.boot (version: 2.7.9)**
* **com.github.curious-odd-man.rgxgen**

### Test coverage:
Test coverage report is available here:

![test coverage](.github/badges/jacoco.svg)

### How to configure:

To configure the library in your project, you will need to follow the steps below::

1. Add the maven dependency to your test or development project:

        <dependency>
            <groupId>io.github.artofcode-info</groupId>
            <artifactId>cucumber-data-generator</artifactId>
            <version>0.0.7</version>
         </dependency>

2. Extend your Spring configuration class to import
   `CucumberDataGeneratorSpringConfig.class` the import annotation will be like the following:
   `@Import({CucumberDataGeneratorSpringConfig.class})`

3. After configuring, you can generate your data from feature files using the following patterns: `${regEx('[0-9]{5}')} `

### Example of use:

To get the value from an environment variable:

    ${env('test.value.from.property')}

To generate a random value based on a regular expression:

    ${regEx('[0-9]{5}')}

To combine multiple functions (for example, get the value based on property name and concatenate with a string):

    ${env('test.value.from.property').concat('22')}

### The branch name must follow the pattern:

* **feature**/{username}-{itemID}-{informative message}
* **bugfix**/{username}-{itemID}-{informative message}
* **issue**/{username}-{itemID}-{informative message}

### Commit message should have the following format:

{issueId} - Informative message of the feature
