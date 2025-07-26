package com.devtoolkit.utility.service;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.languages.Dialect;

@Service
public class UtilityServiceImpl implements UtilityService {
    

    
    @Override
    public String encodeUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to encode URL: " + e.getMessage());
        }
    }
    
    @Override
    public String decodeUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        try {
            return URLDecoder.decode(url, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to decode URL: " + e.getMessage());
        }
    }
    
    @Override
    public String generateUuid(String type) {
        if (type == null || type.trim().isEmpty()) {
            type = "v4";
        }
        
        switch (type.toLowerCase()) {
            case "v1":
                return UUID.randomUUID().toString(); // Simplified v1
            case "v4":
                return UUID.randomUUID().toString();
            case "v5":
                return UUID.randomUUID().toString(); // Simplified v5
            default:
                return UUID.randomUUID().toString();
        }
    }
    
    @Override
    public String convertTimestamp(String timestamp, String format) {
        if (timestamp == null || timestamp.trim().isEmpty()) {
            throw new IllegalArgumentException("Timestamp cannot be null or empty");
        }
        
        try {
            long timestampLong = Long.parseLong(timestamp);
            Instant instant = Instant.ofEpochSecond(timestampLong);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            
            if (format == null || format.trim().isEmpty()) {
                format = "yyyy-MM-dd HH:mm:ss";
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return dateTime.format(formatter);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid timestamp format");
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert timestamp: " + e.getMessage());
        }
    }
    
    @Override
    public String convertFormat(String text, String sourceFormat, String targetFormat) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        try {
            // Handle same format (just format/prettify)
            if (sourceFormat.equalsIgnoreCase(targetFormat)) {
                return formatText(text, sourceFormat);
            }
            
            // Convert between different formats
            if ("JSON".equalsIgnoreCase(sourceFormat) && "YAML".equalsIgnoreCase(targetFormat)) {
                return convertJsonToYaml(text);
            } else if ("YAML".equalsIgnoreCase(sourceFormat) && "JSON".equalsIgnoreCase(targetFormat)) {
                return convertYamlToJson(text);
            } else if ("JSON".equalsIgnoreCase(sourceFormat) && "XML".equalsIgnoreCase(targetFormat)) {
                return convertJsonToXml(text);
            } else if ("XML".equalsIgnoreCase(sourceFormat) && "JSON".equalsIgnoreCase(targetFormat)) {
                return convertXmlToJson(text);
            } else if ("YAML".equalsIgnoreCase(sourceFormat) && "XML".equalsIgnoreCase(targetFormat)) {
                return convertYamlToXml(text);
            } else if ("XML".equalsIgnoreCase(sourceFormat) && "YAML".equalsIgnoreCase(targetFormat)) {
                return convertXmlToYaml(text);
            }
            
            throw new IllegalArgumentException("Conversion from " + sourceFormat + " to " + targetFormat + " is not supported");
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert format: " + e.getMessage());
        }
    }
    
    @Override
    public Map<String, Object> compareText(String text1, String text2) {
        // Basic text comparison - for advanced diff, use the dedicated diff service
        Map<String, Object> result = new HashMap<>();
        
        if (text1 == null) text1 = "";
        if (text2 == null) text2 = "";
        
        result.put("identical", text1.equals(text2));
        result.put("length1", text1.length());
        result.put("length2", text2.length());
        result.put("differences", text1.equals(text2) ? "No differences" : "Found differences");
        
        return result;
    }
    
    @Override
    public String generateCurl(String url, String method, String headers, String body) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        
        StringBuilder curl = new StringBuilder("curl");
        
        if (method != null && !method.trim().isEmpty()) {
            curl.append(" -X ").append(method.toUpperCase());
        }
        
        if (headers != null && !headers.trim().isEmpty()) {
            curl.append(" -H \"").append(headers).append("\"");
        }
        
        if (body != null && !body.trim().isEmpty()) {
            curl.append(" -d '").append(body).append("'");
        }
        
        curl.append(" \"").append(url).append("\"");
        
        return curl.toString();
    }
    
    @Override
    public String formatSql(String sql, String dialect) {
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL cannot be null or empty");
        }

        // Use sql-formatter library for proper formatting
        Dialect sqlDialect = getDialect(dialect);
        return SqlFormatter.of(sqlDialect).format(sql);
    }

    private Dialect getDialect(String dialect) {
        if (dialect == null) return Dialect.StandardSql;
        switch (dialect.toLowerCase()) {
            case "mysql": return Dialect.MySql;
            case "postgresql": return Dialect.PostgreSql;
            case "oracle": return Dialect.PlSql;
            case "sqlserver": return Dialect.TSql;
            case "sqlite": return Dialect.StandardSql;
            default: return Dialect.StandardSql;
        }
    }
    
    @Override
    public String testRegex(String pattern, String text) {
        if (pattern == null || pattern.trim().isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }
        
        try {
            Pattern regex = Pattern.compile(pattern);
            boolean matches = regex.matcher(text != null ? text : "").matches();
            return matches ? "Pattern matches" : "Pattern does not match";
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid regex pattern: " + e.getMessage());
        }
    }
    
    private String formatText(String text, String format) throws JsonProcessingException {
        switch (format.toUpperCase()) {
            case "JSON":
                ObjectMapper jsonMapper = new ObjectMapper();
                Object jsonObject = jsonMapper.readValue(text, Object.class);
                return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            case "YAML":
                YAMLMapper yamlMapper = YAMLMapper.builder().build();
                Object yamlObject = yamlMapper.readValue(text, Object.class);
                return yamlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(yamlObject);
            case "XML":
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
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
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
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
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