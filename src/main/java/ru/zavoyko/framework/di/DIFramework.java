package ru.zavoyko.framework.di;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.factory.BasicComponentFactory;
import ru.zavoyko.framework.di.source.Definition;
import ru.zavoyko.framework.di.source.ComponentSource;
import ru.zavoyko.framework.di.source.impl.java.JavaObjectComponentSource;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DIFramework {

    public static Context start(List<ComponentSource> componentSources) {
        final var componentDefinitionHashMap = new HashMap<String, Definition>();
        componentSources
                .stream()
                .map(ComponentSource::getDefinitions)
                .flatMap(Set::stream)
                .forEach(item -> componentDefinitionHashMap.put(item.getComponentClassName(), item));
        final var basicContext = new BasicContext(componentDefinitionHashMap);
        final var componentFactory = new BasicComponentFactory(basicContext);
        basicContext.setFactory(componentFactory);
        basicContext.initContext();
        return basicContext;
    }

    public static Context createForJavaComponents(String packageToScan) {
        ArrayList<ComponentSource> sources = new ArrayList<>();
        final var javaObjectComponentSource = new JavaObjectComponentSource(packageToScan);
        sources.add(javaObjectComponentSource);
        return start(sources);
    }

}
