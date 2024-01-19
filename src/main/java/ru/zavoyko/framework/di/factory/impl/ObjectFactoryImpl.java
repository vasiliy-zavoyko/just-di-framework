package ru.zavoyko.framework.di.factory.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.factory.ObjectFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;

import static ru.zavoyko.framework.di.Util.createInstance;
import static ru.zavoyko.framework.di.Util.getMethods;

@RequiredArgsConstructor
public class ObjectFactoryImpl implements ObjectFactory {

    @Setter
    private Context context;

    @Override
    @SneakyThrows
    public <T> T getComponent(final Class<? extends T> implClass) {
        T bean;

        bean = createInstance(implClass);

        context.getAllProcessor().forEach(processor -> processor.process(bean, context));

        getMethods(bean).stream()
                .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                .findFirst()
                .ifPresent(method -> {
                    method.setAccessible(true);
                    try {
                        method.invoke(bean, null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
        return bean;
    }

}
