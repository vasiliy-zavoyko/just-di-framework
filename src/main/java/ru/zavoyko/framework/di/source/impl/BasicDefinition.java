package ru.zavoyko.framework.di.source.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.zavoyko.framework.di.source.Definition;

import javax.annotation.Nonnull;
import java.util.List;


@Getter
@Builder()
@RequiredArgsConstructor
public class BasicDefinition implements Definition {

    private final String componentName;
    private final String componentClassName;
    private final String componentSourceName;
    private final List<String> componentAliases;
    private final boolean isSingleton;
    private final boolean isLazy;
    private final boolean isComponent;
    private final Class type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicDefinition that)) return false;
        return Objects.equal(getComponentName(), that.getComponentName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getComponentName());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("componentName", componentName)
                .add("isSingleton", isSingleton)
                .add("isComponent", isComponent)
                .toString();
    }

}
