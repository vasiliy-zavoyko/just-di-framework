package ru.zavoyko.framework.di.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import ru.zavoyko.framework.di.*;

import java.lang.reflect.Field;
import java.util.Map;

@RequiredArgsConstructor
public class ObjectFactoryImpl implements ObjectFactory {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    @SneakyThrows
    public <T> T getBean(Class<? extends T> clazz) {
        var implClass = clazz;

        var instance = implClass.getDeclaredConstructor().newInstance();

        for (Processor processor : context.getProcessorList()) {
            processor.process(context, instance);
        }

        return instance;
    }

}
