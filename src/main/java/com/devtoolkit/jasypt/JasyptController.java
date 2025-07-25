package com.devtoolkit.jasypt;

import com.devtoolkit.jasypt.dto.JasyptRequest;
import com.devtoolkit.jasypt.service.JasyptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/jasypt")
@CrossOrigin(origins = "http://localhost:3000")
public class JasyptController {
    
    @Autowired
    private JasyptService jasyptService;
    
    @PostMapping("/encrypt")
    public ResponseEntity<Map<String, Object>> encrypt(@RequestBody JasyptRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String encrypted = jasyptService.encrypt(request.getText(), request.getPassword(), request.getAlgorithm());
            response.put("encrypted", encrypted);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/decrypt")
    public ResponseEntity<Map<String, Object>> decrypt(@RequestBody JasyptRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String decrypted = jasyptService.decrypt(request.getText(), request.getPassword(), request.getAlgorithm());
            response.put("decrypted", decrypted);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
} 