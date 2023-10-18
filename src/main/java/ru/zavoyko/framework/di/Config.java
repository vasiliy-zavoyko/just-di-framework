package ru.zavoyko.framework.di;

import java.util.List;

public interface Config {

    Class<?> getImplClass(Class<?> aClass);

    List<Processor> getProcessors();

}
