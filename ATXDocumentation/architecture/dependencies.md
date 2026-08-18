# Dependencies

## Internal Component Dependencies

```
MoviesController
  → AppConfigUtility (creates new instance per request)
  → ConfigurationCache (injected via supplier pattern)
  → ConfigurationCacheItem (indirectly via cache)
  → ConfigurationKey (creates for AppConfig lookups)
  → HTMLBuilder (creates for response rendering)
  → MovieUtils (static method call for validation)
  → Movie (domain model, instantiation from JSON)

AppConfigUtility
  → AppConfigClient (AWS SDK client)
  → ConfigurationCache (stores/retrieves cached configs)
  → ConfigurationCacheItem (wraps responses with TTL)
  → ConfigurationKey (lookup parameter)

ConfigurationCache
  → ConfigurationKey (map key)
  → ConfigurationCacheItem (map value)
```

## External Dependencies (from pom.xml)

### Runtime Dependencies

| GroupId | ArtifactId | Version | Purpose |
|---------|-----------|---------|---------|
| org.springframework.boot | spring-boot-starter-web | 2.0.5.RELEASE (parent) | Spring MVC, embedded Tomcat |
| software.amazon.awssdk | bom | 2.14.27 | AWS SDK dependency management |
| software.amazon.awssdk | appconfig | (from BOM) | AWS AppConfig client |
| org.json | json | 20200518 | JSON parsing |
| org.apache.logging.log4j | log4j-api | 2.13.3 | Logging API |
| org.apache.logging.log4j | log4j-core | 2.13.3 | Logging implementation |
| javax.validation | validation-api | 2.0.1.Final | Bean validation annotations |
| com.amazonaws.samples | movie-service-utils | 0.1.0 | First-party validation library |

### Test Dependencies

| GroupId | ArtifactId | Version | Purpose |
|---------|-----------|---------|---------|
| org.springframework.boot | spring-boot-starter-test | 2.0.5.RELEASE (parent) | Spring test utilities |
| junit | junit | 4.13.1 | Unit testing framework |
| org.mockito | mockito-all | 1.10.19 | Mocking framework |

### Build Plugins

| Plugin | Version | Purpose |
|--------|---------|---------|
| spring-boot-maven-plugin | (from parent) | Fat JAR packaging |
| maven-compiler-plugin | 3.8.1 | Java compilation with source/target 1.8 |

## Dependency Graph

```
movie-service (main app)
├── spring-boot-starter-web
│   ├── spring-webmvc
│   ├── embedded-tomcat
│   └── spring-boot-autoconfigure
├── software.amazon.awssdk:appconfig
│   └── (managed by BOM 2.14.27)
├── org.json:json
├── log4j-api + log4j-core
├── javax.validation:validation-api
└── movie-service-utils (first-party JAR)
    └── (pre-built, installed from built-library/)
```

## First-Party Library: movie-service-utils

The `movie-service-utils` module is a separate Maven module with its own `pom.xml` targeting Java 17. Pre-built JARs are provided in `built-library/`:
- `0_1_0/movie-service-utils-0.1.0.jar` — Java 8 compatible
- `0_2_0/movie-service-utils-0.2.0.jar` — Java 17 compatible
- `0_3_0/movie-service-utils-0.3.0.jar` — Java 21 compatible

The main app currently references version 0.1.0 (Java 8). The library provides `MovieUtils.isValidMovie(String, int)` for movie name and ID validation.
