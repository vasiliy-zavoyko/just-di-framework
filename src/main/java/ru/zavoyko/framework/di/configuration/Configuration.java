package ru.zavoyko.framework.di.configuration;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.Util;
import ru.zavoyko.framework.di.actionsProcessors.ActionProcessor;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.processor.Processor;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.isAbstract;
import static ru.zavoyko.framework.di.Util.getProperties;

public interface Configuration {

    static Configuration getConfiguration(String packageName) {
        return new ConfigurationImpl(new Reflections(packageName));
    }

    Reflections getReflection();

    Set<Class<?>> getComponentClasses();

    Collection<Processor> getProcessors();

    Collection<ActionProcessor> getActionProcessors();

    Map<String, String> getPropertiesMap();

    class ConfigurationImpl implements Configuration {

        private Reflections reflection;

        private ConfigurationImpl(Reflections reflection) {
            this.reflection = reflection;
        }

        @Override
        public Reflections getReflection() {
            return reflection;
        }

        @Override
        public Set<Class<?>> getComponentClasses() {
            return reflection.getTypesAnnotatedWith(TypeToInject.class);
        }

        @Override
        public Collection<Processor> getProcessors() {
            return reflection.getSubTypesOf(Processor.class).stream()
                    .filter(aClass -> !aClass.isInterface())
                    .filter(aClass -> !isAbstract(aClass.getModifiers()))
                    .map(Util::createInstance)
                    .collect(Collectors.toList());
        }

        @Override
        public Collection<ActionProcessor> getActionProcessors() {
            return reflection.getSubTypesOf(ActionProcessor.class).stream()
                    .filter(aClass -> !aClass.isInterface())
                    .filter(aClass -> !isAbstract(aClass.getModifiers()))
                    .map(Util::createInstance)
                    .collect(Collectors.toList());
        }

        @Override
        public Map<String, String> getPropertiesMap() {
            return getProperties("application.properties");
        }

    }

}
