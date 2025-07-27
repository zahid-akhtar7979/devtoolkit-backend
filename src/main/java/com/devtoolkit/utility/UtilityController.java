package com.devtoolkit.utility;

import com.devtoolkit.utility.dto.UtilityRequest;
import com.devtoolkit.utility.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utility")
@CrossOrigin(origins = "http://localhost:3000")
public class UtilityController {
    
    @Autowired
    private UtilityService utilityService;
    

    
    @PostMapping("/url/encode")
    public ResponseEntity<Map<String, Object>> encodeUrl(@RequestBody UtilityRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String encoded = utilityService.encodeUrl(request.getUrl());
            response.put("encoded", encoded);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/url/decode")
    public ResponseEntity<Map<String, Object>> decodeUrl(@RequestBody UtilityRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String decoded = utilityService.decodeUrl(request.getUrl());
            response.put("decoded", decoded);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/uuid/generate")
    public ResponseEntity<Map<String, Object>> generateUuid(@RequestBody UtilityRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer count = request.getCount();
            String type = request.getType() != null ? request.getType() : "v4";
            
            if (count != null && count > 1) {
                // Generate multiple UUIDs
                List<String> uuids = utilityService.generateMultipleUuids(type, count);
                response.put("uuids", uuids);
                response.put("count", count);
            } else {
                // Generate single UUID
                String uuid = utilityService.generateUuid(type);
                response.put("uuid", uuid);
            }
            
            response.put("type", type);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/timestamp/convert")
    public ResponseEntity<Map<String, Object>> convertTimestamp(@RequestBody UtilityRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String converted = utilityService.convertTimestamp(request.getText(), request.getFormat());
            response.put("converted", converted);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/converter/convert")
    public ResponseEntity<Map<String, Object>> convertFormat(@RequestBody UtilityRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String converted = utilityService.convertFormat(request.getText(), request.getSourceFormat(), request.getTargetFormat());
            response.put("converted", converted);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    

    
    @PostMapping("/curl/generate")
    public ResponseEntity<Map<String, Object>> generateCurl(@RequestBody UtilityRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String curl = utilityService.generateCurl(request.getUrl(), request.getMethod(), request.getHeaders(), request.getBody());
            response.put("curl", curl);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/sql/format")
    public ResponseEntity<Map<String, Object>> formatSql(@RequestBody UtilityRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String formatted = utilityService.formatSql(request.getSql(), request.getDialect());
            response.put("formatted", formatted);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/regex/test")
    public ResponseEntity<Map<String, Object>> testRegex(@RequestBody UtilityRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String result = utilityService.testRegex(request.getText(), request.getFormat());
            response.put("result", result);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/diff/compare")
    public ResponseEntity<Map<String, Object>> compareText(@RequestBody UtilityRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> result = utilityService.compareText(request.getText1(), request.getText2());
            response.putAll(result);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
} 