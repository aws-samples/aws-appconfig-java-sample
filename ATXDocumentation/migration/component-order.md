# Component Migration Order

## Dependency-Based Migration Sequence

Based on the internal dependency graph, components should be migrated in this order (leaf dependencies first):

### Phase 1: Foundation (No internal dependents)
1. **ConfigurationKey** — Pure data model, no dependencies on other project components
2. **Movie** — Pure POJO, no project dependencies
3. **MovieUtils** (movie-service-utils) — Independent library, upgrade to 0.3.0

### Phase 2: Cache Layer
4. **ConfigurationCacheItem** — Depends only on AWS SDK types
5. **ConfigurationCache** — Depends on ConfigurationKey, ConfigurationCacheItem

### Phase 3: Service Layer
6. **AppConfigUtility** — Depends on ConfigurationCache, ConfigurationKey, AWS SDK client
7. **HTMLBuilder** — No project dependencies, only produces HTML

### Phase 4: Deprecated Utilities
8. **Security.java** — Migrate javax.security.cert → java.security.cert
9. **Math.java** — Replace deprecated constructors and rounding modes
10. **Encoder.java** — Replace sun.misc.BASE64Encoder with java.util.Base64

### Phase 5: Controller Layer (Top of dependency tree)
11. **MoviesController** — Depends on all other components; migrate javax.validation → jakarta.validation

### Phase 6: Application Bootstrap
12. **MoviesApplication** — No code changes typically needed

### Phase 7: Infrastructure
13. **Dockerfile** — Update base image to amazoncorretto:21
14. **pom.xml** — All dependency version updates
15. **build.gradle** — Sync with pom.xml changes or remove

## Migration Dependencies Graph

```
Phase 1 (leaf)     Phase 2         Phase 3          Phase 5 (root)
─────────────      ──────          ──────           ──────────────
ConfigurationKey ──▶ Cache ──────▶ AppConfigUtility ──▶ MoviesController
Movie                CacheItem                          │
MovieUtils                         HTMLBuilder ─────────┘
```
