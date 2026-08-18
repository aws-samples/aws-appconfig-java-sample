# Outdated Components Analysis

## Runtime and Framework

| Component | Current | Status | EOL Date | Recommended |
|-----------|---------|--------|----------|-------------|
| Java (JDK) | 1.8 | EOL | Mar 2022 (Oracle public) | Java 21 (LTS) |
| Spring Boot | 2.0.5.RELEASE | EOL | Nov 2023 (OSS) | 3.4.x |
| Docker base image | maven:3.6.1-amazoncorretto-8 | Outdated | N/A | amazoncorretto:21 |

## Logging

| Component | Current | Issue | Recommended |
|-----------|---------|-------|-------------|
| log4j-api | 2.13.3 | CVE-2021-44228 (Critical RCE) | SLF4J + Logback (via Spring Boot starter) |
| log4j-core | 2.13.3 | CVE-2021-44228 (Critical RCE) | Remove entirely |

## Production Dependencies

| Dependency | Current | Latest | Gap |
|-----------|---------|--------|-----|
| software.amazon.awssdk:bom | 2.14.27 | 2.31.x | ~17 minor versions behind |
| org.json:json | 20200518 | 20240303 | ~4 years behind |
| javax.validation:validation-api | 2.0.1.Final | Superseded | Use jakarta.validation:jakarta.validation-api 3.x |
| movie-service-utils | 0.1.0 | 0.3.0 | 2 versions behind (first-party) |

## Dev/Build Dependencies

| Dependency | Current | Latest | Notes |
|-----------|---------|--------|-------|
| junit:junit | 4.13.1 | JUnit 5.10.x | Major version upgrade required |
| mockito-all | 1.10.19 | mockito-core 5.x | `mockito-all` artifact discontinued |
| maven-compiler-plugin | 3.8.1 | 3.13.x | Minor update |

## Deprecated API Usage

| API | Location | File:Line | Replacement |
|-----|----------|-----------|-------------|
| `javax.security.cert.*` | Security.java | `src/main/java/.../utils/Security.java:4` | `java.security.cert.*` |
| `javax.validation.Valid` | MoviesController.java | `src/main/java/.../movies/MoviesController.java:30` | `jakarta.validation.Valid` |
| `new Boolean(true)` | Math.java | `src/main/java/.../utils/Math.java` | `Boolean.valueOf(true)` |
| `new Integer(1)` | Math.java | `src/main/java/.../utils/Math.java` | `Integer.valueOf(1)` |
| `BigDecimal.ROUND_DOWN` | Math.java | `src/main/java/.../utils/Math.java` | `RoundingMode.DOWN` |
| `sun.misc.BASE64Encoder` | Encoder.java | `src/main/java/.../utils/Encoder.java` | `java.util.Base64` |
