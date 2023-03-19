package ru.zavoyko.framework.di.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zavoyko.framework.di.config.Config;
import ru.zavoyko.framework.di.config.JavaConfig;

import java.util.Map;

/**
 * The factory for the components.
 */

public class ComponentFactory {

    private static final String SYNC_LOCK = "syncLock";
    private static ComponentFactory COMPONENT_FACTORY = new ComponentFactory();
    private static Config CONFIG;


    /**
     * Gets the instance of the factory.
     *
     * @return The instance of the factory.
     */
    public static ComponentFactory getInstance(Map<Class, Class> interfaceToImplClassMap) {
        var ref = CONFIG;
        if (ref == null) {
            synchronized (SYNC_LOCK) {
                ref = CONFIG;
                if (ref == null) {
                    CONFIG = new JavaConfig("ru.zavoyko.framework.di.dataset", interfaceToImplClassMap);
                }
            }
        }
        return COMPONENT_FACTORY;

    }

    /**
     * Creates a component.
     *
     * @param componentClass The class of the component.
     * @param <T>            The type of the component.
     * @return The component.
     */
    @SneakyThrows
    public <T> T createComponent(Class<T> componentClass) {
        Class<? extends T> implClass = componentClass;
        if (componentClass.isInterface()) {
            implClass = CONFIG.getImplClass(componentClass);
        }
        return implClass.getDeclaredConstructor().newInstance();
    }

}
