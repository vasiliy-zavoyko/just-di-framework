package ru.zavoyko.framework.di.context;

import ru.zavoyko.framework.di.processor.Processor;

import java.util.List;
import java.util.Optional;

public interface Context {

   <T> T getBean(Class<T> tClass);

   String getProperty(String key);

   List<? extends Processor> getAllProcessor();

   <T> Class<? extends T> getImplementation(Class<T> clazz);

}
