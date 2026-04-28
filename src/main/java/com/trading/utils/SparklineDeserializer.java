package com.trading.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SparklineDeserializer extends JsonDeserializer<List<Double>> {

    @Override
    public List<Double> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        
        JsonNode node = p.getCodec().readTree(p);
        List<Double> sparkline = new ArrayList<>();
        
        if (node != null && node.has("price")) {
            JsonNode priceNode = node.get("price");
            if (priceNode.isArray()) {
                for (JsonNode price : priceNode) {
                    sparkline.add(price.asDouble());
                }
            }
        }
        
        return sparkline;
    }
}
