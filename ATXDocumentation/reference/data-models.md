# Data Models

## Domain Models

### Movie
- **File**: `src/main/java/com/amazonaws/samples/appconfig/movies/Movie.java`
- **Purpose**: Represents a movie entity

| Field | Type | Description |
|-------|------|-------------|
| id | long | Unique movie identifier |
| movieName | String | Display name of the movie |

**Constructor**: `Movie(long id, String movieName)`

### ConfigurationKey
- **File**: `src/main/java/com/amazonaws/samples/appconfig/model/ConfigurationKey.java`
- **Purpose**: Composite key for AWS AppConfig lookups

| Field | Type | Description |
|-------|------|-------------|
| application | String | AppConfig application name |
| environment | String | AppConfig environment name |
| configuration | String | AppConfig configuration profile name |

**Contract**: Implements `equals()` and `hashCode()` for use as HashMap key.

### ConfigurationCacheItem
- **File**: `src/main/java/com/amazonaws/samples/appconfig/cache/ConfigurationCacheItem.java`
- **Purpose**: Wraps a cached configuration response with TTL metadata

| Field | Type | Description |
|-------|------|-------------|
| response | GetConfigurationResponse | Cached AWS AppConfig response |
| refreshTime | long | Timestamp (millis) when item becomes stale |
| cachedException | Exception | Cached exception for negative caching |

## External Data Structures

### AWS AppConfig Response (JSON)
```json
{
  "movies": [
    {
      "id": 1,
      "movieName": "Movie Title"
    }
  ]
}
```

### application.yml Configuration
```yaml
server:
  address: 0.0.0.0
  port: 8080
appconfig:
  application: MyContainerApplication
  environment: MyContainerApplicationProductionEnvironment
  config: MyContainerApplicationConfigurationProfile
  cacheTtlInSeconds: 30
```

## Data Flow

```
AWS AppConfig (JSON) → GetConfigurationResponse → ConfigurationCacheItem
                                                        ↓
                                                  JSON parsing
                                                        ↓
                                                  Movie[] array
                                                        ↓
                                                  HTML string (response)
```
