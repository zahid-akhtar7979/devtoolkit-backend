# DevToolkit Backend API Documentation

## 📋 Overview

This document provides comprehensive API documentation for the DevToolkit Backend, including sample requests and responses for frontend integration.

**Base URL:** `http://localhost:8080`  
**Content-Type:** `application/json`  
**CORS:** Enabled for `http://localhost:3000`

## 🔧 Common Response Format

All APIs return a standardized response format:

```json
{
  "status": "SUCCESS|ERROR",
  "result": {
    // API-specific response data
  },
  "error": {
    "code": "ERROR_CODE",
    "message": "Error description"
  }
}
```

## 🛠️ API Endpoints

### 1. Base64 Operations

#### 1.1 Encode Text to Base64

**Endpoint:** `POST /api/base64/encode`  
**Description:** Converts plain text to Base64 encoding

**Request:**
```json
{
  "payload": {
    "text": "Hello, World!",
    "operation": "encode"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "encodedText": "SGVsbG8sIFdvcmxkIQ==",
    "originalText": "Hello, World!",
    "operation": "encode",
    "success": true,
    "message": "Text encoded successfully"
  }
}
```

#### 1.2 Decode Base64 to Text

**Endpoint:** `POST /api/base64/decode`  
**Description:** Converts Base64 encoded text to plain text

**Request:**
```json
{
  "payload": {
    "text": "SGVsbG8sIFdvcmxkIQ==",
    "operation": "decode"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "decodedText": "Hello, World!",
    "originalText": "SGVsbG8sIFdvcmxkIQ==",
    "operation": "decode",
    "success": true,
    "message": "Text decoded successfully"
  }
}
```

### 2. Hash Generation

#### 2.1 Generate Hash

**Endpoint:** `POST /api/hash/generate`  
**Description:** Generates various hash types for input text

**Request:**
```json
{
  "payload": {
    "text": "Hello, World!",
    "algorithm": "SHA-256"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "originalText": "Hello, World!",
    "algorithm": "SHA-256",
    "hash": "dffd6021bb2bd5b0af676290809ec3a53191dd81c7f70a4b28688a362182986f",
    "success": true,
    "message": "Hash generated successfully"
  }
}
```

**Supported Algorithms:** `MD5`, `SHA-1`, `SHA-256`, `SHA-512`

### 3. JWT Operations

#### 3.1 Decode JWT Token

**Endpoint:** `POST /api/jwt/decode`  
**Description:** Decodes a JWT token to extract header, payload, and signature

**Request:**
```json
{
  "payload": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "header": {
      "alg": "HS256",
      "typ": "JWT"
    },
    "payload": {
      "sub": "1234567890",
      "name": "John Doe",
      "iat": 1516239022
    },
    "signature": "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "success": true,
    "message": "Token decoded successfully"
  }
}
```

#### 3.2 Verify JWT Token

**Endpoint:** `POST /api/jwt/verify`  
**Description:** Verifies a JWT token using a secret key

**Request:**
```json
{
  "payload": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "secret": "your-secret-key"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "valid": true,
    "claims": {
      "sub": "1234567890",
      "name": "John Doe",
      "iat": 1516239022
    },
    "success": true,
    "message": "Token verified successfully"
  }
}
```

### 4. Jasypt Encryption

#### 4.1 Encrypt Text

**Endpoint:** `POST /api/jasypt/encrypt`  
**Description:** Encrypts text using Jasypt encryption

**Request:**
```json
{
  "payload": {
    "text": "Hello, World!",
    "password": "my-secret-password",
    "algorithm": "PBEWithMD5AndDES"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "originalText": "Hello, World!",
    "encryptedText": "ENC(encrypted-text-here)",
    "algorithm": "PBEWithMD5AndDES",
    "success": true,
    "message": "Text encrypted successfully"
  }
}
```

#### 4.2 Decrypt Text

**Endpoint:** `POST /api/jasypt/decrypt`  
**Description:** Decrypts text using Jasypt decryption

**Request:**
```json
{
  "payload": {
    "text": "ENC(encrypted-text-here)",
    "password": "my-secret-password",
    "algorithm": "PBEWithMD5AndDES"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "originalText": "ENC(encrypted-text-here)",
    "decryptedText": "Hello, World!",
    "algorithm": "PBEWithMD5AndDES",
    "success": true,
    "message": "Text decrypted successfully"
  }
}
```

