# ATXDocumentation — aws-appconfig-java-sample

## Project Summary

A **Java 8 Spring Boot 2.0.5** microservice that demonstrates AWS AppConfig integration for dynamic configuration management. Deployed as a Docker container on ECS Fargate behind an ALB, it serves a movie list fetched from AppConfig with an in-memory caching layer.

**Key Technical Debt**: Java 8 (EOL), Spring Boot 2.0.5 (EOL), Log4j 2.13.3 (CVE-2021-44228 critical vulnerability), javax namespace usage.

---

## Quick Navigation

### [Technical Debt Report](technical-debt-report.md) ⚠️
**Start here** — Executive summary of all technical debt with AWS transformation recommendations.

---

### Architecture
| Document | Description |
|----------|-------------|
| [System Overview](architecture/system-overview.md) | Technology stack, deployment model, key decisions |
| [Components](architecture/components.md) | All system components with responsibilities |
| [Dependencies](architecture/dependencies.md) | Internal and external dependency mapping |
| [Patterns](architecture/patterns.md) | Design patterns and anti-patterns identified |

### Behavior
| Document | Description |
|----------|-------------|
| [Business Logic](behavior/business-logic.md) | Business rules for all components |
| [Workflows](behavior/workflows.md) | Application-level process flows |
| [Decision Logic](behavior/decision-logic.md) | All decision points in code |
| [Error Handling](behavior/error-handling.md) | Exception patterns and recovery |

### Technical Debt
| Document | Description |
|----------|-------------|
| [Summary](technical-debt/summary.md) | Overview of all debt categories |
| [Outdated Components](technical-debt/outdated-components.md) | Full inventory of outdated dependencies |
| [Maintenance Burden](technical-debt/maintenance-burden.md) | Areas requiring attention |
| [Remediation Plan](technical-debt/remediation-plan.md) | Prioritized action items |

### Reference
| Document | Description |
|----------|-------------|
| [Program Structure](reference/program-structure.md) | Package hierarchy and file inventory |
| [Interfaces](reference/interfaces.md) | Type hierarchy and contracts |
| [Data Models](reference/data-models.md) | Domain objects and data structures |
| [API Reference](reference/api-reference.md) | REST endpoints and public methods |
| [Modules](reference/modules.md) | Module organization and versioning |

### Analysis
| Document | Description |
|----------|-------------|
| [Code Metrics](analysis/code-metrics.md) | Lines, complexity, quality indicators |
| [Complexity Analysis](analysis/complexity-analysis.md) | Hotspots and maintainability |
| [Dependency Analysis](analysis/dependency-analysis.md) | Risk assessment of all dependencies |
| [Security Patterns](analysis/security-patterns.md) | Authentication, IAM, vulnerabilities |
| [Tech Debt](analysis/tech-debt.md) | Comprehensive debt assessment |

### Diagrams
| Document | Description |
|----------|-------------|
| [Structural](diagrams/structural/component-diagrams.md) | Component and class diagrams |
| [Behavioral](diagrams/behavioral/sequence-diagrams.md) | Sequence and activity diagrams |
| [Architecture](diagrams/architecture/system-context.md) | System context and deployment |

### Migration
| Document | Description |
|----------|-------------|
| [Component Order](migration/component-order.md) | Dependency-based migration sequence |
| [Test Specifications](migration/test-specifications.md) | Validation test cases |
| [Validation Criteria](migration/validation-criteria.md) | Success/rollback criteria |

### Specialized
| Document | Description |
|----------|-------------|
| [Infrastructure & Deployment](specialized/infrastructure-deployment.md) | CloudFormation, Docker, CI/CD |

---

## Project Overview

See [project-overview.md](project-overview.md) for detailed project context.

---

## Source Code References

| Component | File Path |
|-----------|-----------|
| Application Entry | `src/main/java/com/amazonaws/samples/appconfig/movies/MoviesApplication.java` |
| REST Controller | `src/main/java/com/amazonaws/samples/appconfig/movies/MoviesController.java` |
| Domain Model | `src/main/java/com/amazonaws/samples/appconfig/movies/Movie.java` |
| AppConfig Integration | `src/main/java/com/amazonaws/samples/appconfig/utils/AppConfigUtility.java` |
| Cache | `src/main/java/com/amazonaws/samples/appconfig/cache/ConfigurationCache.java` |
| Build Configuration | `pom.xml` |
| Docker | `Dockerfile` |
| Infrastructure | `templates/ecs-cluster.yml`, `templates/fargate-task.yml` |
