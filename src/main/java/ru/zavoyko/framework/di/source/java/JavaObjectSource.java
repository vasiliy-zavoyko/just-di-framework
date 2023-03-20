package ru.zavoyko.framework.di.source.java;

import lombok.Getter;
import org.reflections.Reflections;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.ObjectSource;
import ru.zavoyko.framework.di.source.java.exceptions.JavaObjectSourceException;
import ru.zavoyko.framework.di.source.java.exceptions.JavaObjectSourceInstanceCreationException;
import ru.zavoyko.framework.di.source.java.exceptions.JavaObjectSourceProcessorException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * The Java configuration for the DI container.
 */
public class JavaObjectSource implements ObjectSource {

    @Getter
    private final Reflections scanner;
    private final Map<Class, Class> interfaceToImplClassMap;

    /**
     * The constructor.
     *
     * @param packageToScan The package to scan for the components.
     */
    public JavaObjectSource(String packageToScan, Map<Class, Class> interfaceToImplClassMap) {
        scanner = new Reflections(packageToScan);
        this.interfaceToImplClassMap = interfaceToImplClassMap;
    }

    /**
     * Gets the implementation class for the given interface.
     *
     * @param componentClassInterface The interface to get the implementation class for.
     * @param <T>                     The type of the interface.
     * @return The implementation class for the given interface.
     */
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> componentClassInterface) {
        return interfaceToImplClassMap.computeIfAbsent(componentClassInterface , clazz -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(componentClassInterface);
            if (classes.size() != 1) {
                throw new JavaObjectSourceInstanceCreationException("0 or more than 1 impl found for " + componentClassInterface);
            }
            return classes.iterator().next();
        });
    }

    @Override
    public ComponentProcessor create(Class<? extends ComponentProcessor> processor) {
        try {
            return processor.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new JavaObjectSourceProcessorException("Failed to create processor " + processor, e);
        }
    }

}
