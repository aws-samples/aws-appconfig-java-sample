# Validation Criteria

## Migration Success Criteria

### Build Validation
- [ ] Project compiles without errors on Java 21
- [ ] All unit tests pass
- [ ] Spring Boot application context loads successfully
- [ ] Docker image builds successfully with new base image

### Dependency Validation
- [ ] No Log4j dependencies remain in dependency tree
- [ ] Spring Boot version ≥ 3.4.x
- [ ] AWS SDK BOM version ≥ 2.31.x
- [ ] No javax.validation or javax.security.cert imports in source
- [ ] movie-service-utils version ≥ 0.3.0

### Functional Validation
- [ ] `GET /movies/getMovies` returns valid HTML response
- [ ] Fallback to static movies works on AppConfig failure
- [ ] `POST /movies/{movie}/edit` validates and processes correctly
- [ ] Cache TTL behavior preserved (30-second default)
- [ ] Logging output matches expected format (now via SLF4J/Logback)

### Code Quality Validation
- [ ] No deprecated API usage (sun.misc, javax.security.cert, wrapper constructors)
- [ ] No compiler warnings for deprecated API usage
- [ ] All `System.out.println` calls removed or replaced with logger
- [ ] `BigDecimal.ROUND_DOWN` replaced with `RoundingMode.DOWN`

### Security Validation
- [ ] No known CVEs in dependency tree (particularly Log4Shell)
- [ ] IAM permissions unchanged (AppConfig access only)
- [ ] No new endpoints or authentication bypasses introduced

### Infrastructure Validation
- [ ] Dockerfile uses `amazoncorretto:21` base image
- [ ] ECS task definition compatible with updated image
- [ ] Health check endpoint (`/movies/getMovies`) still functional
- [ ] CloudFormation templates still valid

## Rollback Criteria

Migration should be rolled back if:
- Application fails to start on target Java version
- More than 20% of existing tests fail after migration
- AppConfig integration is broken (cannot fetch/update configurations)
- Docker image size increases by more than 50%
