package ru.zavoyko.framework.di;

import java.util.List;

public interface Context {

    <T> T getBean(Class<T> clazz);

    List<BeanProcessor> getBeanProcessors();

}
