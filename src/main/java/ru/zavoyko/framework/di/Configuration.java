package ru.zavoyko.framework.di;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.processor.Processor;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.isAbstract;

public interface Configuration {

    static Configuration getConfiguration(String packageName) {
        return new ConfigurationImpl(new Reflections(packageName));
    }

    Reflections getReflection();
    Collection<Processor> getProcessors();

    class ConfigurationImpl implements Configuration{

        private Reflections reflection;

        private ConfigurationImpl(Reflections reflection) {
            this.reflection = reflection;
        }

        @Override
        public Reflections getReflection() {
            return reflection;
        }

        @Override
        public Collection<Processor> getProcessors() {
            return reflection.getSubTypesOf(Processor.class).stream()
                    .filter(aClass -> !aClass.isInterface())
                    .filter(aClass -> !isAbstract(aClass.getModifiers()))
                    .map(Util::createInstance)
                    .collect(Collectors.toList());
        }
    }

}
