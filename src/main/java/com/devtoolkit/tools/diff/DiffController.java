package com.devtoolkit.tools.diff;

import com.devtoolkit.tools.diff.dto.DiffRequest;
import com.devtoolkit.tools.diff.service.DiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/diff")
public class DiffController {
    
    @Autowired
    private DiffService diffService;
    
    @PostMapping("/compare")
    public ResponseEntity<Map<String, Object>> compareText(@RequestBody DiffRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> result = diffService.compareText(request.getText1(), request.getText2());
            response.putAll(result);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/enhanced")
    public ResponseEntity<Map<String, Object>> enhancedCompare(@RequestBody DiffRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> result = diffService.generateEnhancedDiff(request);
            response.putAll(result);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
} 