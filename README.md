# ss test project

## VCS
git clone https://popov1@bitbucket.org/popov1/ss.git
There is no custom setup task that you need to perform. You should be able to run the project out of the box.

## Environment
Test design must follow multi-environment concept. Avoid any hardcode of environment specific data.

All specific data should be stored in properties files located under package `resources/properties`.

## Test Strategy
Framework based on Keyword-Driver approach.
The idea is creating test scenarios using chain of keywords.

First step, call for keywords page, i.e.:
* 'Page' - ''pageManager.homePage()''

Second step, select keyword, i.e.:
* 'Page' - ''pageManager.homePage().chooseCategory(category)''

## Reporting
Reporting tool - *Allure 2*.

[![Allure 2](https://avatars3.githubusercontent.com/u/5879127?s=200&v=4)](https://github.com/allure-framework/allure2)

Reporting tool integrated with base frameworks: *TestNG* and *Selenide*.

> Use annotation *@Step* to mark method as Allure step

### TestNG Integration
Automatically. Don't need any extra setup in test. Just use TestNG API (annotations *@Test*, *@Before*, *@After*) and test results will automatically appear in result HTML report.