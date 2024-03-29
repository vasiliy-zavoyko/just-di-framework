package ru.zavoyko.framework.di.actionsProcessors.impl;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import ru.zavoyko.framework.di.Util;
import ru.zavoyko.framework.di.actionsProcessors.ActionProcessor;
import ru.zavoyko.framework.di.annotations.TimeToRun;
import ru.zavoyko.framework.di.context.Context;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TimeToRunActionProcessorImpl implements ActionProcessor {

    @Override
    public Object process(Object object, Context context) {
        final var collect = Util.getMethods(object).stream()
                .filter(method -> method.isAnnotationPresent(TimeToRun.class))
                .collect(Collectors.toSet());

        if (collect.isEmpty()) {
            return object;
        }

        return getProxy(collect, object);
    }

    private Object getProxy(Set<Method> methods, Object object) {
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            if (methods.contains(method)) {
                log.info("Method {} was invoked", method.getName());
                long beforeTime = System.nanoTime();
                Object result = method.invoke(object, args);  // invoking on the original object
                long afterTime = System.nanoTime();
                log.info("Method {} was executed for {} nanoseconds", method.getName(), (afterTime - beforeTime) / 1000);
                return result;
            } else {
                return method.invoke(object, args);  // handle non-annotated methods
            }
        };

        final var enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(invocationHandler);

        return enhancer.create();  // Create an instance of the proxy
    }

}
