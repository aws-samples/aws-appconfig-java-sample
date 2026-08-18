> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Decision Logic

## Decision Points in Application Code

### Decision 1: Cache Hit vs Cache Miss
- **Location**: `AppConfigUtility.java` — `getConfiguration()`
- **Condition**: Is ConfigurationKey present in cache AND not stale?
- **True path**: Return cached `GetConfigurationResponse`
- **False path**: Call AWS AppConfig API, cache result, return fresh response

### Decision 2: Cache Item Staleness
- **Location**: `ConfigurationCacheItem.java`
- **Condition**: `System.currentTimeMillis() > refreshTime`
- **True path**: Item is stale, trigger refresh
- **False path**: Item is fresh, serve from cache

### Decision 3: AppConfig Fetch Success vs Failure
- **Location**: `MoviesController.java` — `movie()` method
- **Condition**: Does AppConfig call complete without exception?
- **True path**: Parse JSON, build Movie array, render HTML from dynamic data
- **False path**: Log error, render HTML from static `PAIDMOVIES` array (graceful degradation)

### Decision 4: Movie Name Validation
- **Location**: `MoviesController.java` — `processUpdateMovie()`
- **Condition**: `MovieUtils.isValidMovieName(movie.getMovieName())` returns true?
- **True path**: Proceed with AppConfig update
- **False path**: Reject with validation error "Invalid movie name", return edit form view

### Decision 5: Movie Validity (MovieUtils)
- **Location**: `MovieUtils.java` — `isValidMovie()`
- **Conditions** (all must be true):
  - `movieName != null`
  - `movieName.length() >= 1`
  - `movieName.length() <= 200`
  - `movieId > 0`
- **True path**: Return `true`
- **False path**: Return `false`

### Decision 6: Supplier Value Resolution
- **Location**: `MoviesController.java` — `getOrDefault()`
- **Condition**: `optionalGetter.get()` returns non-null?
- **True path**: Use the supplied value (e.g., injected test client)
- **False path**: Use default value from `defaultGetter` (e.g., create new AppConfigClient)

### Decision 7: Certificate Validity
- **Location**: `Security.java` — `getCertificate()`
- **Condition**: `cert.checkValidity(new Date())` passes?
- **True path**: Return the certificate
- **False path**: Throws `CertificateExpiredException` or `CertificateNotYetValidException`

### Decision 8: Exception Type in Cache
- **Location**: `ConfigurationCacheItem.java`
- **Condition**: Is the caught exception `ResourceNotFoundException` or `BadRequestException`?
- **True path**: Cache the exception (negative caching)
- **False path**: Propagate the exception upward
