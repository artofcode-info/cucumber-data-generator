# cucumber-data-generator

**Cucumber-data-generator** is an open source java library with the main scope to simplify generation of test data from the feature file.

Project is compatible with java 8 and higher versions, is base on following main open source libraries:
* io.cucumber
* org.springframework.boot
* com.github.curious-odd-man
### The project contains one module:

* spring-cucumber-data-generator

### Test coverage:
   ![test coverage](.github/badges/jacoco.svg)

### Example of use:
To get the value from an environment variable:

    ${env('test.value.from.property')}

To generate a random value base on regular expression:

    ${regEx('[0-9]{5}')}

### The branch name must follow the pattern:

* **feature**/{username}-{itemID}-{informative message}
* **bugfix**/{username}-{itemID}-{informative message}
* **issue**/{username}-{itemID}-{informative message}

**release/{version}** - for versioning we will use semantic version principle


### Commit message should have the following format:

{issueId} - Informative message of the feature