### 5. CRON Expression Evaluation

#### 5.1 Evaluate CRON Expression

**Endpoint:** `POST /api/cron/evaluate`  
**Description:** Evaluates and describes CRON expressions

**Request:**
```json
{
  "payload": {
    "expression": "0 0 12 * * ?",
    "count": 5
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "cronExpression": "0 0 12 * * ?",
    "nextExecutions": [
      "2024-01-15 12:00:00",
      "2024-01-16 12:00:00",
      "2024-01-17 12:00:00",
      "2024-01-18 12:00:00",
      "2024-01-19 12:00:00"
    ],
    "description": "At 12:00 PM every day",
    "valid": true,
    "message": "Cron expression evaluated successfully"
  }
}
```

### 6. URL Encoder/Decoder

#### 6.1 Encode URL

**Endpoint:** `POST /api/urlencoder/encode`  
**Description:** URL encodes text

**Request:**
```json
{
  "payload": {
    "text": "Hello World!",
    "operation": "encode",
    "encoding": "UTF-8"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "originalText": "Hello World!",
    "encodedText": "Hello%20World%21",
    "operation": "encode",
    "encoding": "UTF-8",
    "success": true,
    "message": "Text encoded successfully"
  }
}
```

#### 6.2 Decode URL

**Endpoint:** `POST /api/urlencoder/decode`  
**Description:** URL decodes text

**Request:**
```json
{
  "payload": {
    "text": "Hello%20World%21",
    "operation": "decode",
    "encoding": "UTF-8"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "originalText": "Hello%20World%21",
    "decodedText": "Hello World!",
    "operation": "decode",
    "encoding": "UTF-8",
    "success": true,
    "message": "Text decoded successfully"
  }
}
```

### 7. UUID Generation

#### 7.1 Generate Single UUID

**Endpoint:** `POST /api/uuidgenerator/generate`  
**Description:** Generates a single UUID

**Request:**
```json
{
  "payload": {
    "type": "v4"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "uuid": "550e8400-e29b-41d4-a716-446655440000",
    "type": "v4",
    "success": true,
    "message": "UUID generated successfully"
  }
}
```

#### 7.2 Generate Multiple UUIDs

**Endpoint:** `POST /api/uuidgenerator/generate-multiple`  
**Description:** Generates multiple UUIDs

**Request:**
```json
{
  "payload": {
    "count": 3,
    "type": "v4"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "uuids": [
      "550e8400-e29b-41d4-a716-446655440000",
      "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
      "6ba7b811-9dad-11d1-80b4-00c04fd430c8"
    ],
    "count": 3,
    "type": "v4",
    "success": true,
    "message": "Multiple UUIDs generated successfully"
  }
}
```

### 8. Timestamp Converter

#### 8.1 Convert Timestamp

**Endpoint:** `POST /api/timestampconverter/convert`  
**Description:** Converts timestamps between different formats

**Request:**
```json
{
  "payload": {
    "timestamp": "1642233600",
    "sourceFormat": "UNIX_SECONDS",
    "targetFormat": "ISO_8601"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "originalTimestamp": "1642233600",
    "sourceFormat": "UNIX_SECONDS",
    "targetFormat": "ISO_8601",
    "convertedTimestamp": "2022-01-15T12:00:00Z",
    "success": true,
    "message": "Timestamp converted successfully"
  }
}
```

### 9. Format Converter

#### 9.1 Convert Between Formats

**Endpoint:** `POST /api/formatconverter/convert`  
**Description:** Converts data between JSON, YAML, and XML formats

**Request:**
```json
{
  "payload": {
    "text": "{\"name\":\"John\",\"age\":30}",
    "sourceFormat": "JSON",
    "targetFormat": "YAML"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "originalText": "{\"name\":\"John\",\"age\":30}",
    "sourceFormat": "JSON",
    "targetFormat": "YAML",
    "convertedText": "name: John\nage: 30",
    "success": true,
    "message": "Format conversion completed successfully"
  }
}
```

### 10. Text Diff Comparison

#### 10.1 Compare Texts

**Endpoint:** `POST /api/diff/compare`  
**Description:** Compares two texts and shows differences

