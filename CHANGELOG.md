# Change Log

All notable changes to this project will be documented in this file.

## [Unrealesed] v 1.0 - 2023-01-01

Here we write upgrading notes for brands. It's a team effort to make them as straightforward as possible.

### Added

* [Issue 4 - Creating a dedicate spring module](https://github.com/artofcode-info/cucumber-data-generator/issues/4) 
  - Added custom method env(String... param) is reading data from properties base on following format : `${env('test.value.from.property')}` where `test.value.from.property` it's an actual property values that needs to be replaced.
* [Issue 7 - RegEx Generator](https://github.com/artofcode-info/cucumber-data-generator/issues/7)
  - Added custom  method regEx(String... param) is generating random values base on Regular Expression (Regex) : `${regEx('[0-9]{5}')}` and will generate a random number for example `28647`.

### Changed

### Fixed
