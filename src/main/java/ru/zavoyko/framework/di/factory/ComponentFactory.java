package ru.zavoyko.framework.di.factory;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.ObjectSource;
import ru.zavoyko.framework.di.source.java.JavaObjectSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;


public class ComponentFactory {

    private static final String SYNC_LOCK = "syncLock";
    private static ComponentFactory COMPONENT_FACTORY;
    private ObjectSource objectSource;
    private List<ComponentProcessor> componentProcessors;


    private ComponentFactory(ObjectSource objectSource, List<ComponentProcessor> componentProcessors) {
        this.objectSource = objectSource;
        this.componentProcessors = componentProcessors;
    }

    public static ComponentFactory getInstance(Map<Class, Class> interfaceToImplClassMap) {
        var ref = COMPONENT_FACTORY;
        if (ref == null) {
            synchronized (SYNC_LOCK) {
                ref = COMPONENT_FACTORY;
                if (ref == null) {
                    final var source = getObjectSource(interfaceToImplClassMap);
                    final var componentProcessors = getComponentProcessors(source);
                    COMPONENT_FACTORY = new ComponentFactory(source, componentProcessors);
                }
            }
        }
        return COMPONENT_FACTORY;
    }

    private static List<ComponentProcessor> getComponentProcessors(ObjectSource objectSource) {
        final var scanner = objectSource.getScanner();
        return scanner.getSubTypesOf(ComponentProcessor.class)
                .stream()
                .filter(processor -> !Modifier.isAbstract(processor.getModifiers()))
                .map(objectSource::create)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static ObjectSource getObjectSource(Map<Class, Class> interfaceToImplClassMap) {
        return new JavaObjectSource("ru.zavoyko.framework.di", interfaceToImplClassMap);
    }

    @SneakyThrows
    public <T> T createComponent(Class<T> componentClass) {
        Class<? extends T> implClass = componentClass;
        if (componentClass.isInterface()) {
            implClass = objectSource.getImplClass(componentClass);
        }
        final var newInstance = implClass.getDeclaredConstructor().newInstance();
        componentProcessors.forEach(processor -> processor.process(newInstance));
        return newInstance;
    }

    private ArrayList<Field> getFields(Class<?> clazz) {
        return new ArrayList<>(List.of(clazz.getDeclaredFields()));
    }

}
