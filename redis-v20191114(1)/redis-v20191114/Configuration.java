package org.cloudbus.cloudsim.container.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class Configuration {

    private static final String CONFIG_FILENAME = "redis.properties";

    private static Properties properties;

    static {
        properties = ResourceFinder.buildProperties(CONFIG_FILENAME);
    }

    public static String getProperty(String key, String defaultValue) {
        String valueCandidate = properties.getProperty(key);
        return StringUtils.isBlank(valueCandidate) ? defaultValue : valueCandidate;
    }

    public static String getPropertyY(String key, String defaultValue) {
        String valueCandidate = properties.getProperty(key);
        return StringUtils.isBlank(valueCandidate) ? defaultValue : valueCandidate;
    }

    public static int getProperty(String key, int defaultValue) {
        String valueCandidate = properties.getProperty(key);
        try {
            return Integer.parseInt(valueCandidate);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double getProperty(String key, double defaultValue) {
        String valueCandidate = properties.getProperty(key);
        try {
            return Double.valueOf(valueCandidate);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
