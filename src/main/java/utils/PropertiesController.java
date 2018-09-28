package utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
public class PropertiesController {

    private static PropertiesController instance = null;
    public static final String DEFAULT_PROPERTIES = "properties/debug.properties";
    public static final String JENKINS_PROPERTIES = "properties/jenkins.properties";
    public static final String JENKINS_CONFIG = "env";

    private static final Properties properties = new Properties();

    private PropertiesController() {
        try {
            if (System.getProperty(JENKINS_CONFIG) == null) {
                loadProperties(DEFAULT_PROPERTIES);
            }
            else {
                loadProperties(JENKINS_PROPERTIES);
                loadProperties(System.getProperty(JENKINS_CONFIG));
            }
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

    public static Properties getProperties() {
        return instance.properties;
    }

    public static List<String> getPropertyNamesStartedWith(final String keyStartedWith) {
        return instance.properties.stringPropertyNames().stream()
                .filter(propertyName -> propertyName.startsWith(keyStartedWith))
                .collect(Collectors.toList());
    }

    public static List<String> getValuesByPropertyNamesStartedWith(final String keyStartedWith) {
        return instance.properties.stringPropertyNames().stream()
                .filter(propertyName -> propertyName.startsWith(keyStartedWith))
                .map(PropertiesController::getProperty)
                .collect(Collectors.toList());
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

    public static void loadLanguageProperties(String language) {
        try {
            loadProperties("properties/languages/" + language + ".properties");
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to load configuration file", e);
        }
    }

    public static void loadSelectorsForPages() {
        try {
            loadProperties("properties/mode/" + PropertiesController.getProperty("mode") + ".properties");
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to load configuration file", e);
        }
    }
}