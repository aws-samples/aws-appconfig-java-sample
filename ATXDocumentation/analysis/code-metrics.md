# Code Metrics

## Overall Statistics

| Metric | Value |
|--------|-------|
| Total source lines (Java + config) | ~1,882 |
| Java source files | 11 (main) + 4 (test) + 1 (utils module) |
| Configuration files | 2 (application.yml, log4j2.xml) |
| Build files | 2 (pom.xml, build.gradle) |
| Infrastructure templates | 2 (CloudFormation) |
| Packages | 4 |

## Per-File Metrics

| File | Approx. Lines | Methods | Complexity |
|------|---------------|---------|-----------|
| MoviesController.java | 140 | 9 | Medium |
| AppConfigUtility.java | 80 | 3 | Low |
| ConfigurationCacheItem.java | 50 | 4 | Low |
| ConfigurationKey.java | 45 | 5 | Low |
| HTMLBuilder.java | 40 | 1 | Low |
| Math.java | 35 | 2 | Low |
| Movie.java | 30 | 4 | Low |
| Encoder.java | 25 | 1 | Low |
| Security.java | 25 | 1 | Low |
| ConfigurationCache.java | 25 | 2 | Low |
| MoviesApplication.java | 12 | 1 | Low |
| MovieUtils.java | 20 | 2 | Low |

## Test Coverage Indicators

| Test File | Tests | Coverage Target |
|-----------|-------|----------------|
| MathTest.java | BigDecimal operations | Math.java |
| MockTest.java | Mockito patterns | Demonstration |
| MovieTest.java | Context loading | Spring context |
| MoviesControllerTest.java | Controller unit test | MoviesController.java |

## Quality Indicators

- **Code duplication**: Medium — `movie()` and `processUpdateMovie()` share significant code for AppConfig access and JSON parsing
- **Coupling**: MoviesController is tightly coupled to AppConfigUtility (creates instance per request)
- **Cohesion**: High within packages; each package has clear responsibility
- **Test ratio**: ~4 test files for 11 source files (low coverage)
