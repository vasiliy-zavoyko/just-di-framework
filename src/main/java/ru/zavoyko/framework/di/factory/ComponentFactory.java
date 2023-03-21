package ru.zavoyko.framework.di.factory;

import lombok.SneakyThrows;

public interface ComponentFactory {
    @SneakyThrows
    <T> T createComponent(Class<T> componentClass);
}
