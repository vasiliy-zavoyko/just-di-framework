package ru.zavoyko.framework.di.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.ActionProcessor;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.anotations.TimeToRun;
import ru.zavoyko.framework.di.utils.DIFObjectUtils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.getDeclaredMethods;

@Slf4j
public class TimeToRunActionProcessor implements ActionProcessor {

    @Override
    public Object process(final Context context, final Object bean) {
        final var implClass = bean.getClass();
        for (final var method : getDeclaredMethods(implClass)) {
            if (method.isAnnotationPresent(TimeToRun.class)) {
                final var invocationHandler = new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        final var start = System.nanoTime();
                        final var invoked = method.invoke(bean, args);
                        final var stop = System.nanoTime();
                        log.info("Method {} was executed for {} nanoseconds", method.getName(), stop - start);
                        return invoked;
                    }

                };
                final var enhancer = new Enhancer();
                enhancer.setSuperclass(bean.getClass());
                enhancer.setCallback(invocationHandler);
                return enhancer.create();
            }
        }
        return bean;
    }

}
