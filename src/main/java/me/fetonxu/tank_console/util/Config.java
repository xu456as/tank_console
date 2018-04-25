package me.fetonxu.tank_console.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;


public class Config {
    private final static Logger logger = LoggerFactory.getLogger(Config.class);
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(Config.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            logger.error(String.format("error: %s", e));
            throw new RuntimeException("Load configuration error");
        }
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }
}
