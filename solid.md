# DI Framework

## S.O.L.I.D.

Introduced by Robert C. Martin, in "Design Principles and Design Pattern"

- The Single-responsibility principle: "There should never be more than one reason for a class to change."
- The Open–closed principle: "Software entities ... should be open for extension, but closed for modification."
- The Liskov substitution principle: "Functions that use pointers or references to base classes must be able to use objects of derived classes without knowing it."
- The Interface segregation principle: "Clients should not be forced to depend upon interfaces that they do not use."
- The Dependency inversion principle: "Depend upon abstractions, not concretions."

### Before
- pom.xml
- lof4j.xml

### Step 1
Create object factory

``` java
    public <T> T createComponent(Class<T> componentClass) {
        Class<? extends T> implClass = componentClass;
        if (componentClass.isInterface()) {
            implClass = CONFIG.getImplClass(componentClass);
        }
        return implClass.getDeclaredConstructor().newInstance();
    }
    
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> componentClassInterface) {
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(componentClassInterface);
        if (classes.size() != 1) {
            throw new JavaConfigException("0 or more than 1 impl found for " + componentClassInterface);
        }
        return classes.iterator().next();
    }
```

Test classes
- Logger interface
- Starter interface
- TestLogger class
- StarterRunner class
- FrameworkTest class

Framework classes
- Config interface
- JavaConfig class
- DIContainerException abstract class
- JavaConfigException class
- ComponentFactory class

### Step 2
Add component map

```java
    private static final Map<Class, Class> factoryMap = new HashMap<>(Map.of(
        Logger.class, TestLogger.class,
        Runner.class, FailedTestRunner.class
    ));
```

### Step 3
Add properties loader

```java
    import static com.google.common.io.Resources.getResource;

    public Map<String, String> loadProperties(String propertyName) {
        try {
            final var resource = getResource(propertyName);
            return new BufferedReader(new InputStreamReader(resource.openStream()))
                    .lines()
                    .map(String::trim)
                    .map(line -> line.split("="))
                    .collect(Collectors.toMap(line -> line[0].trim(), line -> line[1].trim()));
        } catch (IOException e) {
            throw new PropertiesException("Error while loading properties", e);
        } catch (IllegalArgumentException e) {
            return new HashMap<>();
        }
    }
```

### Step 4
Create Component processor

```java
    private static List<ComponentProcessor> getComponentProcessors(ObjectSource objectSource) {
        final var scanner = objectSource.getScanner();
        return scanner.getSubTypesOf(ComponentProcessor.class)
                .stream()
                .filter(processor -> !Modifier.isAbstract(processor.getModifiers()))
                .map(objectSource::create)
                .collect(Collectors.toCollection(ArrayList::new));
    }
```

### Step 6
Add Context
