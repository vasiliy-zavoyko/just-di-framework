package ru.zavoyko.framework.di.factory;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.config.Config;
import ru.zavoyko.framework.di.config.java.JavaConfig;
import ru.zavoyko.framework.di.inject.InjectProperty;
import ru.zavoyko.framework.di.properties.PropertiesLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.io.Resources.getResource;

/**
 * The factory for the components.
 */

public class ComponentFactory {

    private static final String SYNC_LOCK = "syncLock";
    private static ComponentFactory COMPONENT_FACTORY = new ComponentFactory();
    private static Config CONFIG;

    private static Map<String, String> PROPERTIES;

    private ComponentFactory() {
        PROPERTIES = new PropertiesLoader().loadProperties("application.properties");
    }

    /**
     * Gets the instance of the factory.
     *
     * @return The instance of the factory.
     */
    public static ComponentFactory getInstance(Map<Class, Class> interfaceToImplClassMap) {
        var refConfig = CONFIG;
        if (refConfig == null) {
            synchronized (SYNC_LOCK) {
                refConfig = CONFIG;
                if (refConfig == null) {
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
        final var newInstance = implClass.getDeclaredConstructor().newInstance();
        for (Field field : getFields(implClass)) {
            if (field.isAnnotationPresent(InjectProperty.class)) {
                final var annotation = field.getAnnotation(InjectProperty.class);
                final var value = (annotation.value().isEmpty()) ?
                        PROPERTIES.get(field.getName()) : PROPERTIES.get(annotation.value());
                field.setAccessible(true);
                field.set(newInstance, value);
            }
        }
        return newInstance;
    }

    private ArrayList<Field> getFields(Class<?> clazz) {
        return new ArrayList<>(List.of(clazz.getDeclaredFields()));
    }

}
