package com.apisoft.customer.util;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.json.JsonSmartJsonProvider;

/**
 * JSON related utilities.
 *
 * @author Salah Abu Msameh
 */
public final class JsonUtils {
    
    /**
     * extract a json object from a json string.
     *
     * @param json json string
     * @param root root element to be extracted from the json string
     * @return
     */
    public static String extractJson(final String json, final String root) {
        
        JsonProvider jsonProvider = new JsonSmartJsonProvider();
        return jsonProvider.toJson(JsonPath.read(json, root));
    }
}
