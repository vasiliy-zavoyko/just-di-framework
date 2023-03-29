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

    @Nonnull
    private final String name;
    @Nonnull
    private final String componentSourceName;
    @Nonnull
    private final List<String> componentAliases;
    @Nonnull
    private final boolean isSingleton;
    @Nonnull
    private final boolean isLazy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicDefinition that)) return false;
        return Objects.equal(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getName());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("componentName", name)
                .toString();
    }

    @Override
    public int compareTo(Definition definition) {
        return this.getName().compareTo(definition.getName());
    }

}