**Request:**
```json
{
  "payload": {
    "text1": "Hello World!",
    "text2": "Hello Universe!",
    "diffType": "text",
    "contextLines": 3
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "identical": false,
    "unifiedDiff": "@@ -1,1 +1,1 @@\n-Hello World!\n+Hello Universe!",
    "sideBySide": {
      "comparison": [
        {
          "lineNumber": 1,
          "left": "Hello World!",
          "right": "Hello Universe!",
          "status": "modified",
          "cssClass": "diff-modified"
        }
      ],
      "totalLines": 1
    },
    "statistics": {
      "totalLines1": 1,
      "totalLines2": 1,
      "unchangedLines": 0,
      "changedLines": 1,
      "changePercentage": 100.0
    },
    "success": true,
    "message": "Text comparison completed successfully"
  }
}
```

### 11. cURL Command Generator

#### 11.1 Generate cURL Command

**Endpoint:** `POST /api/curlgenerator/generate`  
**Description:** Generates cURL commands from request parameters

**Request:**
```json
{
  "payload": {
    "url": "https://api.example.com/users",
    "method": "POST",
    "headers": {
      "Content-Type": "application/json",
      "Authorization": "Bearer token123"
    },
    "body": "{\"name\":\"John\",\"email\":\"john@example.com\"}"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "curlCommand": "curl -X POST 'https://api.example.com/users' -H 'Content-Type: application/json' -H 'Authorization: Bearer token123' -d '{\"name\":\"John\",\"email\":\"john@example.com\"}'",
    "success": true,
    "message": "cURL command generated successfully"
  }
}
```

### 12. SQL Formatter

#### 12.1 Format SQL Query

**Endpoint:** `POST /api/sqlformatter/format`  
**Description:** Formats SQL queries for better readability

**Request:**
```json
{
  "payload": {
    "sql": "SELECT id,name,email FROM users WHERE active=true ORDER BY name",
    "dialect": "mysql"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "originalSql": "SELECT id,name,email FROM users WHERE active=true ORDER BY name",
    "formattedSql": "SELECT\n  id,\n  name,\n  email\nFROM\n  users\nWHERE\n  active = true\nORDER BY\n  name",
    "dialect": "mysql",
    "success": true,
    "message": "SQL formatted successfully"
  }
}
```

### 13. Regex Tester

#### 13.1 Test Regex Pattern

**Endpoint:** `POST /api/regextester/test`  
**Description:** Tests regex patterns against sample text

**Request:**
```json
{
  "payload": {
    "pattern": "\\b\\w+@\\w+\\.\\w+\\b",
    "testText": "Contact us at john@example.com or support@company.com"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "pattern": "\\b\\w+@\\w+\\.\\w+\\b",
    "testText": "Contact us at john@example.com or support@company.com",
    "matches": [
      {
        "match": "john@example.com",
        "start": 12,
        "end": 28
      },
      {
        "match": "support@company.com",
        "start": 32,
        "end": 51
      }
    ],
    "matchCount": 2,
    "success": true,
    "message": "Regex test completed successfully"
  }
}
```

### 14. Image to PDF Converter

#### 14.1 Convert Images to PDF

**Endpoint:** `POST /api/imagetopdf/convert`  
**Description:** Converts multiple images to a single PDF file

**Request:**
```multipart/form-data
Content-Type: multipart/form-data

images: [file1.jpg, file2.png, file3.gif]
outputFileName: "converted-document.pdf"
pageSize: "A4"
```

**Response:**
```json
{
  "status": "SUCCESS",
  "result": {
    "pdfContent": "JVBERi0xLjQKJcOkw7zDtsO...",
    "fileName": "converted-document.pdf",
    "fileSize": 245760,
    "totalPages": 3,
    "imagesProcessed": 3,
    "pageSize": "A4",
    "success": true,
    "message": "Successfully converted 3 images"
  }
}
```

## 🚨 Error Responses

### Common Error Format

```json
{
  "status": "ERROR",
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Text cannot be null or empty"
  }
}
```

### Error Codes

