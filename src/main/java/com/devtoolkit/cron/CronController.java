package com.devtoolkit.cron;

import com.devtoolkit.cron.dto.CronRequest;
import com.devtoolkit.cron.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cron")
@CrossOrigin(origins = "http://localhost:3000")
public class CronController {
    
    @Autowired
    private CronService cronService;
    
    @PostMapping("/evaluate")
    public ResponseEntity<Map<String, Object>> evaluateCron(@RequestBody CronRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<String> nextExecutions = cronService.getNextExecutions(request.getCronExpression(), 5);
            String description = cronService.getDescription(request.getCronExpression());
            
            response.put("nextExecutions", nextExecutions);
            response.put("description", description);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
} 