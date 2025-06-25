package persistence.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileLoader {
    public static Properties loadProperties(String filename) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(filename)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading " + filename + " file: " + e.getMessage(), e);
        }

        return properties;
    }
}
