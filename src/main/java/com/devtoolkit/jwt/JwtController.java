package com.devtoolkit.jwt;

import com.devtoolkit.jwt.dto.JwtRequest;
import com.devtoolkit.jwt.exception.JwtException;
import com.devtoolkit.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/jwt")
@CrossOrigin(origins = "http://localhost:3000")
public class JwtController {
    
    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/decode")
    public ResponseEntity<Map<String, Object>> decodeToken(@RequestBody JwtRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> decoded = jwtService.decodeToken(request.getToken());
            response.put("decoded", decoded);
            response.put("success", true);
        } catch (JwtException e) {
            response.put("error", e.getUserMessage());
            response.put("errorCode", e.getErrorCode());
            response.put("technicalError", e.getMessage());
            response.put("success", false);
        } catch (Exception e) {
            response.put("error", "An unexpected error occurred while processing the JWT token.");
            response.put("errorCode", "UNKNOWN_ERROR");
            response.put("technicalError", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestBody JwtRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isValid = jwtService.verifyToken(request.getToken(), request.getSecret());
            response.put("valid", isValid);
            response.put("success", true);
        } catch (JwtException e) {
            response.put("error", e.getUserMessage());
            response.put("errorCode", e.getErrorCode());
            response.put("technicalError", e.getMessage());
            response.put("valid", false);
            response.put("success", false);
        } catch (Exception e) {
            response.put("error", "An unexpected error occurred while verifying the JWT token.");
            response.put("errorCode", "UNKNOWN_ERROR");
            response.put("technicalError", e.getMessage());
            response.put("valid", false);
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
} 