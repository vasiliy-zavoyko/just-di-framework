package ru.zavoyko.framework.di.source.impl;

import ru.zavoyko.framework.di.exceptions.DIFrameworkInstantiationException;
import ru.zavoyko.framework.di.inject.java.TypeToInject;
import ru.zavoyko.framework.di.source.ComponentSource;
import ru.zavoyko.framework.di.source.Definition;
import ru.zavoyko.framework.di.source.impl.java.exceptions.JavaObjectSourceInstanceCreationException;
import ru.zavoyko.framework.di.utils.ReflectionUtils;

import java.util.*;

public abstract class AbstractComponentSource implements ComponentSource {

    protected BasicDefinition getComponentDefinition(Class<?> clazz) {
        final var typeToInject = clazz.getAnnotation(TypeToInject.class);
        final var canonicalName = clazz.getCanonicalName();
        final var aliases = new ArrayList<>(getAliases(clazz, new LinkedList<>()));
        return BasicDefinition.builder()
                .isLazy(typeToInject.isLazy())
                .isSingleton(typeToInject.isSingleton())
                .componentSourceName(getSourcePackage())
                .name(canonicalName)
                .componentAliases(aliases)
                .build();
    }

    protected List<String> getAliases(Class<?> item, List<String> aliases) {
        if (item.getSuperclass() == null) {
            return aliases;
        }
        Arrays.stream(item.getInterfaces()).map(Class::getCanonicalName).forEach(aliases::add);
        aliases.add(item.getCanonicalName());
        return getAliases(item.getSuperclass(), aliases);
    }

    @Override
    public Object getInstanceByDefinition(Definition definition) {
        try {
            final Class<?> clazz = Class.forName(definition.getName(), true, this.getClass().getClassLoader());
            return ReflectionUtils.createInstance(clazz);
        } catch (DIFrameworkInstantiationException e) {
            throw new JavaObjectSourceInstanceCreationException("Can't create instance of the class: " + definition.getName(), e);
        } catch (ClassNotFoundException e) {
            throw new JavaObjectSourceInstanceCreationException("Can't find class: " + definition.getName(), e);
        }
    }

}
