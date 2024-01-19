package ru.zavoyko.framework.di.context;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.configuration.Configuration;
import ru.zavoyko.framework.di.context.impl.ContextImpl;
import ru.zavoyko.framework.di.factory.impl.ObjectFactoryImpl;
import ru.zavoyko.framework.di.processor.Processor;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;

public interface Context extends Closeable {

    @SneakyThrows
    public static Context createContext(List<String> packagesToScan) {
        final var configurationList = packagesToScan.stream()
                .map(Configuration::getConfiguration)
                .toList();

        var propertiesMap = new BufferedReader(new InputStreamReader(getResource("application.properties").openStream()))
                .lines()
                .map(String::trim)
                .map(str -> str.split("="))
                .collect(Collectors.toMap(pair -> pair[0].trim(), pair -> pair[1].trim()));

        final var factory = new ObjectFactoryImpl();
        final var context = new ContextImpl(factory);

        factory.setContext(context);

        context.setProcessors(configurationList);
        context.setPropertyMap(propertiesMap);
        context.setConfigurations(configurationList);
        context.init();

        return context;
    }

    <T> T getBean(Class<T> tClass);

    String getProperty(String key);

    List<? extends Processor> getAllProcessor();

    <T> Class<? extends T> getImplementation(Class<T> clazz);

}
