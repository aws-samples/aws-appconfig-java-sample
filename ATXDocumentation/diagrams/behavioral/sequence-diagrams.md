# Behavioral Diagrams

## Sequence Diagram: GET /movies/getMovies

```
Client          MoviesController      AppConfigUtility    ConfigurationCache    AWS AppConfig
  │                    │                     │                    │                  │
  │──GET /movies/──────▶                     │                    │                  │
  │  getMovies         │                     │                    │                  │
  │                    │──new AppConfigUtility(client, cache, ttl, clientId)         │
  │                    │                     │                    │                  │
  │                    │──getConfiguration(key)──▶                │                  │
  │                    │                     │──get(key)──────────▶                  │
  │                    │                     │◀─────CacheItem─────│                  │
  │                    │                     │                    │                  │
  │                    │                     │──[if stale/miss]───────getConfig()───▶│
  │                    │                     │◀──────────────────────response────────│
  │                    │                     │──put(key, item)────▶                  │
  │                    │                     │                    │                  │
  │                    │◀─GetConfigResponse───│                    │                  │
  │                    │                     │                    │                  │
  │                    │──parse JSON                              │                  │
  │                    │──build Movie[]                           │                  │
  │                    │──HTMLBuilder.getMoviesHtml()             │                  │
  │◀───HTML response───│                     │                    │                  │
  │                    │                     │                    │                  │
```

## Activity Diagram: Movie Retrieval

```
         ┌─────────┐
         │  START  │
         └────┬────┘
              ▼
    ┌─────────────────┐
    │ Parse cache TTL │
    │ from app config │
    └────────┬────────┘
              ▼
    ┌─────────────────┐
    │ Create AppConfig│
    │ Utility instance│
    └────────┬────────┘
              ▼
    ┌─────────────────┐
    │ Get config from │──── Exception ────┐
    │ AppConfig/Cache │                   │
    └────────┬────────┘                   ▼
              │                  ┌─────────────────┐
              │                  │ Log error       │
              ▼                  │ Use PAIDMOVIES  │
    ┌─────────────────┐         └────────┬────────┘
    │ Parse JSON      │                   │
    │ response        │                   │
    └────────┬────────┘                   │
              ▼                            │
    ┌─────────────────┐                   │
    │ Map to Movie[]  │                   │
    └────────┬────────┘                   │
              ▼                            ▼
    ┌─────────────────────────────────────────┐
    │       HTMLBuilder.getMoviesHtml()        │
    └────────────────────┬────────────────────┘
                          ▼
                   ┌─────────┐
                   │   END   │
                   └─────────┘
```

## State Diagram: ConfigurationCacheItem Lifecycle

```
    ┌──────────┐     put()      ┌──────────┐
    │  ABSENT  │───────────────▶│  FRESH   │
    └──────────┘                └────┬─────┘
         ▲                           │
         │                    time > refreshTime
         │                           │
         │ eviction              ┌───▼─────┐
         │ (not implemented)     │  STALE  │
         │                       └───┬─────┘
         │                           │
         │                    refresh from API
         │                           │
         │                       ┌───▼─────┐
         └───────────────────────│  FRESH  │
                                 └─────────┘
```
