# Structural Diagrams

## Component Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                     movie-service (Spring Boot)                   │
│                                                                   │
│  ┌──────────────────┐     ┌────────────────────────────────┐    │
│  │  MoviesController │────▶│       AppConfigUtility         │    │
│  │  (REST endpoint)  │     │  (AWS AppConfig integration)   │    │
│  └────────┬──────────┘     └───────────────┬────────────────┘    │
│           │                                 │                     │
│           │                                 ▼                     │
│           │                 ┌────────────────────────────────┐    │
│           │                 │     ConfigurationCache          │    │
│           │                 │  (ConcurrentHashMap + TTL)      │    │
│           │                 └────────────────────────────────┘    │
│           │                                                       │
│           ▼                                                       │
│  ┌──────────────────┐     ┌────────────────────────────────┐    │
│  │   HTMLBuilder     │     │       MovieUtils               │    │
│  │  (view rendering) │     │  (first-party lib: validation)  │    │
│  └──────────────────┘     └────────────────────────────────┘    │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
```

## Package Dependency Graph

```
com.amazonaws.samples.appconfig.movies
    │
    ├──▶ com.amazonaws.samples.appconfig.utils
    │         │
    │         └──▶ com.amazonaws.samples.appconfig.cache
    │                    │
    │                    └──▶ com.amazonaws.samples.appconfig.model
    │
    └──▶ com.amazonaws.samples.appconfig.model
```

## Class Relationships

```
┌─────────────────────┐
│  MoviesApplication  │  @SpringBootApplication
└─────────────────────┘

┌─────────────────────┐         ┌──────────────────┐
│  MoviesController   │────────▶│  Movie           │
│  - PAIDMOVIES[]     │         │  - id: long      │
│  - cache            │         │  - movieName     │
│  - client           │         └──────────────────┘
│  + movie()          │
│  + processUpdate()  │         ┌──────────────────────────────┐
│  - getOrDefault()   │────────▶│  AppConfigUtility            │
└─────────────────────┘         │  - client: AppConfigClient   │
                                │  - cache: ConfigurationCache │
                                │  + getConfiguration()        │
                                │  + updateConfiguration()     │
                                └──────────────┬───────────────┘
                                               │
                                               ▼
┌─────────────────────────────┐    ┌───────────────────────────┐
│  ConfigurationCache         │    │  ConfigurationCacheItem   │
│  - map: ConcurrentHashMap   │───▶│  - response              │
│  + get(key)                 │    │  - refreshTime           │
│  + put(key, item)           │    │  + isStale()             │
└─────────────────────────────┘    └───────────────────────────┘
              │
              ▼
┌─────────────────────────────┐
│  ConfigurationKey           │
│  - application: String      │
│  - environment: String      │
│  - configuration: String    │
│  + equals() / hashCode()    │
└─────────────────────────────┘
```
