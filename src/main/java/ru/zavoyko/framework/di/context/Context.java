package ru.zavoyko.framework.di.context;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.actionsProcessors.ActionProcessor;
import ru.zavoyko.framework.di.configuration.Configuration;
import ru.zavoyko.framework.di.context.impl.ContextImpl;
import ru.zavoyko.framework.di.factory.impl.ObjectFactoryImpl;
import ru.zavoyko.framework.di.processor.Processor;

import java.io.Closeable;
import java.util.List;

public interface Context extends Closeable {

    @SneakyThrows
    public static Context createContext(List<String> packagesToScan) {
        final var configurationList = packagesToScan.stream()
                .map(Configuration::getConfiguration)
                .toList();

        final var factory = new ObjectFactoryImpl();
        final var context = new ContextImpl(factory);

        factory.setContext(context);

        context.init(configurationList);

        return context;
    }


    <T> T getBean(Class<T> tClass);

    String getProperty(String key);

    List<? extends Processor> getAllProcessor();

    List<? extends ActionProcessor> getAllActionProcessors();

    <T> Class<? extends T> getImplementation(Class<T> clazz);

}
