# Components

## Component Inventory

### 1. MoviesController
- **Type**: REST Controller
- **Package**: `com.amazonaws.samples.appconfig.movies`
- **File**: `src/main/java/com/amazonaws/samples/appconfig/movies/MoviesController.java`
- **Responsibility**: Handles HTTP requests for movie listing and editing
- **Endpoints**:
  - `GET /movies/getMovies` — Fetch and display movie list from AppConfig
  - `POST /movies/{movie}/edit` — Update a movie via AppConfig
- **Dependencies**: AppConfigUtility, ConfigurationCache, HTMLBuilder, MovieUtils, Environment

### 2. MoviesApplication
- **Type**: Spring Boot Application Entry Point
- **Package**: `com.amazonaws.samples.appconfig.movies`
- **File**: `src/main/java/com/amazonaws/samples/appconfig/movies/MoviesApplication.java`
- **Responsibility**: Bootstrap the Spring Boot application
- **Annotations**: `@SpringBootApplication`

### 3. Movie
- **Type**: Domain Model (POJO)
- **Package**: `com.amazonaws.samples.appconfig.movies`
- **File**: `src/main/java/com/amazonaws/samples/appconfig/movies/Movie.java`
- **Fields**: `id` (long), `movieName` (String)
- **Responsibility**: Data carrier for movie entities

### 4. AppConfigUtility
- **Type**: Service Utility
- **Package**: `com.amazonaws.samples.appconfig.utils`
- **File**: `src/main/java/com/amazonaws/samples/appconfig/utils/AppConfigUtility.java`
- **Responsibility**: Wraps AWS AppConfig client with caching logic
- **Key Methods**: `getConfiguration(ConfigurationKey)`, `updateConfiguration(ConfigurationKey, String)`
- **Dependencies**: AppConfigClient, ConfigurationCache, Duration (TTL)

### 5. ConfigurationCache
- **Type**: Cache Implementation
- **Package**: `com.amazonaws.samples.appconfig.cache`
- **File**: `src/main/java/com/amazonaws/samples/appconfig/cache/ConfigurationCache.java`
- **Responsibility**: Thread-safe in-memory cache using `ConcurrentHashMap<ConfigurationKey, ConfigurationCacheItem>`
- **Key Methods**: `get(ConfigurationKey)`, `put(ConfigurationKey, ConfigurationCacheItem)`

### 6. ConfigurationCacheItem
- **Type**: Cache Entry
- **Package**: `com.amazonaws.samples.appconfig.cache`
- **File**: `src/main/java/com/amazonaws/samples/appconfig/cache/ConfigurationCacheItem.java`
- **Responsibility**: Holds cached configuration value with TTL-based refresh logic
- **Features**: Automatic refresh time calculation, exception caching for AppConfig errors

### 7. ConfigurationKey
- **Type**: Model / Composite Key
- **Package**: `com.amazonaws.samples.appconfig.model`
- **File**: `src/main/java/com/amazonaws/samples/appconfig/model/ConfigurationKey.java`
- **Responsibility**: Composite key (application + environment + configuration) for AppConfig lookups
- **Used as**: HashMap key in ConfigurationCache

### 8. HTMLBuilder
- **Type**: View Utility
- **Package**: `com.amazonaws.samples.appconfig.utils`
- **File**: `src/main/java/com/amazonaws/samples/appconfig/utils/HTMLBuilder.java`
- **Responsibility**: Generates HTML markup for rendering movie lists
- **Key Methods**: `getMoviesHtml(Movie[])`

### 9. MovieUtils (First-Party Library)
- **Type**: Validation Utility
- **Package**: `com.amazonaws.samples.appconfig.utils`
- **File**: `movie-service-utils/src/main/java/com/amazonaws/samples/appconfig/utils/MovieUtils.java`
- **Responsibility**: Validates movie names and IDs
- **Key Methods**: `isValidMovie(String movieName, int movieId)`

### 10. Encoder (Deprecated Pattern Demo)
- **Type**: Utility (demo)
- **File**: `src/main/java/com/amazonaws/samples/appconfig/utils/Encoder.java`
- **Responsibility**: Demonstrates deprecated `sun.misc.BASE64Encoder` usage

### 11. Math (Deprecated Pattern Demo)
- **Type**: Utility (demo)
- **File**: `src/main/java/com/amazonaws/samples/appconfig/utils/Math.java`
- **Responsibility**: Demonstrates deprecated wrapper constructors and BigDecimal rounding

### 12. Security (Deprecated Pattern Demo)
- **Type**: Utility (demo)
- **File**: `src/main/java/com/amazonaws/samples/appconfig/utils/Security.java`
- **Responsibility**: Demonstrates deprecated `javax.security.cert` usage

## Component Interaction Map

```
MoviesController
  ├── AppConfigUtility (configuration fetching)
  │     ├── AppConfigClient (AWS SDK)
  │     └── ConfigurationCache
  │           └── ConfigurationCacheItem
  ├── HTMLBuilder (response rendering)
  ├── MovieUtils (validation)
  └── Movie (data model)
       └── ConfigurationKey (lookup key)
```
