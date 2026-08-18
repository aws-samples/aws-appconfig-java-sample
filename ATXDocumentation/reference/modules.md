# Modules

## Module Overview

| Module | Artifact | Purpose | Java Version |
|--------|----------|---------|-------------|
| movie-service | org.amazonaws.samples:movie-service:0.1.0 | Main Spring Boot application | 1.8 |
| movie-service-utils | com.amazonaws.samples:movie-service-utils:0.1.0 | First-party validation library | 17 (source) |

## Main Module: movie-service

**Build file**: `pom.xml`  
**Packaging**: JAR (Spring Boot fat JAR)  
**Entry point**: `MoviesApplication.main()`

### Packages

| Package | Responsibility |
|---------|---------------|
| `com.amazonaws.samples.appconfig.movies` | REST API, domain model, application bootstrap |
| `com.amazonaws.samples.appconfig.cache` | In-memory configuration caching |
| `com.amazonaws.samples.appconfig.model` | Data models (ConfigurationKey) |
| `com.amazonaws.samples.appconfig.utils` | Service utilities, AWS integration, rendering |

### Dependencies on movie-service-utils
- `MoviesController` calls `MovieUtils.isValidMovieName()` for movie name validation
- Dependency declared in `pom.xml` as `com.amazonaws.samples:movie-service-utils:0.1.0`
- JAR must be manually installed from `movie-service-utils/built-library/0_1_0/`

## Sub-Module: movie-service-utils

**Build file**: `movie-service-utils/pom.xml`  
**Source**: `movie-service-utils/src/main/java/`  
**Versioning**: Multiple pre-built versions available for different Java targets

### Version Compatibility Matrix

| Version | Java Target | Method Signature |
|---------|------------|-----------------|
| 0.1.0 | Java 8 | `isValidMovieName(String)` |
| 0.2.0 | Java 17 | `isValidMovie(String, int)` |
| 0.3.0 | Java 21 | `isValidMovie(String, int)` |

Note: The method signature changed between 0.1.0 and 0.2.0, requiring caller updates when upgrading.

## Inter-Module Dependencies

```
movie-service ──depends-on──→ movie-service-utils (0.1.0)
                                  │
                                  └── provides MovieUtils.isValidMovieName()
```
