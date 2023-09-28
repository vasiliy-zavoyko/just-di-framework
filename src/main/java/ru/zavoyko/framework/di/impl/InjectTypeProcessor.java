package ru.zavoyko.framework.di.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.BeanProcessor;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.anotations.InjectType;
import ru.zavoyko.framework.di.utils.DIFObjectUtils;

import java.lang.reflect.Field;
import java.util.Set;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.setField;

@Slf4j
public class InjectTypeProcessor implements BeanProcessor {

    @Override
    public void process(Context context, Object bean) {
        final Class<?> beanClass = bean.getClass();
        final var declaredFields = DIFObjectUtils.getDeclaredFields(beanClass);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectType.class)) {
                log.info("Found field to inject: " + field);
                final var annotation = field.getAnnotation(InjectType.class);
                Class<?> implClass = field.getType();
                if (annotation.type() != Object.class) {
                    implClass = annotation.type();
                }
                final Object dependency = context.getBean(implClass);
                setField(field, bean, dependency);
            }
        }
    }

}
