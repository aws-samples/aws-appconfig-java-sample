# Security Patterns

## Authentication & Authorization

The application does **not** implement authentication or authorization:
- No Spring Security dependency
- No JWT or session management
- All endpoints are publicly accessible
- The `/movies/getMovies` endpoint is also the health check path (CloudFormation template)

## AWS Security

### IAM Role-Based Access
- **Location**: `templates/ecs-cluster.yml`
- **Pattern**: ECS Task Role with AppConfig permissions
- **Permissions**: `appconfig:GetConfiguration`, `appconfig:StartConfigurationSession`
- **Principle of least privilege**: Task role is scoped to AppConfig operations only

### AWS SDK Credential Chain
- **Location**: `AppConfigUtility.java`
- **Pattern**: `AppConfigClient.create()` uses the default credential provider chain
- No hardcoded credentials in source code

## Cryptographic Usage

### javax.security.cert (Deprecated)
- **Location**: `Security.java`
- **Issue**: Uses deprecated `javax.security.cert.X509Certificate.getInstance()` instead of `java.security.cert.CertificateFactory`
- **Risk**: Medium — the deprecated API has been superseded since Java 1.2 and may be removed

## Input Validation

| Location | Validation | Method |
|----------|-----------|--------|
| MoviesController.processUpdateMovie | Movie name validation | `MovieUtils.isValidMovieName()` |
| MoviesController.processUpdateMovie | Bean validation | `@Valid` annotation |
| MovieUtils.isValidMovie | Null check, length bounds, ID positivity | Manual checks |

## Security Vulnerabilities

### Critical: Log4j RCE (CVE-2021-44228)
- **Affected**: `log4j-core:2.13.3`
- **Vector**: Remote code execution via JNDI lookup injection in log messages
- **Impact**: Complete system compromise
- **Status**: Unpatched in current configuration

### Potential: Unvalidated Environment Properties
- **Location**: `MoviesController.java`
- **Issue**: `env.getProperty()` return values are not null-checked before use
- **Risk**: NPE on misconfiguration, not a direct security issue

## Network Security

- **Location**: `templates/ecs-cluster.yml`, `templates/fargate-task.yml`
- **Pattern**: Security groups restrict inbound traffic
- **ALB**: Public-facing on port 80
- **ECS Task**: Only accessible from ALB security group
- **VPC**: Isolated network with Internet Gateway for outbound
