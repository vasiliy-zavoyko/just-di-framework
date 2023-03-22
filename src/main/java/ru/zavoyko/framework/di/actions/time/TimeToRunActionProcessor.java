package ru.zavoyko.framework.di.actions.time;

import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.context.Context;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TimeToRunActionProcessor implements ActionsProcessor {

    private final static Logger logger = LoggerFactory.getLogger(TimeToRunActionProcessor.class);

    @Override
    public Object applyAction(final Context context, final Object component) {
        final var implClass = component.getClass();
        final var methods = List.of(implClass.getMethods()).stream()
                .filter(method -> method.isAnnotationPresent(TimeToRun.class))
                .collect(Collectors.toSet());
        if (methods.isEmpty()) {
            return component;
        }
        return createProxy(implClass, component, methods);
    }

    Object createProxy(Class<?> implClass, Object component, Set<Method> methods) {
        final var methodInterceptor = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
