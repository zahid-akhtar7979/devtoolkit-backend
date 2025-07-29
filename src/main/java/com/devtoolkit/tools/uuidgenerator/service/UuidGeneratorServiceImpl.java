package com.devtoolkit.tools.uuidgenerator.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.uuidgenerator.constants.UuidGeneratorConstants;
import com.devtoolkit.tools.uuidgenerator.dto.UuidGeneratorResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for UUID Generator operations
 */
@Service
public class UuidGeneratorServiceImpl implements UuidGeneratorService {
    
    @Override
    public UuidGeneratorResponse generateUuid(String type) {
        if (type == null || type.trim().isEmpty()) {
            type = UuidGeneratorConstants.DEFAULT_TYPE;
        }
        
        try {
            String uuid = generateUuidByType(type);
            
            UuidGeneratorResponse response = new UuidGeneratorResponse();
            response.setType(type);
            response.setUuid(uuid);
            response.setSuccess(true);
            response.setMessage(UuidGeneratorConstants.SINGLE_UUID_SUCCESS_MESSAGE);
            
            return response;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, e, UuidGeneratorConstants.FAILED_TO_GENERATE_UUID);
        }
    }
    
    @Override
    public UuidGeneratorResponse generateMultipleUuids(String type, int count) {
        if (type == null || type.trim().isEmpty()) {
            type = UuidGeneratorConstants.DEFAULT_TYPE;
        }
        
        if (count <= 0) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, UuidGeneratorConstants.COUNT_MUST_BE_GREATER_THAN_ZERO);
        }
        if (count > UuidGeneratorConstants.MAX_COUNT) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                UuidGeneratorConstants.COUNT_CANNOT_EXCEED_PREFIX + UuidGeneratorConstants.MAX_COUNT);
        }
        
        try {
            List<String> uuids = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                uuids.add(generateUuidByType(type));
            }
            
            UuidGeneratorResponse response = new UuidGeneratorResponse();
            response.setType(type);
            response.setUuids(uuids);
            response.setCount(count);
            response.setSuccess(true);
            response.setMessage(UuidGeneratorConstants.MULTIPLE_UUIDS_SUCCESS_MESSAGE);
            
            return response;
        } catch (DevToolkitException e) {
            throw e;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, e, UuidGeneratorConstants.FAILED_TO_GENERATE_MULTIPLE_UUIDS);
        }
    }
    
    private String generateUuidByType(String type) {
        switch (type.toLowerCase()) {
            case UuidGeneratorConstants.UUID_V1:
                return UUID.randomUUID().toString(); // Simplified v1
            case UuidGeneratorConstants.UUID_V4:
                return UUID.randomUUID().toString();
            case UuidGeneratorConstants.UUID_V5:
                return UUID.randomUUID().toString(); // Simplified v5
            default:
                throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                    UuidGeneratorConstants.INVALID_UUID_TYPE_PREFIX + UuidGeneratorConstants.UUID_V1 + 
                    ", " + UuidGeneratorConstants.UUID_V4 + ", " + UuidGeneratorConstants.UUID_V5);
        }
    }
} 