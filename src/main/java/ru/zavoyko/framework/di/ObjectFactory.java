package ru.zavoyko.framework.di;

public interface ObjectFactory {

    <T> T create(Class<T> clazz);

}
