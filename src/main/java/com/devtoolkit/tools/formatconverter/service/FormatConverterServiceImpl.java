package com.devtoolkit.tools.formatconverter.service;

import com.devtoolkit.common.enums.ErrorCode;
import com.devtoolkit.exception.DevToolkitException;
import com.devtoolkit.tools.formatconverter.constants.FormatConverterConstants;
import com.devtoolkit.tools.formatconverter.dto.FormatConverterResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

/**
 * Service implementation for Format Converter operations
 */
@Service
public class FormatConverterServiceImpl implements FormatConverterService {
    
    @Override
    public FormatConverterResponse convertFormat(String text, String sourceFormat, String targetFormat) {
        if (text == null || text.trim().isEmpty()) {
            throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, FormatConverterConstants.EMPTY_TEXT_MESSAGE);
        }
        
        try {
            String converted;
            String sourceFormatUpper = sourceFormat.toUpperCase();
            String targetFormatUpper = targetFormat.toUpperCase();
            
            // Handle same format (just format/prettify)
            if (sourceFormatUpper.equals(targetFormatUpper)) {
                converted = formatText(text, sourceFormatUpper);
            } else {
                // Convert between different formats
                if (FormatConverterConstants.JSON_FORMAT.equals(sourceFormatUpper) && 
                    FormatConverterConstants.YAML_FORMAT.equals(targetFormatUpper)) {
                    converted = convertJsonToYaml(text);
                } else if (FormatConverterConstants.YAML_FORMAT.equals(sourceFormatUpper) && 
                           FormatConverterConstants.JSON_FORMAT.equals(targetFormatUpper)) {
                    converted = convertYamlToJson(text);
                } else if (FormatConverterConstants.JSON_FORMAT.equals(sourceFormatUpper) && 
                           FormatConverterConstants.XML_FORMAT.equals(targetFormatUpper)) {
                    converted = convertJsonToXml(text);
                } else if (FormatConverterConstants.XML_FORMAT.equals(sourceFormatUpper) && 
                           FormatConverterConstants.JSON_FORMAT.equals(targetFormatUpper)) {
                    converted = convertXmlToJson(text);
                } else if (FormatConverterConstants.YAML_FORMAT.equals(sourceFormatUpper) && 
                           FormatConverterConstants.XML_FORMAT.equals(targetFormatUpper)) {
                    converted = convertYamlToXml(text);
                } else if (FormatConverterConstants.XML_FORMAT.equals(sourceFormatUpper) && 
                           FormatConverterConstants.YAML_FORMAT.equals(targetFormatUpper)) {
                    converted = convertXmlToYaml(text);
                } else {
                    throw new DevToolkitException(ErrorCode.VALIDATION_ERROR, 
                        FormatConverterConstants.UNSUPPORTED_CONVERSION_MESSAGE_PREFIX + sourceFormat + FormatConverterConstants.UNSUPPORTED_CONVERSION_MESSAGE_MIDDLE + targetFormat + FormatConverterConstants.UNSUPPORTED_CONVERSION_MESSAGE_SUFFIX);
                }
            }
            
            FormatConverterResponse response = new FormatConverterResponse();
            response.setOriginalText(text);
            response.setConvertedText(converted);
            response.setSourceFormat(sourceFormatUpper);
            response.setTargetFormat(targetFormatUpper);
            response.setSuccess(true);
            response.setMessage(FormatConverterConstants.CONVERSION_SUCCESS_MESSAGE);
            
            return response;
        } catch (DevToolkitException e) {
            throw e;
        } catch (Exception e) {
            throw new DevToolkitException(ErrorCode.UTILITY_PROCESSING_ERROR, e, FormatConverterConstants.CONVERSION_FAILED_MESSAGE);
        }
    }
    
    private String formatText(String text, String format) throws JsonProcessingException {
        switch (format) {
            case FormatConverterConstants.JSON_FORMAT:
                ObjectMapper jsonMapper = new ObjectMapper();
                Object jsonObject = jsonMapper.readValue(text, Object.class);
                return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            case FormatConverterConstants.YAML_FORMAT:
                YAMLMapper yamlMapper = YAMLMapper.builder().build();
                Object yamlObject = yamlMapper.readValue(text, Object.class);
                return yamlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(yamlObject);
            case FormatConverterConstants.XML_FORMAT:
                XmlMapper xmlMapper = XmlMapper.builder().build();
                Object xmlObject = xmlMapper.readValue(text, Object.class);
                return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(xmlObject);
            default:
                return text;
        }
    }
    
    private String convertJsonToYaml(String json) throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        YAMLMapper yamlMapper = YAMLMapper.builder().build();
        
        Object obj = jsonMapper.readValue(json, Object.class);
        return yamlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
    
    private String convertYamlToJson(String yaml) throws JsonProcessingException {
        YAMLMapper yamlMapper = YAMLMapper.builder().build();
        ObjectMapper jsonMapper = new ObjectMapper();
        
        Object obj = yamlMapper.readValue(yaml, Object.class);
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
    
    private String convertJsonToXml(String json) throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        XmlMapper xmlMapper = XmlMapper.builder().build();
        
        Object obj = jsonMapper.readValue(json, Object.class);
        return FormatConverterConstants.XML_HEADER + xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
    
    private String convertXmlToJson(String xml) throws JsonProcessingException {
        XmlMapper xmlMapper = XmlMapper.builder().build();
        ObjectMapper jsonMapper = new ObjectMapper();
        
        // Remove XML declaration if present
        String cleanXml = xml.replaceFirst("<\\?xml[^>]*\\?>\\s*", "");
        Object obj = xmlMapper.readValue(cleanXml, Object.class);
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
    
    private String convertYamlToXml(String yaml) throws JsonProcessingException {
        YAMLMapper yamlMapper = YAMLMapper.builder().build();
        XmlMapper xmlMapper = XmlMapper.builder().build();
        
        Object obj = yamlMapper.readValue(yaml, Object.class);
        return FormatConverterConstants.XML_HEADER + xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
    
    private String convertXmlToYaml(String xml) throws JsonProcessingException {
        XmlMapper xmlMapper = XmlMapper.builder().build();
        YAMLMapper yamlMapper = YAMLMapper.builder().build();
        
        // Remove XML declaration if present
        String cleanXml = xml.replaceFirst("<\\?xml[^>]*\\?>\\s*", "");
        Object obj = xmlMapper.readValue(cleanXml, Object.class);
        return yamlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
} 