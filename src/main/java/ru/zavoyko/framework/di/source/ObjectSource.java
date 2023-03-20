package ru.zavoyko.framework.di.source;


import ru.zavoyko.framework.di.processors.ComponentProcessor;

public interface ObjectSource {

    <T> Class<? extends T> getImplClass(Class<T> componentClassInterface);

    org.reflections.Reflections getScanner();

    ComponentProcessor create(Class<? extends ComponentProcessor> processor);

}
