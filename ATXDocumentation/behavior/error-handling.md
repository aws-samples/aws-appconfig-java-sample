> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Error Handling

## Error Handling Patterns

### 1. Global Try-Catch with Fallback (MoviesController)
- **Location**: `MoviesController.java` — `movie()` method (lines 63-99)
- **Pattern**: Entire AppConfig fetch + JSON parsing is wrapped in a try-catch that catches `Exception`
- **Recovery**: Returns HTML rendered from static `PAIDMOVIES` array
- **Logging**: Error logged via `logger.error("Error fetching movies from AWS App Config", e)`
- **Effect**: Endpoint never returns a 5xx error; always serves some movie list

### 2. Exception Wrapping (Security)
- **Location**: `Security.java` — `getCertificate()` method
- **Pattern**: Checked exceptions (`CertificateException`, `FileNotFoundException`) are wrapped in `RuntimeException`
- **Effect**: Converts checked exceptions to unchecked, propagating up the call stack

### 3. Negative Caching (ConfigurationCacheItem)
- **Location**: `ConfigurationCacheItem.java`
- **Pattern**: When AppConfig returns `ResourceNotFoundException` or `BadRequestException`, the exception is cached with TTL
- **Effect**: Prevents hammering AppConfig with requests for non-existent or invalid configurations

### 4. Validation Rejection (MoviesController)
- **Location**: `MoviesController.java` — `processUpdateMovie()` method
- **Pattern**: Uses Spring's `BindingResult` to collect validation errors
- **Recovery**: Returns form view name "editMovieForm" with error details

## Exception Flow Diagram

```
HTTP Request
  └── MoviesController
        ├── [Success] → AppConfigUtility → AppConfig API → JSON parse → HTML response
        └── [Exception]
              ├── Log error (Log4j)
              └── Return fallback HTML (static movies)

AppConfigUtility
  └── ConfigurationCache
        ├── [Cache Hit, Fresh] → Return cached value
        ├── [Cache Hit, Stale] → Refresh from API
        │     ├── [Success] → Update cache, return
        │     └── [ResourceNotFoundException/BadRequestException] → Cache exception
        └── [Cache Miss] → Fetch from API
              ├── [Success] → Cache and return
              └── [Exception] → Propagate to controller
```

## Unhandled Scenarios

1. **No explicit error handling in `processUpdateMovie()`**: If `appConfigUtility.updateConfiguration()` throws, there is no try-catch. The exception would propagate to Spring's default error handler (HTTP 500).
2. **No null-check on `env.getProperty()`**: If AppConfig properties are missing from `application.yml`, `NullPointerException` would occur at `Long.parseLong()`.
3. **No circuit-breaker pattern**: Repeated AppConfig failures may cause latency spikes until the negative cache catches them.
