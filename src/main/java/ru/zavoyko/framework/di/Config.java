package ru.zavoyko.framework.di;

import ru.zavoyko.framework.di.impl.ConfigImpl;
import org.reflections.Reflections;
import java.util.List;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;

public interface Config {

    static Config getInstance(List<String> packagesToScan) {
        checkNonNullOrThrowException(packagesToScan, "Packages list is null or empty");
        final var list = packagesToScan.stream().map(Reflections::new).toList();
        final var config = new ConfigImpl(list);
        config.initDefinitions();
        return config;
    }

    Definition getDefinition(String type);

    List<BeanProcessor> getBeanProcessors();

}
