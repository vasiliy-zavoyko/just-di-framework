package ru.zavoyko.framework.di.config;

/**
 * The interface for the configuration of the DI container.
 */
public interface Config {

    /**
     * Gets the implementation class for the given interface.
     *
     * @param componentClassInterface The interface to get the implementation class for.
     * @param <T>                     The type of the interface.
     * @return The implementation class for the given interface.
     */
    <T> Class<? extends T> getImplClass(Class<T> componentClassInterface);

}
