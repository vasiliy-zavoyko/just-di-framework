package ru.zavoyko.framework.di.factory.impl;

import static ru.zavoyko.framework.di.Util.createInstance;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.factory.ObjectFactory;

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

        return bean;
    }

}
