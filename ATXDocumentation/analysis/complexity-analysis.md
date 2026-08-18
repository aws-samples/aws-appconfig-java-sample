# Complexity Analysis

## Cyclomatic Complexity

| Method | File | Complexity | Notes |
|--------|------|-----------|-------|
| `movie()` | MoviesController.java | Medium | Try-catch, JSON iteration, fallback logic |
| `processUpdateMovie()` | MoviesController.java | Medium | Validation branch, JSON iteration |
| `getConfiguration()` | AppConfigUtility.java | Low | Cache check + API call |
| `isStale()` | ConfigurationCacheItem.java | Low | Single comparison |
| `isValidMovie()` | MovieUtils.java | Low | 4 boolean conditions |
| `getCertificate()` | Security.java | Low | Try-catch with two exception types |
| `getOrDefault()` | MoviesController.java | Low | Optional-based null check |

## Hotspots

### MoviesController.movie() — Highest Complexity
This method has the highest complexity in the codebase due to:
1. Multiple object instantiations (AppConfigUtility, HTMLBuilder)
2. JSON parsing with iteration loop
3. Environment property lookups (potential NPE)
4. Full try-catch with alternative rendering path
5. `System.out.println` calls mixed with Log4j logging

### Code Duplication Hotspot
`MoviesController.movie()` and `MoviesController.processUpdateMovie()` share:
- AppConfigUtility instantiation pattern (identical 4-arg constructor)
- Environment property reading (application, environment, config)
- JSON response parsing loop (nearly identical)
- HTMLBuilder rendering

This duplicated block (~30 lines) could be extracted to a private helper method.

## Maintainability Concerns

| Area | Concern | Impact |
|------|---------|--------|
| Per-request service creation | MoviesController creates AppConfigUtility each call | Performance, testability |
| Mixed logging | System.out + Log4j in same method | Debugging confusion |
| String-typed configuration | Properties read as strings without validation | Runtime errors |
| No connection management | AppConfigClient created with defaults | Resource leaks possible |
