package com.devtoolkit.hash;

import com.devtoolkit.hash.dto.HashRequest;
import com.devtoolkit.hash.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/hash")
@CrossOrigin(origins = "http://localhost:3000")
public class HashController {
    
    @Autowired
    private HashService hashService;
    
    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateHash(@RequestBody HashRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (request.getAlgorithm() != null && !request.getAlgorithm().trim().isEmpty()) {
                String hash = hashService.generateHash(request.getText(), request.getAlgorithm());
                response.put("hash", hash);
                response.put("algorithm", request.getAlgorithm());
            } else {
                Map<String, String> hashes = hashService.generateHash(request.getText());
                response.put("hashes", hashes);
            }
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
} 