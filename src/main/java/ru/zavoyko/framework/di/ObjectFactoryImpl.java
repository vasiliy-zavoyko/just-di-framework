package ru.zavoyko.framework.di;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import static com.google.common.io.Resources.getResource;

@RequiredArgsConstructor
public class ObjectFactoryImpl implements ObjectFactory {

    @Setter
    private Context context;

    @Override
    @SneakyThrows
    public <T> T getComponent(final Class<? extends T> implClass) {
        T bean;

        bean = implClass.getDeclaredConstructor().newInstance();

//        if (!implClass.isInstance(Processor.class)) {
            context.getAllProcessor().forEach(processor -> processor.process(bean, context));
//        }
        return bean;
    }

}
