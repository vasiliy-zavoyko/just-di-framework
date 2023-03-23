package ru.zavoyko.framework.di.properties;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;

/**
 * The properties loader.
 */
public class PropertiesLoader {

    /**
     * Loads the properties from the file.
     *
     * @param propertyName The name of the properties file.
     * @return The map of the properties.
     */
    public Map<String, String> loadProperties(String propertyName) {
        try (final var properties = new BufferedReader(new InputStreamReader(getResource(propertyName).openStream()))) {
            return properties.lines()
                    .map(String::trim)
                    .map(line -> line.split("="))
                    .collect(Collectors.toMap(line -> line[0].trim(), line -> line[1].trim()));
        } catch (IOException e) {
            throw new PropertiesException("Error while loading properties", e);
        } catch (IllegalArgumentException e) {
            return new HashMap<>();
        }
    }

}
