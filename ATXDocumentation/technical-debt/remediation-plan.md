# Remediation Plan

## Priority 1: Critical Security — Log4j Vulnerability (Severity: High)

**Action**: Replace Log4j 2.13.3 with SLF4J + Logback

1. Remove `log4j-api` and `log4j-core` dependencies from `pom.xml`
2. Add `spring-boot-starter-logging` (provides SLF4J + Logback via Spring Boot)
3. Replace `log4j2.xml` with `logback-spring.xml`
4. Update all source files importing `org.apache.logging.log4j.*` to use `org.slf4j.*`
   - `MoviesController.java`: Change `LogManager.getLogger()` to `LoggerFactory.getLogger()`
5. Verify logging output format is preserved

**AWS Transformation**: `AWS/early-access-log4j-to-slf4j-migration` handles this automatically.

## Priority 2: Runtime Upgrade — Java 8 to Java 21 (Severity: High)

**Action**: Upgrade Java version, Spring Boot, and migrate javax → jakarta

1. Update `pom.xml`: `<java.version>21</java.version>`
2. Upgrade Spring Boot parent to 3.4.x
3. Migrate `javax.validation` → `jakarta.validation`
4. Migrate `javax.security.cert` → `java.security.cert` in `Security.java`
5. Replace deprecated wrapper constructors in `Math.java`
6. Replace `BigDecimal.ROUND_DOWN` with `RoundingMode.DOWN`
7. Replace `sun.misc.BASE64Encoder` with `java.util.Base64`
8. Update Dockerfile base image to `amazoncorretto:21`
9. Upgrade AWS SDK BOM to 2.31.x
10. Upgrade `org.json:json` to latest
11. Upgrade test dependencies: JUnit 5, Mockito 5.x

**AWS Transformation**: `AWS/java-version-upgrade` handles this automatically.

## Priority 3: Test Modernization (Severity: Low)

**Action**: Migrate to JUnit 5 + Mockito 5

1. Replace `junit:junit:4.13.1` with `org.junit.jupiter:junit-jupiter`
2. Replace `mockito-all:1.10.19` with `mockito-core:5.x` + `mockito-junit-jupiter`
3. Update test annotations: `@RunWith(SpringRunner.class)` → `@ExtendWith(SpringExtension.class)`
4. Update imports: `org.junit.Test` → `org.junit.jupiter.api.Test`
5. Replace `org.mockito.Matchers` with `org.mockito.ArgumentMatchers`

## Priority 4: Build System Cleanup (Severity: Low)

**Action**: Consolidate to single build system

1. Choose Maven or Gradle as the single build system
2. Remove the unused build file to eliminate drift risk
3. Update CI/CD pipelines accordingly

## Recommended Execution Order

```
1. Log4j → SLF4J migration (security-critical, independent)
2. Java 8 → 21 + Spring Boot 3.x (comprehensive upgrade)
3. Test modernization (follows from step 2)
4. Build system consolidation (cleanup)
```

Each step should be validated independently before proceeding to the next.
