package ru.zavoyko.framework.di.source;

import java.util.List;

public interface Definition {

    boolean isSingleton();

    boolean isLazy();

    boolean isComponent();

    String getComponentSourceName();

    String getComponentClassName();

    String getComponentName();

    List<String> getComponentAliases();

    Class getType();

}
