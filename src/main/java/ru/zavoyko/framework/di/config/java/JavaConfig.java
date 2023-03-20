package ru.zavoyko.framework.di.config.java;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.config.Config;

import java.util.*;

/**
 * The Java configuration for the DI container.
 */
public class JavaConfig implements Config {

    private final Reflections scanner;
    private final Map<Class, Class> interfaceToImplClassMap;

    /**
     * The constructor.
     *
     * @param packageToScan The package to scan for the components.
     */
    public JavaConfig(String packageToScan, Map<Class, Class> interfaceToImplClassMap) {
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
                throw new JavaConfigException("0 or more than 1 impl found for " + componentClassInterface);
            }
            return classes.iterator().next();
        });
    }

}
