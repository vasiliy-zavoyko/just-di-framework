package ru.zavoyko.framework.di.config;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.exceptions.JavaConfigException;

import java.util.Set;

/**
 * The Java configuration for the DI container.
 */
public class JavaConfig implements Config {

    private final Reflections scanner;

    /**
     * The constructor.
     *
     * @param packageToScan The package to scan for the components.
     */
    public JavaConfig(String packageToScan) {
        scanner = new Reflections(packageToScan);
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
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(componentClassInterface);
        if (classes.size() != 1) {
            throw new JavaConfigException("0 or more than 1 impl found for " + componentClassInterface);
        }
        return classes.iterator().next();
    }

}
