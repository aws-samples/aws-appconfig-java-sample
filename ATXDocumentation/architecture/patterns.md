# Architectural Patterns

## Design Patterns Identified

### 1. Cache-Aside Pattern
- **Location**: `AppConfigUtility.java`, `ConfigurationCache.java`, `ConfigurationCacheItem.java`
- **Implementation**: The application checks the cache before calling AWS AppConfig. On cache miss or TTL expiry, it fetches from AppConfig and populates the cache.
- **Key characteristics**:
  - TTL-based invalidation (configurable via `appconfig.cacheTtlInSeconds`)
  - Thread-safe via `ConcurrentHashMap`
  - Exception caching for `ResourceNotFoundException` and `BadRequestException`

### 2. Graceful Degradation / Fallback Pattern
- **Location**: `MoviesController.java` — `movie()` method
- **Implementation**: On any exception from AppConfig, the controller falls back to a static movie array (`PAIDMOVIES`), ensuring the endpoint always returns a response.

### 3. Supplier/Factory Pattern (Lazy Initialization)
- **Location**: `MoviesController.java` — `getOrDefault()` method
- **Implementation**: Uses Java `Supplier<T>` with `Optional.ofNullable().orElseGet()` to provide configurable dependency injection at method level, allowing test doubles to be injected.

### 4. Composite Key Pattern
- **Location**: `ConfigurationKey.java`
- **Implementation**: Combines application, environment, and configuration name into a single key object used for cache lookups. Implements proper `equals()` and `hashCode()` for HashMap usage.

### 5. Multi-Stage Docker Build
- **Location**: `Dockerfile`
- **Implementation**: Separates build environment (Maven + JDK) from runtime environment (JRE only), reducing final image size and attack surface.

## Anti-Patterns Identified

### 1. Controller creates services per-request
- **Location**: `MoviesController.java`
- **Issue**: `AppConfigUtility` is instantiated on every request rather than being a Spring-managed singleton bean. This wastes resources and prevents proper lifecycle management.

### 2. Direct HTML generation in controller
- **Location**: `MoviesController.java`, `HTMLBuilder.java`
- **Issue**: The controller returns HTML strings directly instead of using a proper template engine (Thymeleaf, FreeMarker) or returning JSON for a separate frontend.

### 3. Dual build system without synchronization
- **Location**: `pom.xml` (Spring Boot 2.0.5) vs `build.gradle` (Spring Boot 2.3.0)
- **Issue**: Two build files with different dependency versions create confusion about which is authoritative.

### 4. System.out.println in production code
- **Location**: `MoviesController.java` (lines 86, 89, 119, 122)
- **Issue**: Uses `System.out.println` alongside Log4j logger, bypassing structured logging configuration.

## Architectural Style

The application follows a **simple layered architecture** without strict separation of concerns:
- No service layer between controller and utility
- No repository/DAO pattern (data comes from AppConfig, not a database)
- No DTO separation (Movie POJO used across all layers)

This is appropriate for its scope as a demonstration application.
