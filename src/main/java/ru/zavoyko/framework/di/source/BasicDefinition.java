package ru.zavoyko.framework.di.source;

import com.google.common.base.Objects;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Builder
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
    public boolean isSingleton() {
        return isSingleton;
    }

    @Override
    public boolean isLazy() {
        return isLazy;
    }

    @Override
    public boolean isComponent() {
        return isComponent;
    }

    @Override
    public String getComponentSourceName() {
        return componentSourceName;
    }

    @Override
    public String getComponentClassName() {
        return componentClassName;
    }

    @Override
    public String getComponentName() {
        return componentName;
    }

    @Override
    public List<String> getComponentAliases() {
        return componentAliases;
    }

    @Override
    public Class getType() {
        return type;
    }

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

}
