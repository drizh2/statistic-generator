package edu.profitsoft.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.profitsoft.models.Field;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Utility class for parsing JSON files.
 */
public class JsonUtils {

    private static final Logger LOGGER = Logger.getLogger(JsonUtils.class.getName());

    private JsonUtils() {
        // Private constructor to prevent instantiation of this utility class.
    }

    /**
     * Parses a JSON file and extracts statistics based on the specified field.
     *
     * @param file  The JSON file to parse.
     * @param field The field to extract statistics from.
     * @return A map containing attribute statistics extracted from the JSON file.
     */
    public static Map<String, Integer> parseJson(File file, Field field) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Integer> fileStatisticsMap = new HashMap<>();
        try {
            JsonNode jsonTree = objectMapper.readTree(file);
            for (JsonNode songNode : jsonTree) {
                JsonNode nodeValue = songNode.get(field.getFieldName());
                if (Objects.nonNull(nodeValue) && !nodeValue.isNull()) {
                    String attributeValue = nodeValue.asText().toLowerCase();
                    if (field.isCollection()) {
                        List<String> values = parseCollectionString(attributeValue);
                        for (String value : values) {
                            addAttributeToMap(value, fileStatisticsMap);
                        }
                    } else {
                        addAttributeToMap(attributeValue, fileStatisticsMap);
                    }
                } else {
                    LOGGER.info("Attribute value is null!");
                }
            }
        } catch (IOException e) {
            LOGGER.info("Cannot read file: " + file.getAbsolutePath());
        }
        return fileStatisticsMap;
    }

    /**
     * Parses a comma-separated string into a list of strings.
     *
     * @param collectionString The comma-separated string to parse.
     * @return A list of strings.
     */
    public static List<String> parseCollectionString(String collectionString) {
        if (Objects.isNull(collectionString)) {
            return Collections.emptyList();
        }
        return Arrays.stream(collectionString.split(", "))
                .filter(item -> !item.isBlank())
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();
    }

    /**
     * Adds an attribute to the statistics map, incrementing its count if it already exists.
     *
     * @param attribute  The attribute to add to the map.
     * @param statistics The map containing attribute statistics.
     */
    private static void addAttributeToMap(String attribute, Map<String, Integer> statistics) {
        statistics.put(attribute, statistics.getOrDefault(attribute, 0) + 1);
    }
}
