package ru.zavoyko.framework.di.processor.impl;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.Util;
import ru.zavoyko.framework.di.annotations.InjectByType;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exception.DIException;
import ru.zavoyko.framework.di.processor.Processor;

import java.util.stream.Collectors;

public class InjectByTypeProcessorImpl implements Processor {

    @Override
    @SneakyThrows
    public void process(Object bean, Context context) {
        final var collectOfFields = Util.getFields(bean).stream()
                .filter(field -> field.isAnnotationPresent(InjectByType.class))
                .collect(Collectors.toSet());

        for (final var field : collectOfFields) {
            final var annotation = field.getAnnotation(InjectByType.class);
            final var anno = (annotation.classValue() == Object.class) ? field.getType() : annotation.classValue();
            final Object beanToInject = context.getBean(anno)
                    .orElseThrow(() -> new DIException("No bean was found", new IllegalArgumentException(field.getType().getName())));
            field.setAccessible(true);
            field.set(
                    bean,
                    beanToInject
            );
        }
    }

}
