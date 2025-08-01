package com.devtoolkit.tools.uuidgenerator.service;

import com.devtoolkit.tools.uuidgenerator.dto.UuidGeneratorResponse;

/**
 * Service interface for UUID Generator operations
 */
public interface UuidGeneratorService {
    
    /**
     * Generate a single UUID
     * 
     * @param type the UUID type (v1, v4, v5)
     * @return UuidGeneratorResponse with the generated UUID
     */
    UuidGeneratorResponse generateUuid(String type);
    
    /**
     * Generate multiple UUIDs
     * 
     * @param type the UUID type (v1, v4, v5)
     * @param count the number of UUIDs to generate
     * @return UuidGeneratorResponse with the generated UUIDs
     */
    UuidGeneratorResponse generateMultipleUuids(String type, int count);
} 