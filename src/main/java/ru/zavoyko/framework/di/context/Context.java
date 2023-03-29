package ru.zavoyko.framework.di.context;

import ru.zavoyko.framework.di.factory.ComponentFactory;
import ru.zavoyko.framework.di.source.Definition;

import java.util.Optional;

public interface Context {

    void setFactory(ComponentFactory factory);

    void initContext();

    <T> T getComponent(Class<T> type);

    Object getComponent(String name);

}
