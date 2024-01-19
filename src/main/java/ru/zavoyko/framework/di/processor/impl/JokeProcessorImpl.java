package ru.zavoyko.framework.di.processor.impl;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exception.DIException;
import ru.zavoyko.framework.di.processor.Processor;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;
import static ru.zavoyko.framework.di.Util.castAndReturn;
import static ru.zavoyko.framework.di.Util.getFields;

public class JokeProcessorImpl implements Processor {

    @Override
    @SneakyThrows
    public void process(Object object, Context context) {
        final var collect = getFields(object)
                .stream()
                .filter(field -> field.getType() == String.class)
                .filter(field -> {
                    field.setAccessible(true);
                    try {
                        final var name = castAndReturn(String.class, field.get(object));
                        return (nonNull(name) && !name.isEmpty());
                    } catch (IllegalAccessException e) {
                        throw new DIException(e);
                    }
                })
                .collect(toSet());

        for (final var col : collect) {
            col.setAccessible(true);
            final var string = castAndReturn(String.class, col.get(object));
            col.set(object, string.toUpperCase());
        }
    }

}
