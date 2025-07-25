# DevToolkit Backend

Spring Boot backend API for the Developer Utility Toolkit.

## 🚀 Features

- **Base64 Encode/Decode** - Convert text to/from Base64 encoding
- **Hash Generator** - Generate MD5, SHA-1, SHA-256, SHA-512 hashes
- **JWT Decoder/Verifier** - Decode and verify JSON Web Tokens
- **Jasypt Encrypt/Decrypt** - Encrypt and decrypt text using Jasypt
- **CRON Expression Evaluator** - Evaluate and describe CRON expressions
- **Utility APIs** - URL encode/decode, UUID generation, timestamp conversion, and more

## 🛠️ Tech Stack

- **Spring Boot 3** with Java 17
- **Spring Security** for security
- **Spring Web** for REST APIs
- **Jasypt** for encryption
- **JWT** for token handling
- **Maven** for dependency management

## 📦 Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.8+

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd devtoolkit
   ```

2. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Access the API**
   - API Base URL: `http://localhost:8080/api`
   - Health Check: `http://localhost:8080/actuator/health`

## 🔧 API Endpoints

### Base64
- `POST /api/base64/encode` - Encode text to Base64
- `POST /api/base64/decode` - Decode Base64 to text

### Hash
- `POST /api/hash/generate` - Generate hash for text

### JWT
- `POST /api/jwt/decode` - Decode JWT token
- `POST /api/jwt/verify` - Verify JWT token

### Jasypt
- `POST /api/jasypt/encrypt` - Encrypt text
- `POST /api/jasypt/decrypt` - Decrypt text

### CRON
- `POST /api/cron/evaluate` - Evaluate CRON expression

### Utilities
- `POST /api/utility/url/encode` - Encode URL
- `POST /api/utility/url/decode` - Decode URL
- `POST /api/utility/uuid/generate` - Generate UUID
- `POST /api/utility/timestamp/convert` - Convert timestamp
- `POST /api/utility/converter/convert` - Convert between formats
- `POST /api/utility/diff/compare` - Compare text differences
- `POST /api/utility/curl/generate` - Generate cURL command
- `POST /api/utility/sql/format` - Format SQL query
- `POST /api/utility/regex/test` - Test regex pattern

## 🏗️ Architecture

### Feature-Wise Package Structure

The backend follows Google's production-grade feature-wise folder structure:

```
com.devtoolkit/
├── DevToolkitApplication.java          # Main application class
├── base64/                             # Base64 encoding/decoding feature
│   ├── Base64Controller.java
│   ├── dto/
│   │   └── Base64Request.java
│   └── service/
│       ├── Base64Service.java
│       └── Base64ServiceImpl.java
├── hash/                               # Hash generation feature
├── jwt/                                # JWT token handling feature
├── jasypt/                             # Encryption feature
├── cron/                               # CRON evaluation feature
├── utility/                            # General utilities feature
└── common/                             # Shared components
    ├── config/
    └── exception/
```

## 🧪 Testing

```bash
# Run tests
mvn test

# Run with coverage
mvn test jacoco:report
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
```

## 🔒 Security

- CORS configuration for frontend integration
- Input validation for all endpoints
- JWT security for token handling
- Jasypt encryption utilities

## 📄 License

This project is licensed under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Follow the feature-wise structure for new features
4. Add tests if applicable
5. Submit a pull request 