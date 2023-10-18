package ru.zavoyko.framework.di.impl;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.Processor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class JavaConfig implements Config {

    private final Reflections reflections;

    public JavaConfig(String packageToScan) {
        reflections = new Reflections(packageToScan);
    }


    @Override
    public Class getImplClass(final Class<?> classToSet) {
        var implClass = classToSet;
        if (classToSet.isInterface()) {
            final var typesOf = reflections.getSubTypesOf(classToSet);
            if (typesOf.size() != 1) {
                throw new IllegalArgumentException("0 or more then 1 component found of type: " + classToSet);
            }
            implClass = typesOf.iterator().next();
        }
        return implClass;
    }

    @Override
    @SneakyThrows
    public List<Processor> getProcessors() {
        final var processorArrayList = new ArrayList<Processor>();
        reflections.getSubTypesOf(Processor.class).stream()
                .map(aClass -> {
                    try {
                        return aClass.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(processor -> processorArrayList.add(processor));
        return processorArrayList;
    }

}
