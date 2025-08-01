# DevToolkit Backend

Spring Boot backend API for the Developer Utility Toolkit.

## 🚀 Features

- **Base64 Encode/Decode** - Convert text to/from Base64 encoding
- **Hash Generator** - Generate MD5, SHA-1, SHA-256, SHA-512 hashes
- **JWT Decoder/Verifier** - Decode and verify JSON Web Tokens
- **Jasypt Encrypt/Decrypt** - Encrypt and decrypt text using Jasypt
- **CRON Expression Evaluator** - Evaluate and describe CRON expressions
- **Text Diff Comparison** - Compare texts and show differences
- **URL Encoder/Decoder** - URL encode and decode text
- **UUID Generator** - Generate single or multiple UUIDs
- **Timestamp Converter** - Convert between timestamp formats
- **Format Converter** - Convert between JSON, YAML, and XML
- **cURL Generator** - Generate cURL commands from request parameters
- **SQL Formatter** - Format SQL queries for readability
- **Regex Tester** - Test regex patterns against sample text
- **Image to PDF Converter** - Convert multiple images to PDF

## 🛠️ Tech Stack

- **Spring Boot 3** with Java 17
- **Spring Security** for security
- **Spring Web** for REST APIs
- **Jasypt** for encryption
- **JWT** for token handling
- **Apache PDFBox** for PDF generation
- **Maven** for dependency management
- **YAML Configuration** for better readability

