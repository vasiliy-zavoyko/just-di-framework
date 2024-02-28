package ru.zavoyko.framework.di.factory.impl;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.actionsProcessors.ActionProcessor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exception.DIException;
import ru.zavoyko.framework.di.factory.ObjectFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;

import static ru.zavoyko.framework.di.Util.castAndReturn;
import static ru.zavoyko.framework.di.Util.createInstance;
import static ru.zavoyko.framework.di.Util.getMethods;

@Setter
@NoArgsConstructor
@Slf4j
public class ObjectFactoryImpl implements ObjectFactory {

    private Context context;

    @Override
    public <T> T getComponent(final Class<? extends T> implClass) {
        log.debug("Creating bean of class {}", implClass.getName());
        T bean = createInstance(implClass);

        context.getAllProcessor().forEach(processor -> processor.process(bean, context));
        Object proxyBean = bean;
        for (ActionProcessor allActionProcessor : context.getAllActionProcessors()) {
            proxyBean = allActionProcessor.process(proxyBean, context);
        }

        final var newProxyBean = castAndReturn(implClass, proxyBean);

        getMethods(newProxyBean)
                .stream()
                .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                .findFirst()
                .ifPresent(method -> {
                    method.setAccessible(true);
                    try {
                        log.debug("Invoking PostConstruct method of {}", newProxyBean.getClass().getName());
                        method.invoke(newProxyBean, null);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new DIException("Error during PostConstruct", e);
                    }
                });

        return newProxyBean;
    }

}