| **Error Code** | **Description** | **HTTP Status** |
|----------------|-----------------|-----------------|
| `VALIDATION_ERROR` | Input validation failed | 400 |
| `BASE64_INVALID_INPUT` | Invalid Base64 input | 400 |
| `HASH_INVALID_ALGORITHM` | Invalid hash algorithm | 400 |
| `JWT_VALIDATION_ERROR` | JWT validation failed | 400 |
| `JWT_INVALID_FORMAT` | Invalid JWT format | 400 |
| `JWT_TOKEN_EXPIRED` | JWT token expired | 401 |
| `JWT_INVALID_SIGNATURE` | Invalid JWT signature | 401 |
| `JASYPT_ENCRYPTION_ERROR` | Jasypt encryption failed | 500 |
| `JASYPT_DECRYPTION_ERROR` | Jasypt decryption failed | 500 |
| `CRON_INVALID_EXPRESSION` | Invalid CRON expression | 400 |
| `UTILITY_PROCESSING_ERROR` | General processing error | 500 |

## 🔧 Frontend Integration Examples

### JavaScript/TypeScript

```javascript
// Example: Base64 Encode
async function encodeBase64(text) {
  try {
    const response = await fetch('http://localhost:8080/api/base64/encode', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        payload: {
          text: text,
          operation: 'encode'
        }
      })
    });
    
    const data = await response.json();
    if (data.status === 'SUCCESS') {
      return data.result.encodedText;
    } else {
      throw new Error(data.error.message);
    }
  } catch (error) {
    console.error('Error encoding text:', error);
    throw error;
  }
}

// Example: Hash Generation
async function generateHash(text, algorithm) {
  try {
    const response = await fetch('http://localhost:8080/api/hash/generate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        payload: {
          text: text,
          algorithm: algorithm
        }
      })
    });
    
    const data = await response.json();
    if (data.status === 'SUCCESS') {
      return data.result.hash;
    } else {
      throw new Error(data.error.message);
    }
  } catch (error) {
    console.error('Error generating hash:', error);
    throw error;
  }
}

// Example: JWT Decode
async function decodeJWT(token) {
  try {
    const response = await fetch('http://localhost:8080/api/jwt/decode', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        payload: {
          token: token
        }
      })
    });
    
    const data = await response.json();
    if (data.status === 'SUCCESS') {
      return data.result;
    } else {
      throw new Error(data.error.message);
    }
  } catch (error) {
    console.error('Error decoding JWT:', error);
    throw error;
  }
}
```

### React Hook Example

```javascript
import { useState } from 'react';

const useDevToolkitAPI = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const callAPI = async (endpoint, payload) => {
    setLoading(true);
    setError(null);
    
    try {
      const response = await fetch(`http://localhost:8080/api/${endpoint}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ payload })
      });
      
      const data = await response.json();
      
      if (data.status === 'SUCCESS') {
        return data.result;
      } else {
        throw new Error(data.error.message);
      }
    } catch (err) {
      setError(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return { callAPI, loading, error };
};

// Usage in component
const MyComponent = () => {
  const { callAPI, loading, error } = useDevToolkitAPI();

  const handleEncode = async (text) => {
    try {
      const result = await callAPI('base64/encode', {
        text: text,
        operation: 'encode'
      });
      console.log('Encoded:', result.encodedText);
    } catch (err) {
      console.error('Error:', err);
    }
  };

  return (
    <div>
      {loading && <p>Loading...</p>}
      {error && <p>Error: {error}</p>}
      <button onClick={() => handleEncode('Hello World')}>
        Encode Text
      </button>
    </div>
  );
};
```

## 🔒 Security Considerations

1. **CORS Configuration**: The API is configured to allow requests from `http://localhost:3000`
2. **Input Validation**: All endpoints validate input parameters
3. **Error Handling**: Comprehensive error handling with meaningful messages
4. **Rate Limiting**: Consider implementing rate limiting for production use
5. **Authentication**: JWT endpoints require proper secret keys

## 📝 Notes

- All timestamps are in ISO 8601 format
- File uploads support common image formats (JPEG, PNG, GIF, etc.)
- Maximum file size is configurable per environment
- All responses follow the standardized format for consistency
- Error messages are user-friendly and actionable

## 🚀 Getting Started

1. **Start the backend server**
   ```bash
   mvn spring-boot:run
   ```

2. **Test an endpoint**
   ```bash
   curl -X POST http://localhost:8080/api/base64/encode \
     -H "Content-Type: application/json" \
     -d '{"payload":{"text":"Hello World","operation":"encode"}}'
   ```

3. **Integrate with your frontend**
   - Use the provided JavaScript examples
   - Handle loading states and errors
   - Implement proper error handling
   - Test all endpoints thoroughly

---

**For more information, visit the main README.md file or contact the development team.** 