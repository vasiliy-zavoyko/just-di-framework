package ru.zavoyko.framework.di.processor.impl;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.processor.Processor;

import java.util.stream.Collectors;

import static ru.zavoyko.framework.di.Util.getFields;

public class JokeProcessorImpl implements Processor {

    @Override
    @SneakyThrows
    public void process(Object object, Context context) {
        final var collect = getFields(object).stream()
                .filter(field -> field.getType() == String.class).filter(field -> {
                    field.setAccessible(true);
                    try {
                        final var name =  ((String) field.get(object));
                        return ((name != null) && !name.isEmpty());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());

        for (final var col: collect) {
            col.setAccessible(true);
            final var string = (String) col.get(object);
            col.set(object,string.toUpperCase());
        }
    }

}
