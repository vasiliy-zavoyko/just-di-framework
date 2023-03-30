package ru.zavoyko.framework.di;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.factory.impl.BasicComponentFactory;
import ru.zavoyko.framework.di.source.ComponentSource;
import ru.zavoyko.framework.di.source.impl.java.JavaObjectComponentSource;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DIFramework {

    public static Context start(Map<String, ComponentSource> componentSourceMapToSetup) {
        final var basicContext = new BasicContext();
        final var basicComponentFactory = BasicComponentFactory.builder()
                .context(basicContext)
                .componentSourceMap(componentSourceMapToSetup)
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
