# Technical Debt Assessment

## Summary

This codebase has **High** overall technical debt primarily driven by security vulnerabilities and EOL runtime/framework dependencies. The debt is concentrated in the build configuration (`pom.xml`) rather than in application logic.

## Debt Inventory

### Category 1: Security Debt (Severity: High)

| Item | Current State | Target State | Risk |
|------|--------------|--------------|------|
| Log4j 2.13.3 | Vulnerable to CVE-2021-44228 | Replace with SLF4J + Logback | Critical RCE |
| Spring Boot 2.0.5 transitives | Pins vulnerable Jackson, Tomcat versions | Upgrade to 3.4.x | Multiple CVEs |

### Category 2: Runtime/Framework Debt (Severity: High)

| Item | Current State | Target State | Blocker |
|------|--------------|--------------|---------|
| Java 1.8 | EOL | Java 21 LTS | Requires code changes for deprecated APIs |
| Spring Boot 2.0.5 | EOL | 3.4.x | Requires javax → jakarta migration |
| javax.validation | Legacy namespace | jakarta.validation | Blocks Spring Boot 3.x |
| javax.security.cert | Deprecated since Java 9 | java.security.cert | Migration required |

### Category 3: Dependency Debt (Severity: Medium)

| Item | Current | Target | Impact |
|------|---------|--------|--------|
| AWS SDK v2 BOM | 2.14.27 | 2.31.x | Missing features and fixes |
| org.json | 20200518 | 20240303 | Minor |
| movie-service-utils | 0.1.0 | 0.3.0 | Method signature change |

### Category 4: Code Quality Debt (Severity: Low)

| Item | Location | Issue |
|------|----------|-------|
| Code duplication | MoviesController | Two methods share ~30 lines of identical code |
| Per-request instantiation | MoviesController | AppConfigUtility not managed by Spring |
| Mixed logging | MoviesController | System.out alongside Log4j |
| Deprecated constructors | Math.java | `new Integer()`, `new Boolean()` |
| Deprecated rounding | Math.java | `BigDecimal.ROUND_DOWN` constant |
| Deprecated encoding | Encoder.java | `sun.misc.BASE64Encoder` |

### Category 5: Test Debt (Severity: Low)

| Item | Current | Issue |
|------|---------|-------|
| JUnit 4 | 4.13.1 | Superseded by JUnit 5 |
| Mockito | 1.10.19 | `mockito-all` discontinued, API deprecated |
| Coverage | Low | Only 4 test files for 11 source files |
| Test patterns | `@RunWith(SpringRunner.class)` | Should use `@ExtendWith(SpringExtension.class)` |

## Debt Correlation

The debts are interconnected:
1. Upgrading Spring Boot (Category 2) resolves most of Category 1 (security transitives)
2. Java upgrade (Category 2) is prerequisite for Spring Boot 3.x
3. javax → jakarta (Category 2) is required by Spring Boot 3.x
4. Test modernization (Category 5) is naturally addressed during Java upgrade
