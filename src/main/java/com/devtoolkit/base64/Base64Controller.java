package com.devtoolkit.base64;

import com.devtoolkit.base64.dto.Base64Request;
import com.devtoolkit.base64.service.Base64Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/base64")
@CrossOrigin(origins = "http://localhost:3000")
public class Base64Controller {
    
    @Autowired
    private Base64Service base64Service;
    
    @PostMapping("/encode")
    public ResponseEntity<Map<String, Object>> encode(@RequestBody Base64Request request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String encoded = base64Service.encode(request.getText());
            response.put("encoded", encoded);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/decode")
    public ResponseEntity<Map<String, Object>> decode(@RequestBody Base64Request request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String decoded = base64Service.decode(request.getText());
            response.put("decoded", decoded);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
} 