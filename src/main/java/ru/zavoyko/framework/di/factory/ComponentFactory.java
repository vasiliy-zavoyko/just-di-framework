package ru.zavoyko.framework.di.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.zavoyko.framework.di.config.Config;
import ru.zavoyko.framework.di.config.JavaConfig;

/**
 * The factory for the components.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComponentFactory {

    private static final ComponentFactory COMPONENT_FACTORY = new ComponentFactory();
    private static final Config CONFIG = new JavaConfig("ru.zavoyko.framework");

    /**
     * Gets the instance of the factory.
     *
     * @return The instance of the factory.
     */
    public static ComponentFactory getInstance() {
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
