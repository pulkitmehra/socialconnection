package com.exercise.socialconnection.data.config;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class ConfigUtil. A utility class having general helper methods.
 *
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
public class ConfigUtil {

    /** The Constant PROPERTY_MISSING_ERR_FORMAT. */
    private static final String PROPERTY_MISSING_ERR_FORMAT = "Required property key '%s' is missing in the Environment";

    /**
     * Format message.
     *
     * @param format the format
     * @param props the props
     * @return the string
     */
    private static String formatMessage(String format, Object... props) {
        return String.format(format, props);
    }

    /**
     * Gets the missing property message.
     *
     * @param property the property
     * @return the missing property message
     */
    public static String getMissingPropertyMessage(String property) {
        return formatMessage(PROPERTY_MISSING_ERR_FORMAT, property);
    }

    /**
     * Utility method for generating a single key value map
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param k the k
     * @param v the v
     * @return the map
     */
    public static <K, V> Map<K, V> map(K k, V v) {
        Map<K, V> map = new HashMap<K, V>();
        map.put(k, v);
        return map;
    }

}
