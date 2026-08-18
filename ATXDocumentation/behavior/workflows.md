> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Workflows

## Application-Level Workflows

### Workflow 1: Movie List Retrieval (Primary Entry Point)

**Entry Point**: `MoviesController.movie()` → `GET /movies/getMovies`

```
1. HTTP GET request arrives at /movies/getMovies
2. Parse cache TTL from environment property
3. Create AppConfigUtility with:
   - AppConfigClient (default or injected)
   - ConfigurationCache (existing or new)
   - TTL duration
   - Client ID (UUID)
4. Read AppConfig properties (application, environment, config)
5. Call appConfigUtility.getConfiguration(key)
   5a. Check ConfigurationCache for existing entry
   5b. If cached and fresh → return cached value
   5c. If stale or missing → call AWS AppConfig API
   5d. Store response in cache
6. Parse JSON response → extract "movies" array
7. Map each JSON object to Movie POJO
8. Convert Movie[] to HTML via HTMLBuilder
9. Return HTML response
   
   ON EXCEPTION:
   E1. Log error via Log4j
   E2. Build HTML from static PAIDMOVIES array
   E3. Return fallback HTML response
```

### Workflow 2: Movie Edit (Secondary Entry Point)

**Entry Point**: `MoviesController.processUpdateMovie()` → `POST /movies/{movie}/edit`

```
1. HTTP POST request arrives at /movies/{movie}/edit
2. Validate movie name via MovieUtils.isValidMovieName()
   2a. If invalid → reject with error, return "editMovieForm"
3. Create AppConfigUtility (same pattern as Workflow 1)
4. Read AppConfig properties from environment
5. Call appConfigUtility.updateConfiguration(key, movie.toString())
6. Parse updated JSON response
7. Map to Movie[] via JSON parsing
8. Convert to HTML via HTMLBuilder
9. Return HTML response
```

### Workflow 3: Configuration Caching (Internal)

**Entry Point**: `AppConfigUtility.getConfiguration(ConfigurationKey)`

```
1. Receive ConfigurationKey (application + environment + config)
2. Look up key in ConfigurationCache (ConcurrentHashMap)
3. If found:
   3a. Check if ConfigurationCacheItem.isStale()
   3b. If fresh → return cached GetConfigurationResponse
   3c. If stale → proceed to step 4
4. Call AppConfigClient.getConfiguration() with:
   - application, environment, configuration, clientId
5. Wrap response in ConfigurationCacheItem with TTL
6. Store in cache
7. Return GetConfigurationResponse
```

### Workflow 4: Application Bootstrap

**Entry Point**: `MoviesApplication.main(String[] args)`

```
1. Spring Boot starts via SpringApplication.run()
2. Component scan discovers MoviesController
3. application.yml loaded → configures:
   - Server: 0.0.0.0:8080
   - AppConfig: application, environment, config, cacheTtlInSeconds
4. Embedded Tomcat starts on port 8080
5. Application ready to serve requests
```
