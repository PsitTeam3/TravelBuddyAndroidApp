package group3.psit3.zhaw.ch.travelbuddy.util;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleJsonBuilder {

    private final Map<String, String> properties;

    public SimpleJsonBuilder() {
        properties = new HashMap<>();
    }

    public SimpleJsonBuilder setProperty(String key, Object val) {
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

    public JSONArray toJsonArray() {
        JSONArray result = new JSONArray();
        try {
            result =  new JSONArray(build());
        } catch (JSONException e) {
            // falls through, returns empty JSONArray
        }
        return result;
    }
}
