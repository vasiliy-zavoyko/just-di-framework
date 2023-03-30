package ru.zavoyko.framework.di.properties;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;

public class PropertiesLoader {

    public Map<String, String> loadProperties(String propertyName) {
        try (var properties = new BufferedReader(new InputStreamReader(getResource(propertyName).openStream()))) {
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
