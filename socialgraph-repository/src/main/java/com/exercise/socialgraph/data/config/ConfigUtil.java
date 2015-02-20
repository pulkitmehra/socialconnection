package com.exercise.socialgraph.data.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class ConfigUtil. A utility class having general helper methods.
 * 
 * @author pulkit.mehra
 */
public class ConfigUtil {

    /** The Constant PROPERTY_MISSING_ERR_FORMAT. */
    private static final String PROPERTY_MISSING_ERR_FORMAT = "Required property key '%s' is missing in the Environment";

    /**
     * Format message.
     *
     * @param format
     *            the format
     * @param props
     *            the props
     * @return the string
     */
    private static String formatMessage(String format, Object... props) {
        return String.format(format, props);
    }

    /**
     * Property missing error message.
     *
     * @param property
     *            the property
     * @return the string
     */
    public static String getMissingPropertyMessage(String property) {
        return formatMessage(PROPERTY_MISSING_ERR_FORMAT, property);
    }

    public static <K, V> Map<K, V> map(K k, V v) {
        Map<K, V> map = new HashMap<K, V>();
        map.put(k, v);
        return map;
    }

    @SafeVarargs
    public static <T> List<T> list(T... t) {
        return Arrays.asList(t);
    }

}
