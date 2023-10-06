package ru.zavoyko.framework.di.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import ru.zavoyko.framework.di.BeanProcessor;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.Definition;
import ru.zavoyko.framework.di.anotations.TypeToInject;
import ru.zavoyko.framework.di.exception.DIFException;
import ru.zavoyko.framework.di.utils.DIFObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PUBLIC;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;

@Slf4j
@RequiredArgsConstructor(access = PUBLIC)
public class ConfigImpl implements Config {

    private final ArrayList<Definition> definitions = new ArrayList<>();
    private final ArrayList<BeanProcessor> beanProcessors = new ArrayList<>();
    private final List<Reflections> reflections;

    public void initDefinitions() {
        final var definitionList = reflections.stream()
                .map(ref -> ref.getTypesAnnotatedWith(TypeToInject.class))
                .flatMap(Collection::stream)
                .map(type -> Definition.getDefinition(type))
                .toList();
        definitions.addAll(definitionList);

        var subTypesOfBeanProcessor = reflections.stream()
                .map(ref -> ref.getSubTypesOf(BeanProcessor.class))
                .flatMap(Collection::stream)
                .map(DIFObjectUtils::instantiate)
                .toList();
        beanProcessors.addAll(subTypesOfBeanProcessor);
    }

    @Override
    public Definition getDefinition(final String type) {
        checkNonNullOrThrowException(type, "Type can not be null");
        log.debug("Locking for the implementation of the type " + type);
        final var typeDefinitions = definitions.stream()
                .filter(object -> {
                    if (type.equals(object.name())) {
                        return true;
                    }
                    for (String alias : object.aliases()) {
                        if (type.equals(alias)) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();

        if (typeDefinitions.size() != 1) {
            log.error(type + " has " + typeDefinitions.size() + " implementations");
            throw new DIFException(type + " has 0 or more than 1 implementations", new IllegalArgumentException(type));
        }
        return typeDefinitions.get(0);
    }

    @Override
    public List<BeanProcessor> getBeanProcessors() {
        return beanProcessors;
    }

}
