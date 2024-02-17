package com.knspatavardhan.currenciesconverterwrapper.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Double readPropertyDouble(final String json, final String key) {
        try {
            final JsonNode jsonNode = objectMapper.readTree(json);
            return Double.parseDouble(jsonNode.get(key).asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing response", e);
        }
    }
}
