# Interfaces and Type Hierarchy

## Interfaces

This project does not define custom interfaces. All types are concrete classes.

## Class Hierarchy

```
java.lang.Object
├── MoviesApplication (Spring Boot app)
├── MoviesController (@RestController)
├── Movie (POJO)
├── AppConfigUtility
├── ConfigurationCache
├── ConfigurationCacheItem
├── ConfigurationKey
├── HTMLBuilder
├── MovieUtils
├── Encoder
├── Math
└── Security
```

## Notable Type Contracts

### ConfigurationKey — HashMap Key Contract
- Implements `equals()` and `hashCode()` based on (application, environment, configuration)
- Used as key in `ConcurrentHashMap<ConfigurationKey, ConfigurationCacheItem>`

### Movie — Domain Object Contract
- Fields: `id` (long), `movieName` (String)
- Constructor: `Movie(long id, String movieName)`
- Getters: `getId()`, `getMovieName()`
- Used with `@Valid` annotation for bean validation

### MoviesController — Spring Contract
- Annotated `@RestController` — return values are serialized as HTTP response body
- Uses `@Autowired` for `Environment` injection
- Uses `@GetMapping`, `@RequestMapping` for endpoint binding

## Annotations Used

| Annotation | Source | Usage |
|-----------|--------|-------|
| `@SpringBootApplication` | Spring Boot | `MoviesApplication` |
| `@RestController` | Spring MVC | `MoviesController` |
| `@GetMapping` | Spring MVC | `movie()` method |
| `@RequestMapping` | Spring MVC | `processUpdateMovie()` method |
| `@Autowired` | Spring | `Environment` field injection |
| `@PathVariable` | Spring MVC | Path parameter binding |
| `@Valid` | javax.validation | Request body validation |
| `@RunWith` | JUnit 4 | Test runner configuration |
| `@Test` | JUnit 4 | Test method marking |
