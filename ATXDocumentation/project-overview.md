# Project Overview

## aws-appconfig-java-sample

### Purpose
A demonstration Java 8 Spring Boot microservice that integrates with AWS AppConfig for dynamic application configuration. The application displays a list of free movies fetched from AppConfig, with an in-memory TTL-based caching layer to optimize retrieval.

This project also serves as a reference target for **Amazon Q Developer Agent for Code Transformation**, showcasing the Java 8-to-17/21 upgrade path including dependency modernization, API migration, and framework upgrades.

### Key Characteristics
- **Language**: Java 8 (target: 21)
- **Framework**: Spring Boot 2.0.5.RELEASE (target: 3.4.x)
- **Build**: Maven (primary), Gradle (alternative)
- **Cloud Service**: AWS AppConfig for configuration management
- **Deployment**: Docker on ECS Fargate behind ALB
- **Caching**: In-memory ConcurrentHashMap with TTL (30s default)

### Project Structure Summary
- **11 Java source files** across 4 packages
- **4 test files** using JUnit 4 + Mockito 1.x
- **1 sub-module** (movie-service-utils) providing validation utilities
- **2 CloudFormation templates** for infrastructure provisioning
- **2 CI/CD pipelines** (GitHub Actions + GitLab CI)

### AWS Services Used
- **AWS AppConfig** — Dynamic configuration profiles
- **Amazon ECS (Fargate)** — Serverless container orchestration
- **Amazon ECR** — Container image registry
- **Application Load Balancer** — HTTP traffic distribution
- **AWS IAM** — Task-level permissions for AppConfig access

### Related Documentation
- [Architecture Overview](architecture/system-overview.md)
- [Technical Debt Report](technical-debt-report.md)
- [Migration Plan](migration/component-order.md)
