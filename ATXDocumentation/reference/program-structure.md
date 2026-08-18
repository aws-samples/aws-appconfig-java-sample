# Program Structure

## Package Hierarchy

```
com.amazonaws.samples.appconfig/
├── cache/
│   ├── ConfigurationCache.java
│   └── ConfigurationCacheItem.java
├── model/
│   └── ConfigurationKey.java
├── movies/
│   ├── Movie.java
│   ├── MoviesApplication.java
│   └── MoviesController.java
└── utils/
    ├── AppConfigUtility.java
    ├── Encoder.java
    ├── HTMLBuilder.java
    ├── Math.java
    ├── MovieUtils.java (in movie-service-utils module)
    └── Security.java
```

## Module Structure

### Main Module: `movie-service`
- **Group ID**: `org.amazonaws.samples`
- **Artifact ID**: `movie-service`
- **Version**: `0.1.0`
- **Build**: Maven (`pom.xml`) + Gradle alternative (`build.gradle`)
- **Source root**: `src/main/java/`
- **Test root**: `src/test/java/`
- **Resources**: `src/main/resources/`

### Sub-Module: `movie-service-utils`
- **Group ID**: `com.amazonaws.samples`
- **Artifact ID**: `movie-service-utils`
- **Version**: `0.1.0` (current), `0.2.0` (Java 17), `0.3.0` (Java 21)
- **Source root**: `movie-service-utils/src/main/java/`
- **Pre-built JARs**: `movie-service-utils/built-library/`

## File Inventory

| File | Package | Lines | Type |
|------|---------|-------|------|
| MoviesApplication.java | movies | ~12 | Application entry |
| MoviesController.java | movies | ~140 | REST controller |
| Movie.java | movies | ~30 | Domain model |
| AppConfigUtility.java | utils | ~80 | Service utility |
| HTMLBuilder.java | utils | ~40 | View utility |
| ConfigurationCache.java | cache | ~25 | Cache store |
| ConfigurationCacheItem.java | cache | ~50 | Cache entry |
| ConfigurationKey.java | model | ~45 | Composite key |
| Encoder.java | utils | ~25 | Demo (deprecated APIs) |
| Math.java | utils | ~35 | Demo (deprecated APIs) |
| Security.java | utils | ~25 | Demo (deprecated APIs) |
| MovieUtils.java | utils (ext) | ~20 | Validation utility |

## Configuration Files

| File | Purpose |
|------|---------|
| `src/main/resources/application.yml` | Spring Boot config (port, AppConfig settings) |
| `src/main/resources/log4j2.xml` | Log4j 2 logging configuration |
| `pom.xml` | Maven build definition |
| `build.gradle` | Gradle build definition (alternative) |
| `Dockerfile` | Container build instructions |
| `templates/ecs-cluster.yml` | CloudFormation: VPC + ECS cluster |
| `templates/fargate-task.yml` | CloudFormation: Fargate task + ALB |
