package ru.zavoyko.framework.di.actionsProcessors.impl;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import ru.zavoyko.framework.di.Util;
import ru.zavoyko.framework.di.actionsProcessors.ActionProcessor;
import ru.zavoyko.framework.di.annotations.ExceptionInterceptor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exception.DIException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ExceptionInterceptorImpl implements ActionProcessor {
    @Override
    public Object process(Object object, Context context) {
        final var collect = Util.getMethods(object).stream()
                .filter(method -> method.isAnnotationPresent(ExceptionInterceptor.class))
                .collect(Collectors.toSet());

        if (collect.isEmpty()) {
            return object;
        }

        return getProxy(collect, object);
    }

    private Object getProxy(Set<Method> methods, Object object) {
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            if (methods.contains(method)) {
                Object result;
                try {
                    result = method.invoke(object, args);  // invoking on the original object
                } catch (InvocationTargetException e) {
                    log.error("Exception was thrown during method " + method.getName() + " execution", e);
                    if (e.getCause() instanceof DIException) {
                        throw e;
                    }
                    throw new DIException("Exception was thrown during method " + method.getName() + " execution", e);
                }
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
