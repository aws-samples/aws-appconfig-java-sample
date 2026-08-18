# API Reference

## REST Endpoints

### GET /movies/getMovies
- **Controller**: `MoviesController`
- **Method**: `movie()`
- **Description**: Retrieves the current movie list from AWS AppConfig and renders as HTML
- **Response**: HTML string containing movie list table
- **Fallback**: On error, returns static movie list (10 hardcoded movies)
- **Content-Type**: text/html (implicit from String return in @RestController)

### POST /movies/{movie}/edit
- **Controller**: `MoviesController`
- **Method**: `processUpdateMovie(@Valid Movie movie, BindingResult result, @PathVariable("movieId") int movieId)`
- **Description**: Updates a movie configuration in AWS AppConfig
- **Path Variable**: `movieId` — integer ID of the movie to edit
- **Request Body**: Movie object (validated with `@Valid`)
- **Response**: HTML string with updated movie list
- **Validation Error Response**: Returns "editMovieForm" view name

## Internal API (Public Methods)

### AppConfigUtility
| Method | Parameters | Returns | Description |
|--------|-----------|---------|-------------|
| `AppConfigUtility(...)` | AppConfigClient, ConfigurationCache, Duration, String | — | Constructor |
| `getConfiguration` | ConfigurationKey | GetConfigurationResponse | Fetch config with caching |
| `updateConfiguration` | ConfigurationKey, String | GetConfigurationResponse | Update config in AppConfig |

### ConfigurationCache
| Method | Parameters | Returns | Description |
|--------|-----------|---------|-------------|
| `get` | ConfigurationKey | ConfigurationCacheItem | Retrieve cached item |
| `put` | ConfigurationKey, ConfigurationCacheItem | void | Store item in cache |

### ConfigurationCacheItem
| Method | Parameters | Returns | Description |
|--------|-----------|---------|-------------|
| `isStale` | — | boolean | Check if item has exceeded TTL |
| `getResponse` | — | GetConfigurationResponse | Get cached response |
| `getCachedException` | — | Exception | Get cached exception (null if none) |

### HTMLBuilder
| Method | Parameters | Returns | Description |
|--------|-----------|---------|-------------|
| `getMoviesHtml` | Movie[] | String | Render movie array as HTML |

### MovieUtils (First-Party Library)
| Method | Parameters | Returns | Description |
|--------|-----------|---------|-------------|
| `isValidMovie` | String movieName, int movieId | boolean | Validate movie name and ID |
| `isValidMovieName` | String movieName | boolean | Validate movie name only |

### Security
| Method | Parameters | Returns | Description |
|--------|-----------|---------|-------------|
| `getCertificate` | File certFile | Certificate | Load and validate X.509 certificate |

### Movie
| Method | Parameters | Returns | Description |
|--------|-----------|---------|-------------|
| `getId` | — | long | Get movie ID |
| `getMovieName` | — | String | Get movie name |
