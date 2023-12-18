package ru.zavoyko.framework.di;

public interface ObjectFactory {

    <T> T getComponent(Class<? extends T> clazz);

}
