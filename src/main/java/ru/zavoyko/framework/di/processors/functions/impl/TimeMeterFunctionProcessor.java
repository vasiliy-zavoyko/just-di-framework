package ru.zavoyko.framework.di.processors.functions.impl;

import net.sf.cglib.proxy.Enhancer;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.functions.TimeMeter;
import ru.zavoyko.framework.di.processors.functions.FunctionProcessor;
import ru.zavoyko.framework.di.util.ReflectionUtils;

import net.sf.cglib.proxy.InvocationHandler;
import java.lang.reflect.Method;

public class TimeMeterFunctionProcessor implements FunctionProcessor {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TimeMeterFunctionProcessor.class);


    @Override
    public Object process(Context context, Object component) {
        final var implClass = component.getClass();
        final var methods = ReflectionUtils.getMethodAnnotatedBy(component, TimeMeter.class);
        if (methods.isEmpty()) {
            return component;
        }
        return createProxy(implClass, component);
    }

    private Object createProxy(Class<?> implClass, Object component) {
        final var invocationHandler = new InvocationHandler() {
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
        enhancer.setCallback(invocationHandler);
        return enhancer.create();
    }


}
