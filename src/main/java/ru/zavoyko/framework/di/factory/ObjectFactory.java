package ru.zavoyko.framework.di.factory;

public interface ObjectFactory {

    <T> T getComponent(Class<? extends T> clazz);

}
