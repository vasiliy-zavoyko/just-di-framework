package ru.zavoyko.framework.di;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.factory.impl.BasicComponentFactory;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.Definition;
import ru.zavoyko.framework.di.source.ComponentSource;
import ru.zavoyko.framework.di.source.impl.java.JavaObjectComponentSource;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DIFramework {

    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(DIFramework.class);

    public static Context start(Map<String, ComponentSource> componentSourceMapToSetup) {
        final var basicContext = new BasicContext();

        final var componentSourceMap = new HashMap<String, ComponentSource>();
        final var definitionsToSetup = new HashMap<String, Definition>();
        final var componentProcessors = new HashSet<ComponentProcessor>();
        final var actionsProcessors = new HashSet<ActionsProcessor>();
        final var componentsDefinitions = new HashSet<Definition>();

        for (final var componentSource : componentSourceMapToSetup.values()) {
            componentSourceMap.put(componentSource.getPackageToScan(), componentSource);
            definitionsToSetup.putAll(
                    componentSource.findAllDefinitions().stream()
                            .collect(Collectors.toMap(Definition::getComponentClassName, item -> item))
            );
            componentsDefinitions.addAll(componentSource.getComponentDefinitions());
            actionsProcessors.addAll(componentSource.getActionProcessors());
            componentProcessors.addAll(componentSource.getComponentProcessors());
        }

        final var basicComponentFactory = BasicComponentFactory.builder()
                .componentSourceMap(unmodifiableMap(componentSourceMap))
                .definitions(unmodifiableMap(definitionsToSetup))
                .componentProcessors(unmodifiableSet(componentProcessors))
                .actionsProcessors(unmodifiableSet(actionsProcessors))
                .componentsDefinitions(unmodifiableSet(componentsDefinitions))
                .context(basicContext)
                .build();

        basicContext.setFactory(basicComponentFactory);
        basicContext.initContext();
        return basicContext;
    }

    public static Context createForJavaComponents(String packageToScan) {
        final var componentSourceMap = new HashMap<String, ComponentSource>();
        final var javaObjectComponentSource = new JavaObjectComponentSource(packageToScan);
        componentSourceMap.put(packageToScan, javaObjectComponentSource);
        return start(componentSourceMap);
    }

}
