package ru.zavoyko.framework.di;

import java.util.List;

public interface Config {

    <T> Class<? extends T> getImplClass(Class<T> t);

    List<BeanProcessor> getBeanProcessors();
    List<ActionProcessor> getActionProcessors();

}
