package ru.zavoyko.framework.di;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.processor.Processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;
import static ru.zavoyko.framework.di.Configuration.getConfiguration;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextImpl implements Context {

    @SneakyThrows
    public static Context createContext(List<String> packagesToScan) {
        final var configurationList = packagesToScan.stream()
                .map(pack -> getConfiguration(pack))
                .collect(Collectors.toList());
        var propertiesMap = new BufferedReader(new InputStreamReader(getResource("application.properties").openStream()))
                .lines()
                .map(String::trim)
                .map(str -> str.split("="))
                .collect(Collectors.toMap(pair -> pair[0].trim(), pair -> pair[1].trim()));
        final var factory = new ObjectFactoryImpl();
        final var context = new ContextImpl(factory);
        factory.setContext(context);
        context.setProcessors(configurationList);
        context.setPropertyMap(propertiesMap);
        context.setConfigurations(configurationList);
        return context;
    }

    private final Map<Class,Object> singletonMap = new ConcurrentHashMap<>();
    private final List<Configuration> configurations = new CopyOnWriteArrayList<>();
    private final Map<String, String> propertyMap = new ConcurrentHashMap<>();
    private final List<Processor> processorList = new CopyOnWriteArrayList<>();
    private final ObjectFactory objectFactory;

    public void setConfigurations(List<Configuration> configurationList){
        this.configurations.addAll(configurationList);
    }
    public void setProcessors(List<Configuration> configurationList) {
        configurationList.forEach(configuration -> processorList.addAll(configuration.getProcessors()));
    }

    public void setPropertyMap(Map<String,String> propertiesMap){
        this.propertyMap.putAll(propertiesMap);
    }
    @Override
    public <T> Optional<T> getBean(final Class<T> beanClassToGet) { //TODO: make thread safe
        Class<?> classToGet = beanClassToGet;
        if (beanClassToGet.isInterface()){
           classToGet = getImplementation(beanClassToGet);
        }
        if(singletonMap.containsKey(classToGet)){
            return Optional.of(beanClassToGet.cast(singletonMap.get(classToGet)));
        }
        final var newImpl = objectFactory.getComponent(classToGet);
        singletonMap.put(classToGet, newImpl);
        return Optional.of(beanClassToGet.cast(newImpl));
    }

    @Override
    public String getProperty(String key) {
        return propertyMap.get(key);
    }

    @Override
    public List<? extends Processor> getAllProcessor() {
        return processorList;
    }

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> clazz) {
        final List<Class<? extends T>> classList = configurations.stream()
                .map(Configuration::getReflection)
                .map(reflections -> reflections.getSubTypesOf(clazz))
                .flatMap(Set::stream)
                .filter(aClass -> !aClass.isInterface())
                .filter(aClass -> aClass.isAnnotationPresent(TypeToInject.class))
                .toList();
        if (classList.size() != 1) {
            throw new DIException("0 or more than one implementations found", new IllegalArgumentException(clazz.getName()));
        }
        return classList.get(0);
    }

}