## 📦 Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.8+

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd devtoolkit-backend
   ```

2. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Access the API**
   - API Base URL: `http://localhost:8080/api`
   - Health Check: `http://localhost:8080/actuator/health`
   - API Documentation: See [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

## 🔧 Configuration

The application uses YAML configuration files for better readability and structure:

- `application.yml` - Base configuration
- `application-dev.yml` - Development environment
- `application-prod.yml` - Production environment
- `application-sit.yml` - System Integration Test environment
- `application-stage.yml` - Staging environment

### Environment Variables

| **Variable** | **Description** | **Default** |
|--------------|-----------------|-------------|
| `JWT_SECRET` | JWT secret key | `your-256-bit-secret-key-here` |
| `JWT_EXPIRATION` | JWT expiration time (ms) | `86400000` |
| `CORS_ALLOWED_ORIGINS` | Allowed CORS origins | `http://localhost:3000` |
| `MAX_FILE_SIZE` | Maximum file upload size | `10MB` |
| `MAX_REQUEST_SIZE` | Maximum request size | `10MB` |
| `PORT` | Server port | `8080` |

## 📚 API Documentation

For comprehensive API documentation with sample requests and responses, see:

**[📖 API_DOCUMENTATION.md](API_DOCUMENTATION.md)**

The documentation includes:
- Complete endpoint reference
- Sample requests and responses
- Error handling examples
- Frontend integration examples
- JavaScript/TypeScript code samples
- React hook examples

## 🏗️ Architecture

### Feature-Wise Package Structure

The backend follows Google's production-grade feature-wise folder structure:

```
com.devtoolkit/
├── DevToolkitApplication.java          # Main application class
├── base64/                             # Base64 encoding/decoding feature
│   ├── Base64Controller.java
│   ├── dto/
│   │   ├── Base64Request.java
│   │   └── Base64Response.java
│   ├── constants/
│   │   └── Base64Constants.java
│   ├── validation/
│   │   └── Base64RequestValidator.java
│   ├── config/
│   │   └── Base64Config.java
│   ├── api/
│   │   └── IBase64Resources.java
│   └── service/
│       ├── Base64Service.java
│       └── Base64ServiceImpl.java
├── hash/                               # Hash generation feature
├── jwt/                                # JWT token handling feature
├── jasypt/                             # Encryption feature
├── cron/                               # CRON evaluation feature
├── diff/                               # Text diff comparison feature
├── urlencoder/                         # URL encoding/decoding feature
├── uuidgenerator/                      # UUID generation feature
├── timestampconverter/                 # Timestamp conversion feature
├── formatconverter/                    # Format conversion feature
├── curlgenerator/                      # cURL command generation feature
├── sqlformatter/                       # SQL formatting feature
├── regextester/                        # Regex testing feature
├── imagetopdf/                         # Image to PDF conversion feature
└── common/                             # Shared components
    ├── config/
    ├── dto/
    ├── enums/
    ├── exception/
    └── helper/
```

### Key Architectural Principles

1. **Single Responsibility Principle (SRP)** - Each feature is self-contained
2. **Interface Segregation** - API contracts defined in interfaces
3. **Dependency Injection** - Spring-managed components
4. **Centralized Exception Handling** - Global exception handler
5. **Standardized Response Format** - Consistent API responses
6. **Input Validation** - Controller-level validation
7. **Constants Management** - No hardcoded strings
8. **YAML Configuration** - Environment-specific configs

## 🧪 Testing

```bash
# Run tests
mvn test

# Run with coverage
mvn test jacoco:report

# Run specific test class
mvn test -Dtest=Base64ControllerTest

# Run integration tests
mvn test -Dtest=*IntegrationTest
```

## 🚀 Deployment

### Docker Deployment
```bash
# Build and run with Docker Compose
./deploy.sh

# Or manually
docker-compose up --build -d

# Stop service
docker-compose down
```

### Environment-Specific Deployment

#### Development
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

#### Production
```bash
mvn spring-boot:run -Dspring.profiles.active=prod
```

#### Staging
```bash
mvn spring-boot:run -Dspring.profiles.active=stage
```

### Modern Cloud Deployment

#### AWS ECS/Fargate
```bash
# Build and push to ECR
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin your-account.dkr.ecr.us-east-1.amazonaws.com
docker build -t devtoolkit-backend .
docker tag devtoolkit-backend:latest your-account.dkr.ecr.us-east-1.amazonaws.com/devtoolkit-backend:latest
docker push your-account.dkr.ecr.us-east-1.amazonaws.com/devtoolkit-backend:latest

# Deploy to ECS
aws ecs update-service --cluster devtoolkit-cluster --service devtoolkit-backend --force-new-deployment
```

#### Google Cloud Run
```bash
# Build and deploy
gcloud builds submit --tag gcr.io/your-project/devtoolkit-backend
gcloud run deploy devtoolkit-backend --image gcr.io/your-project/devtoolkit-backend --platform managed
```

#### Azure Container Instances
```bash
# Build and push to ACR
az acr build --registry your-registry --image devtoolkit-backend .
az container create --resource-group your-rg --name devtoolkit-backend --image your-registry.azurecr.io/devtoolkit-backend:latest --ports 8080
```

#### Railway
```bash
# Install Railway CLI
npm install -g @railway/cli

# Deploy
railway login
railway init
railway up
```

#### Heroku
```bash
# Create Procfile
echo "web: java -jar target/devtoolkit-backend-0.1.0.jar" > Procfile

# Deploy
heroku create devtoolkit-backend
git push heroku main
```

### Manual Docker Commands
```bash
# Build image
docker build -t devtoolkit-backend .

# Run container
docker run -p 8080:8080 devtoolkit-backend

# Run with environment variables
docker run -p 8080:8080 \
  -e JWT_SECRET=your-secret-key \
  -e CORS_ALLOWED_ORIGINS=https://your-frontend.com \
  devtoolkit-backend
```

## 🔒 Security

- **CORS Configuration** - Configured for frontend integration
- **Input Validation** - Comprehensive validation for all endpoints
- **JWT Security** - Secure token handling with configurable secrets
- **Jasypt Encryption** - Strong encryption utilities
- **Error Handling** - Secure error messages without sensitive data exposure
- **File Upload Security** - Configurable file size limits and type validation

## 📊 Monitoring & Health

### Actuator Endpoints

- **Health Check**: `GET /actuator/health`
- **Application Info**: `GET /actuator/info`
- **Metrics**: `GET /actuator/metrics`
- **Environment**: `GET /actuator/env`

### Logging

- **Development**: DEBUG level with file logging
- **Production**: ERROR level with console logging
- **Staging**: WARN level with file logging
- **SIT**: INFO level with file logging

## 🔧 Development

### Code Quality

- **No Hardcoded Strings** - All strings moved to constants
- **Comprehensive Exception Handling** - Global exception handler
- **Input Validation** - Controller-level validation
- **Standardized Response Format** - Consistent API responses
- **YAML Configuration** - Environment-specific configs

### Adding New Features

1. Create feature package structure
2. Define DTOs for request/response
3. Create constants file
4. Implement validation
5. Create service interface and implementation
6. Define API interface
7. Implement controller
8. Add tests
9. Update documentation

## 📄 License

This project is licensed under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Follow the feature-wise structure for new features
4. Add tests if applicable
5. Update API documentation
6. Submit a pull request

## 📞 Support

For questions or issues:
- Check the [API Documentation](API_DOCUMENTATION.md)
- Review the [Exception Handling Guide](EXCEPTION_HANDLING.md)
- Check the [Validation Architecture](VALIDATION_ARCHITECTURE.md)
- Open an issue on GitHub 