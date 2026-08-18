# Maintenance Burden

## High Maintenance Areas

### 1. Security Vulnerability Management

The Log4j 2.13.3 dependency requires immediate attention. Any deployment of this application in its current state exposes the system to remote code execution via Log4Shell (CVE-2021-44228). This creates ongoing operational risk and potential compliance failures.

- **Affected files**: `pom.xml`, `src/main/resources/log4j2.xml`
- **Impact**: Cannot pass security audits; blocks production deployments

### 2. Framework Compatibility Constraints

Spring Boot 2.0.5 constrains the entire dependency tree. Upgrading any single dependency often requires upgrading Spring Boot first, creating a cascade effect:
- Spring Boot 2.x → requires Java 8-17
- Spring Boot 3.x → requires Java 17+, jakarta namespace
- AWS SDK updates may conflict with older Spring dependency management

### 3. javax/jakarta Namespace Split

The project uses both `javax.validation` and `javax.security.cert` namespaces. Migration to Spring Boot 3.x requires converting all javax references to jakarta equivalents. This affects:
- `MoviesController.java` — `javax.validation.Valid`
- `Security.java` — `javax.security.cert.*` (entire file)

### 4. Deprecated API Surface

Multiple utility classes (`Math.java`, `Encoder.java`, `Security.java`) use deprecated Java APIs that produce compiler warnings and may be removed in future JDK releases. While functional today, they increase maintenance burden through:
- Compiler warning noise
- Risk of breakage on JDK upgrades
- Difficulty finding documentation for deprecated APIs

## Moderate Maintenance Areas

### 5. Test Framework Gap

JUnit 4 and Mockito 1.x are two major versions behind. New test patterns (parameterized tests, extensions, BDD-style mocking) are unavailable. The `mockito-all` artifact bundles all dependencies which can cause classpath conflicts.

### 6. Multi-Build System Complexity

The project maintains both `pom.xml` (Maven) and `build.gradle` (Gradle) with slightly different Spring Boot versions (2.0.5 vs 2.3.0). This dual-build configuration doubles the effort for dependency updates and can lead to inconsistencies.

## Maintenance Effort Assessment

| Area | Severity | Effort Required |
|------|----------|----------------|
| Log4j vulnerability remediation | High | Significant (logging framework swap) |
| Java 8 → 17/21 migration | High | Significant (API changes, namespace migration) |
| Spring Boot 2.x → 3.x upgrade | High | Significant (breaking changes, dependency cascade) |
| AWS SDK version update | Medium | Moderate (API compatibility generally maintained) |
| Test framework modernization | Low | Moderate (rewrite test annotations and mocking) |
