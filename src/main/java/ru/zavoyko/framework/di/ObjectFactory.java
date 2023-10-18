package ru.zavoyko.framework.di;

public interface ObjectFactory {

    <T> T getBean(Class<? extends T>  clazz);

}
