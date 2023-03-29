package ru.zavoyko.framework.di.source;

import java.util.List;
import java.util.Optional;

public interface Definition extends Comparable<Definition> {

    boolean isSingleton();

    boolean isLazy();

    String getComponentSourceName();

    String getName();

    List<String> getComponentAliases();

}
