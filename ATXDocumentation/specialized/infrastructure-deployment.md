# Specialized Documentation: Infrastructure and Deployment

## AWS CloudFormation Templates

### ecs-cluster.yml
- **Location**: `templates/ecs-cluster.yml`
- **Purpose**: Creates the base infrastructure for ECS deployment
- **Resources created**:
  - VPC with CIDR block
  - 2 Public Subnets (in different AZs)
  - Internet Gateway + Route Table
  - ECR Repository (for Docker images)
  - ECS Cluster
  - Security Group (inbound port 80)
  - IAM Roles:
    - **ECS Task Role**: Allows `appconfig:GetConfiguration`, `appconfig:StartConfigurationSession`
    - **Auto Scaling Role**: ECS service auto-scaling
    - **Task Execution Role**: ECR image pull, CloudWatch Logs

### fargate-task.yml
- **Location**: `templates/fargate-task.yml`
- **Purpose**: Defines the Fargate service and load balancer
- **Resources created**:
  - Application Load Balancer
  - ALB Security Group
  - ECS Fargate Service
  - Task Definition (container definition, port mapping)
  - Target Group (health check: `/movies/getMovies`)
  - Listener Rules

## Docker Configuration

### Dockerfile (Multi-Stage Build)
```
Stage 1 (build):
  Base: maven:3.6.1-amazoncorretto-8
  Action: mvn package -DskipTests
  Output: target/movie-service-0.1.0.jar (extracted)

Stage 2 (runtime):
  Base: amazoncorretto:8
  Command: java -cp app:app/lib/* com.amazonaws.samples.appconfig.movies.MoviesApplication
  Port: 8080
```

## CI/CD Pipelines

### GitHub Actions (`q-code-transformation.yml`)
- **Trigger**: Push to `Q-TRANSFORM-issue-*` branches
- **Features**: Dynamic Java version detection from commit messages
- **Steps**: Install movie-service-utils → Build → Verify → Package

### GitLab CI (`.gitlab-ci.yml`)
- **Stages**: .pre → build → test → package
- **Features**: Detects Java version from pom.xml, uses matching Maven Docker image
- **Docker build**: Kaniko-based (rootless)

## Application Configuration

### application.yml
| Property | Value | Purpose |
|----------|-------|---------|
| server.address | 0.0.0.0 | Bind to all interfaces |
| server.port | 8080 | HTTP port |
| appconfig.application | MyContainerApplication | AppConfig application name |
| appconfig.environment | MyContainerApplicationProductionEnvironment | AppConfig environment |
| appconfig.config | MyContainerApplicationConfigurationProfile | AppConfig profile |
| appconfig.cacheTtlInSeconds | 30 | Cache TTL for AppConfig responses |
