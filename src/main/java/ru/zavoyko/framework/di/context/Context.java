package ru.zavoyko.framework.di.context;

import ru.zavoyko.framework.di.processor.Processor;

import java.io.Closeable;
import java.util.List;

public interface Context extends Closeable {

    <T> T getBean(Class<T> tClass);

    String getProperty(String key);

    List<? extends Processor> getAllProcessor();

    <T> Class<? extends T> getImplementation(Class<T> clazz);

}
