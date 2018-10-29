# ss test project

## VCS
git clone https://github.com/popovua/ss.git.
There is no custom setup task that you need to perform. You should be able to run the project out of the box.

All specific data should be stored in properties files located under package `resources/properties`.

## Test Strategy
Framework based on Keyword-Driver approach.
The idea is creating test scenarios using chain of keywords.

First step, call for keywords page, i.e.:
* `keywordManager` - ``keywordManager.weather()``

Second step, select keyword, i.e.:
* `keywordManager` - ``keywordManager.weather().getWeatherForecast()``

All dynamic data should be stored in ``Storage.rememberThe(String key, Object value)``
It can be retrieved using ``Storage.whatIsThe(String key)`` or ``Storage.whatIsTheObject(String key, Class<T> tClass)``

## Reporting
Reporting tool - *Allure 2*.

[![Allure 2](https://avatars3.githubusercontent.com/u/5879127?s=200&v=4)](https://github.com/allure-framework/allure2)

Reporting tool integrated with base frameworks: *TestNG* and *REST Assured*.

> Use annotation *Step* to mark method as Allure step

### TestNG Integration
Automatically. Don't need any extra setup in test. Just use TestNG API (annotations *Test*, *Before*, *After*) and test results will automatically appear in result HTML report.