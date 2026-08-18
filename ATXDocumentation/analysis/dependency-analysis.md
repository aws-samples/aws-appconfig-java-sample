# Dependency Analysis

## Internal Dependency Map

```
MoviesController
  ├── Movie (domain model)
  ├── AppConfigUtility (service)
  ├── ConfigurationCache (data store)
  ├── ConfigurationKey (model)
  ├── HTMLBuilder (view)
  └── MovieUtils (validation, external module)

AppConfigUtility
  ├── ConfigurationCache
  ├── ConfigurationCacheItem
  └── ConfigurationKey

ConfigurationCache
  ├── ConfigurationKey (key type)
  └── ConfigurationCacheItem (value type)
```

## External Dependency Risk Assessment

| Dependency | Version | Risk Level | Rationale |
|-----------|---------|-----------|-----------|
| log4j-core | 2.13.3 | **Critical** | Known RCE vulnerability (CVE-2021-44228) |
| log4j-api | 2.13.3 | **High** | Part of vulnerable Log4j stack |
| spring-boot-starter-parent | 2.0.5 | **High** | EOL, no security patches |
| javax.validation | 2.0.1.Final | **Medium** | Superseded, blocks Spring Boot 3.x |
| software.amazon.awssdk:bom | 2.14.27 | **Medium** | Significantly outdated |
| org.json:json | 20200518 | **Low** | Outdated but no critical issues |
| junit:junit | 4.13.1 | **Low** | Test-only, superseded by JUnit 5 |
| mockito-all | 1.10.19 | **Low** | Test-only, artifact discontinued |

## Dependency Freshness

```
Current version age (approximate):
  spring-boot:      2.0.5 → 3.4.x     (~6 years behind)
  log4j:            2.13.3 → 2.23.x    (~4 years behind)
  aws-sdk-v2:       2.14.27 → 2.31.x   (~4 years behind)
  org.json:         20200518 → 20240303 (~4 years behind)
  junit:            4.13.1 → 5.10.x    (~1 major version)
  mockito:          1.10.19 → 5.x      (~4 major versions)
```

## Transitive Dependency Concerns

Spring Boot 2.0.5 manages these transitive versions:
- **Spring Framework**: 5.0.x (EOL)
- **Tomcat embedded**: 8.5.x (approaching EOL)
- **Jackson**: 2.9.x (multiple CVEs in older 2.9 versions)
- **Hibernate Validator**: 6.x (javax namespace)

The Spring Boot parent BOM pins all these transitives — upgrading Spring Boot is the single action that addresses most transitive dependency debt.
