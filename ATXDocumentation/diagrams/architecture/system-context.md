# Architecture Diagrams

## System Context

```
┌─────────────────────────────────────────────────────────────────────┐
│                           AWS Cloud                                   │
│                                                                       │
│  ┌─────────┐     ┌───────┐     ┌──────────────────────────────┐    │
│  │ Internet│────▶│  ALB  │────▶│  ECS Fargate                  │    │
│  │  Users  │     │       │     │  ┌────────────────────────┐   │    │
│  └─────────┘     └───────┘     │  │    movie-service       │   │    │
│                                 │  │  (Spring Boot + Java 8)│   │    │
│                                 │  └───────────┬────────────┘   │    │
│                                 └──────────────┼────────────────┘    │
│                                                │                     │
│                                                ▼                     │
│                                 ┌──────────────────────────────┐    │
│                                 │      AWS AppConfig            │    │
│                                 │  (Configuration Management)   │    │
│                                 └──────────────────────────────┘    │
│                                                                       │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │  VPC                                                          │   │
│  │  ├── Public Subnet 1 (AZ-1)                                  │   │
│  │  ├── Public Subnet 2 (AZ-2)                                  │   │
│  │  ├── Internet Gateway                                         │   │
│  │  └── Security Groups (ALB + ECS)                              │   │
│  └──────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
```

## Integration Patterns

```
┌──────────────┐          ┌──────────────────┐
│  HTTP Client │──REST───▶│ MoviesController │
└──────────────┘          └────────┬─────────┘
                                    │
                          ┌─────────▼──────────┐
                          │  AppConfigUtility   │
                          │  (Cache-Aside)      │
                          └─────┬─────────┬────┘
                                │         │
                      [Cache Hit]    [Cache Miss]
                                │         │
                    ┌───────────▼┐   ┌────▼────────────┐
                    │ Local Cache │   │ AWS AppConfig    │
                    │ (in-memory) │   │ (GetConfiguration│
                    └─────────────┘   │  API call)       │
                                      └──────────────────┘
```

## Security Boundaries

```
┌─────────────────────────────────────────────────────────┐
│ PUBLIC ZONE                                              │
│   Internet Users → ALB (port 80)                        │
├─────────────────────────────────────────────────────────┤
│ PRIVATE ZONE (VPC)                                      │
│   ALB → ECS Task (port 8080)                            │
│         Security Group: ALB ingress only                │
├─────────────────────────────────────────────────────────┤
│ AWS SERVICE ZONE                                        │
│   ECS Task → AWS AppConfig (via IAM Task Role)          │
│   Permissions: appconfig:GetConfiguration               │
│                appconfig:StartConfigurationSession       │
└─────────────────────────────────────────────────────────┘
```

## Deployment Architecture

```
┌────────────────────────────────────────────────────┐
│ Docker Image Build (Multi-Stage)                    │
│                                                     │
│ Stage 1: maven:3.6.1-amazoncorretto-8              │
│   └── mvn package → movie-service-0.1.0.jar       │
│                                                     │
│ Stage 2: amazoncorretto:8 (runtime)                │
│   └── java -cp BOOT-INF/...                       │
└────────────────────┬───────────────────────────────┘
                     │ push to ECR
                     ▼
┌────────────────────────────────────────────────────┐
│ ECS Fargate Task Definition                         │
│   CPU: configurable                                 │
│   Memory: configurable                              │
│   Image: ECR repository                             │
│   Port: 8080                                        │
│   Health Check: GET /movies/getMovies               │
└────────────────────────────────────────────────────┘
```
