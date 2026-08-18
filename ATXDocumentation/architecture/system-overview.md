# System Overview

## Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Language | Java | 1.8 |
| Framework | Spring Boot | 2.0.5.RELEASE |
| Cloud SDK | AWS SDK for Java v2 | 2.14.27 |
| Cloud Service | AWS AppConfig | N/A |
| Logging | Log4j 2 | 2.13.3 |
| JSON Processing | org.json | 20200518 |
| Build Tool | Maven (primary), Gradle (alternative) | 3.6.1 / N/A |
| Container | Docker (Amazon Corretto 8) | N/A |
| Deployment | AWS ECS Fargate behind ALB | N/A |

## Architecture Style

**Monolithic Spring Boot Microservice** — A single-module REST API application deployed as a containerized service on ECS Fargate. The application follows a simple layered architecture:

```
┌─────────────────────────────────────────┐
│            REST Controller Layer         │
│         (MoviesController.java)          │
├─────────────────────────────────────────┤
│           Utility/Service Layer          │
│  (AppConfigUtility, HTMLBuilder, etc.)   │
├─────────────────────────────────────────┤
│              Caching Layer               │
│  (ConfigurationCache, CacheItem)        │
├─────────────────────────────────────────┤
│          External Service Layer          │
│      (AWS AppConfig via SDK v2)          │
└─────────────────────────────────────────┘
```

## Deployment Model

- **Container**: Multi-stage Docker build using `maven:3.6.1-amazoncorretto-8`
- **Orchestration**: AWS ECS Fargate (serverless containers)
- **Load Balancing**: Application Load Balancer (ALB)
- **Networking**: VPC with 2 public subnets, Internet Gateway
- **Health Check**: `GET /movies/getMovies`
- **Port**: 8080

## Key Architectural Decisions

1. **In-memory caching over external cache**: Uses `ConcurrentHashMap` with TTL-based items rather than Redis/ElastiCache, suitable for single-instance deployments
2. **AWS AppConfig for dynamic configuration**: Movie lists are stored as AppConfig configuration profiles rather than in a database
3. **Fallback pattern**: On AppConfig fetch failure, serves a static movie list as a graceful degradation
4. **HTML rendering server-side**: The controller returns HTML directly rather than JSON, with server-side template building via `HTMLBuilder`

## Source File: [pom.xml](../../pom.xml), [Dockerfile](../../Dockerfile)
