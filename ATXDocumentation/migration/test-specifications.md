# Test Specifications

## Existing Tests

| Test File | Type | Framework | Coverage Target |
|-----------|------|-----------|----------------|
| MathTest.java | Unit | JUnit 4 | BigDecimal divide operations |
| MockTest.java | Unit | JUnit 4 + Mockito 1.x | Mocking patterns demonstration |
| MovieTest.java | Integration | JUnit 4 + Spring Test | Spring context loading |
| MoviesControllerTest.java | Unit | JUnit 4 + Mockito | Controller with mocked AppConfig |

## Validation Test Cases for Migration

### TC-1: Application Startup
- **Verify**: Application starts without errors on Java 21
- **Method**: Run Spring Boot application, check context loads
- **Success criteria**: No ClassNotFoundException, no IllegalAccessError

### TC-2: Movie List Retrieval (Happy Path)
- **Verify**: `GET /movies/getMovies` returns HTML with movie data
- **Precondition**: AppConfig mock returns valid JSON
- **Success criteria**: HTTP 200, response contains movie names in HTML format

### TC-3: Movie List Fallback
- **Verify**: On AppConfig failure, static movie list is served
- **Precondition**: AppConfig client throws exception
- **Success criteria**: HTTP 200, response contains "Static Movie 1" through "Static Movie 10"

### TC-4: Movie Edit Validation
- **Verify**: Invalid movie names are rejected
- **Input**: Movie with empty name
- **Success criteria**: Returns "editMovieForm" with validation error

### TC-5: Cache TTL Behavior
- **Verify**: Cached configs are served within TTL window
- **Method**: Call getConfiguration twice within TTL
- **Success criteria**: AWS API called only once

### TC-6: Deprecated API Replacement
- **Verify**: All deprecated APIs have been replaced
- **Method**: Grep for `javax.security.cert`, `javax.validation`, `new Integer(`, `new Boolean(`, `BigDecimal.ROUND_`
- **Success criteria**: Zero matches in source files

### TC-7: Logging Framework
- **Verify**: SLF4J is used instead of Log4j
- **Method**: Grep for `org.apache.logging.log4j` imports
- **Success criteria**: Zero Log4j imports; SLF4J imports present

### TC-8: Jakarta Namespace
- **Verify**: javax.validation replaced with jakarta.validation
- **Method**: Grep for `javax.validation` imports
- **Success criteria**: Zero javax.validation imports; jakarta.validation present

## Test Migration Requirements

When upgrading test frameworks:
1. Replace `@RunWith(SpringRunner.class)` with `@ExtendWith(SpringExtension.class)`
2. Replace `org.junit.Test` with `org.junit.jupiter.api.Test`
3. Replace `org.mockito.Matchers` with `org.mockito.ArgumentMatchers`
4. Replace `mockito-all` dependency with `mockito-core` + `mockito-junit-jupiter`
