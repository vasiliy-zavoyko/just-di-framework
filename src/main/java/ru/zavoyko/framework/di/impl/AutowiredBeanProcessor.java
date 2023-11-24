package ru.zavoyko.framework.di.impl;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.BeanProcessor;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.ObjectFactory;
import ru.zavoyko.framework.di.anotations.Autowired;
import ru.zavoyko.framework.di.utils.DIFObjectUtils;

public class AutowiredBeanProcessor implements BeanProcessor {

    @Override
    @SneakyThrows
    public void process(Context context, final Object newInstance) {
        final var declaredFields = DIFObjectUtils.getDeclaredFields(newInstance.getClass());

        for (var field : declaredFields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                final var autowired = field.getAnnotation(Autowired.class);

                final var aClass = autowired.value() == Object.class ? field.getType() : autowired.value();
                final var fieldToSet = context.getBean(aClass);

                field.setAccessible(true);
                field.set(newInstance, fieldToSet);
            }
        }
    }

}
