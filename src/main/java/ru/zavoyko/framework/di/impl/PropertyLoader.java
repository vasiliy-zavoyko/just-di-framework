package ru.zavoyko.framework.di.impl;

import com.google.common.io.Resources;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyLoader {

    @SneakyThrows
    public Map<String, String> load() {
        final var resource = Resources.getResource("application.properties");

        final var bufferedReader = new BufferedReader(new InputStreamReader(resource.openStream()));

        final var stringMap = bufferedReader.lines()
                .map(string -> string.split("="))
                .collect(Collectors.toMap(strings -> strings[0], strings -> strings[1]));

        return stringMap;
    }

}
