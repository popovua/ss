package utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesController {

    private static PropertiesController instance = null;
    public static final String DEFAULT_PROPERTIES = "properties/debug.properties";

    private static final Properties properties = new Properties();

    private PropertiesController() {
        try {
            loadProperties(DEFAULT_PROPERTIES);
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to load configuration file", e);
        }
    }

    public static String getProperty(final String propertyName) {
        if (instance == null) {
            instance = new PropertiesController();
        }
        return System.getProperty(propertyName, instance.properties.getProperty(propertyName));
    }

    private static void loadProperties(final String resource) throws IOException {
        log.info("Reading env properties: " + resource);
        final InputStream inputStream = PropertiesController.class.getClassLoader().getResourceAsStream(resource);
        if (inputStream == null) {
            throw new IOException("Unable to open stream for resource " + resource);
        }
        final Properties props = new Properties();
        props.load(inputStream);
        inputStream.close();
        for (final String propertyName : props.stringPropertyNames()) {
            if (propertyName.startsWith("+")) {
                loadProperties(propertyName.substring(1));
                props.remove(propertyName);
            }
        }
        properties.putAll(props);
    }
}