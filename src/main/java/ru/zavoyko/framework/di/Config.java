package ru.zavoyko.framework.di;

public interface Config {

    <T> Class<? extends T> getImplClass(Class<T> t);

}
