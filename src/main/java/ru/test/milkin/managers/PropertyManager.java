package ru.test.milkin.managers;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    private final static Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(
                    PropertyManager.class.getResourceAsStream(
                    "/" + System.getProperty("env", "application") + ".properties"
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException("Properties definition parameter was specified incorrectly or there is no .properties-file in the resources folder", e);
        }
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    public static String getProperty(String key, String defaultProperty) {
        String property = PROPERTIES.getProperty(key);
        return property != null ? property : defaultProperty;
    }
}
