package ru.zavoyko.framework.di;

public interface Context {

    <T> T getBean(Class<T> clazz);

}
