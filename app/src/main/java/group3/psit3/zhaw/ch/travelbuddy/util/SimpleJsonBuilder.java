package group3.psit3.zhaw.ch.travelbuddy.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SimpleJsonBuilder creates valid JSON using a builder pattern.
 * Useful for testing or to create request bodies.
 */
public class SimpleJsonBuilder {

    private final Map<String, String> properties;

    SimpleJsonBuilder() {
        properties = new HashMap<>();
    }

    SimpleJsonBuilder setProperty(String key, Object val) {
        String value = val.toString();
        properties.put(key, value);
        return this;
    }

    public String build() {
        Set<String> keys = properties.keySet();
        String body = keys.stream()
                .map(cur -> String.format("%s:%s", cur, properties.get(cur)))
                .collect(Collectors.joining(","));
        return String.format("{%s}", body);
    }
}
