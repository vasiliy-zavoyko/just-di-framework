package ru.zavoyko.framework.di.impl;

import ru.zavoyko.framework.di.exception.DIFException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.io.Resources.getResource;
import static java.util.stream.Collectors.toMap;

public class ApplicationPropertiesLoader {

    public static Map<String, String> loadProperties() {
        try {
            final var resource = getResource("application.properties");
            try (var props = new BufferedReader(new InputStreamReader(resource.openStream()))) {
                return props.lines()
                        .map(String::trim)
                        .map(line -> line.split("="))
                        .collect(toMap(line -> line[0].trim(), line -> line[1].trim()));
            } catch (IOException e) {
                throw new DIFException("Unable to load application.properties", e);
            }
        } catch (IllegalArgumentException e) {
            return new HashMap<>(0);
        }
    }

}
