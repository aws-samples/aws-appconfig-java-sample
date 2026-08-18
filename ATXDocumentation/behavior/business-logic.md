> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Business Logic

## Core Business Rules

### MoviesController — Movie List Retrieval
- **File**: `src/main/java/com/amazonaws/samples/appconfig/movies/MoviesController.java`
- **Method**: `movie()` (mapped to `GET /movies/getMovies`)

**Rules**:
1. Cache TTL is configured from `appconfig.cacheTtlInSeconds` property (default: 30 seconds)
2. AppConfig is queried using composite key: (application, environment, config) from `application.yml`
3. Response is parsed as JSON with structure `{"movies": [{"id": long, "movieName": string}]}`
4. Each JSON object is mapped to a `Movie` POJO
5. On ANY exception, fall back to static `PAIDMOVIES` array (10 hardcoded movies)
6. Response is rendered as HTML via `HTMLBuilder`

### MoviesController — Movie Edit
- **File**: `src/main/java/com/amazonaws/samples/appconfig/movies/MoviesController.java`
- **Method**: `processUpdateMovie()` (mapped to `POST /movies/{movie}/edit`)

**Rules**:
1. Movie name is validated via `MovieUtils.isValidMovieName(movie.getMovieName())`
2. If validation fails, reject with error message "Invalid movie name" and return edit form
3. If valid, update configuration in AppConfig with movie's string representation
4. Re-fetch and display updated movie list as HTML

### MovieUtils — Movie Validation
- **File**: `movie-service-utils/src/main/java/com/amazonaws/samples/appconfig/utils/MovieUtils.java`
- **Method**: `isValidMovie(String movieName, int movieId)`

**Rules**:
1. Movie name must not be null
2. Movie name length must be between 1 and 200 characters
3. Movie ID must be positive (> 0)
4. Returns `true` only if all conditions are met

### ConfigurationCacheItem — TTL Logic
- **File**: `src/main/java/com/amazonaws/samples/appconfig/cache/ConfigurationCacheItem.java`

**Rules**:
1. Cache item stores a configuration value with a calculated refresh time
2. Refresh time = creation time + TTL duration
3. Item is considered stale when current time exceeds refresh time
4. Exceptions from `ResourceNotFoundException` and `BadRequestException` are cached (negative caching)
5. On refresh, if AppConfig call fails with cached exception types, the cached exception is returned

### AppConfigUtility — Configuration Access
- **File**: `src/main/java/com/amazonaws/samples/appconfig/utils/AppConfigUtility.java`

**Rules**:
1. Check cache first for existing configuration
2. If cache miss or stale, call AppConfig `getConfiguration` API
3. Store successful response in cache with configured TTL
4. Return `GetConfigurationResponse` to caller
5. `updateConfiguration` writes new value to AppConfig and returns updated response

### Security — Certificate Validation
- **File**: `src/main/java/com/amazonaws/samples/appconfig/utils/Security.java`
- **Method**: `getCertificate(File certFile)`

**Rules**:
1. Load X.509 certificate from file input stream
2. Check certificate validity against current date
3. Throw `CertificateExpiredException` if certificate has expired
4. Throw `CertificateNotYetValidException` if certificate is not yet valid
5. Wrap `CertificateException` and `FileNotFoundException` in `RuntimeException`

### Math — Numeric Operations (Demo)
- **File**: `src/main/java/com/amazonaws/samples/appconfig/utils/Math.java`

**Rules**:
1. Uses deprecated `BigDecimal.ROUND_DOWN` for division rounding
2. Demonstrates deprecated wrapper constructors (`new Integer()`, `new Boolean()`, etc.)
