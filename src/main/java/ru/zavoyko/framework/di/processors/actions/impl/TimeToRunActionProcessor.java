package ru.zavoyko.framework.di.processors.actions.impl;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.actions.time.TimeToRun;
import ru.zavoyko.framework.di.processors.actions.ActionsProcessor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.utils.ReflectionUtils;

import java.lang.reflect.Method;

public class TimeToRunActionProcessor implements ActionsProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TimeToRunActionProcessor.class);

    @Override
    public Object applyAction(final Context context, final Object component) {
        final var implClass = component.getClass();
        final var methods = ReflectionUtils.getAllMethodsByAnnotation(implClass, TimeToRun.class);
        if (methods.isEmpty()) {
            return component;
        }
        return createProxy(implClass, component);
    }

    Object createProxy(Class<?> implClass, Object component) {
        final var methodInterceptor = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                logger.info("Method {} was invoked", method.getName());
                final var beforeTime = System.nanoTime();
                final var invokeResult = method.invoke(component, args);
                final var afterTime = System.nanoTime();
                logger.info("Method {} was executed for {} nanoseconds", method.getName(), afterTime - beforeTime);
                return invokeResult;
            }
        };
        final var enhancer = new Enhancer();
        enhancer.setSuperclass(implClass);
        enhancer.setCallback(methodInterceptor);
        return enhancer.create();
    }

}
