# *S.O.L.I.D.*

Introduced by Robert C. Martin, in "Design Principles and Design Pattern"

- **The Single-responsibility principle**: "There should never be more than one reason for a class to change."
- **The Open–closed principle:** "Software entities should be open for extension, but closed for modification."
- **The Liskov substitution principle**: "Functions that use pointers or references to base classes must be able to use objects of derived classes without knowing it."
- **The Interface segregation principle**: "Clients should not be forced to depend upon interfaces that they do not use."
- **The Dependency inversion principle**: "Depend upon abstractions, not concretions."

## *The Single-responsibility principle*

The Single-Responsibility Principle (SRP) is one of the five SOLID principles of object-oriented programming and design. These principles were introduced by Robert C. Martin and are intended to make software designs more understandable, flexible, and maintainable.

The Single-Responsibility Principle states that a class should have only one reason to change. In other words, a class should have only one responsibility or job. When a class has more than one responsibility, it becomes more complex, harder to understand, harder to test, and more prone to bugs.

### Benefits of SRP:

- **Maintainability**: When classes have a single responsibility, they are generally smaller and easier to understand and maintain.
- **Testability**: Classes with a single responsibility are easier to test since there are fewer test cases to consider.
- **Flexibility and Reusability**: When each class in a system focuses on a single task, it is easier to reuse classes in different contexts and recombine them to create new functionality.

**Example of SRP:**

Imagine you have a class `Report` which has methods for generating a report and saving it to a file:

```java
class Report {  
	void generate() { 
		// Generate the report
    }
     
	void saveToFile(String filepath) {
		// Save the report to a file
	}
 }
```
This class has two responsibilities: generating a report and saving it to a file. According to SRP, these responsibilities should be separated into different classes:
```java  
class Report {
	void generate() {
		// Generate the report 
	}
}

class ReportSaver {  
	void saveToFile(Report report, String filepath) { 
		// Save the report to a file 
	}
}
```
Now, the `Report` class is only responsible for generating a report, and the `ReportSaver` class is responsible for saving a report to a file. Each class has a single responsibility and a single reason to change.

**Applying SRP:**

- Identify the different reasons why a class might need to change.
- Separate those responsibilities into different classes.
- Ensure that each class has a well-defined, single responsibility.

Following the Single-Responsibility Principle helps in creating a codebase that is more modular, easier to maintain, understand, and extend.

## *The Open–closed principle*

The Open/Closed Principle (OCP) is the "O" in the SOLID principles of object-oriented design and programming. It was introduced by Bertrand Meyer in 1988 and states that:

- **Open for Extension:** Software entities (classes, modules, functions, etc.) should be open for extension. This means that the behavior of a module can be extended or enhanced.
- **Closed for Modification:** Software entities should be closed for modification. This means that once a module has been developed and tested, its behavior should not be modified directly to add new functionality or to change its behavior.

In simpler terms, the Open/Closed Principle encourages developers to write code that allows new functionality to be added with minimal changes to existing code. This can be achieved by using interfaces, abstract classes, and polymorphism, among other techniques, to create well-structured and flexible codebases.
### Example:
Consider a system that filters products based on their color:
```java  
class Product {
    
    Color color; // ...
    
}  
  
class ProductFilter { 
    
    List<Product> filterByColor(List<Product> products, Color color) {
        return products.stream() .filter(p -> p.color == color) .collect(Collectors.toList());
    }
    
 }
```  
Now, if you want to add more filtering criteria, such as filtering by size, you would have to modify the `ProductFilter` class directly, which violates the Open/Closed Principle.

To adhere to the OCP, you could define a generic specification interface and implement it for each new filter:
```java
interface Specification<T> {
    boolean isSatisfied(T item);
}

class ColorSpecification implements Specification<Product> {

    private Color color;

    ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.color == color;
    }
}

class ProductFilter {
    List<Product> filter(List<Product> products, Specification<Product> specification) {
        return products.stream() .filter(specification::isSatisfied) .collect(Collectors.toList());
    }
}
```
With this design, you can add new filtering criteria by simply creating new classes that implement the `Specification` interface, without modifying the existing `ProductFilter` class. This adheres to the Open/Closed Principle.
### Benefits of Open/Closed Principle:

- **Reduced Risk:** Since existing code isn’t altered, the risk of introducing bugs in already working code is minimized.
- **Enhanced Reusability:** By separating the concerns, the classes become more focused and reusable.
- **Increased Flexibility:** It’s easy to introduce new functionality and extensions, making the software more adaptable to changes.
- **Improved Maintainability:** With well-structured and modular code, the maintainability of the software is enhanced.

By adhering to the Open/Closed Principle, developers can create more robust, adaptable, and maintainable software.
## *The Liskov substitution principle*

The Liskov Substitution Principle (LSP) is one of the SOLID principles of object-oriented design and programming, introduced by Barbara Liskov in 1987. It states that objects of a superclass should be able to be replaced with objects of a subclass without affecting the correctness of the program.

In more formal terms, if *S* is a subtype of *T*, then objects of type *T* in a program may be replaced with objects of type *S* without altering the desirable properties of that program.

### Key Points of LSP:

- **Subtype Requirements:**
- Subtypes must implement the expected behavior of the superclass.
- Subtypes can enhance behavior but should not weaken the behavior of the superclass.
- **Method Signatures:** Subtypes must maintain the same method signatures as the superclass. This includes the input parameters, return types, and exceptions thrown.
- **Invariants:** Subtypes must maintain any invariants of the superclass. If the superclass ensures certain conditions always hold true, the subclass should not violate those conditions.
- **Contravariance of Method Arguments:** If the subclass overrides a method, it should accept a more general type of arguments or the same type as the superclass method.
- **Covariance of Return Types:** If the subclass overrides a method, it should return the same type or a subtype of the return type in the superclass method.

### Example:
Consider a class `Rectangle` with methods to set its width and height:
```java
class Rectangle {

    protected int width; protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }

}
```
Now, consider a subclass `Square`:
```java
class Square extends Rectangle {

    public void setWidth(int width) {
        this.width = width; this.height = width;
    }

    public void setHeight(int height) {
        this.width = height; this.height = height;
    }

}
```
At first glance, it might seem logical that a `Square` is a `Rectangle`. However, this substitution violates the Liskov Substitution Principle because a `Square` has a constraint that the `width` and `height` must always be equal, which is not a constraint on a general `Rectangle`. Thus, a `Square` is not substitutable for a `Rectangle`.
### Benefits of LSP:

- **Interchangeability:** Subtypes can be used interchangeably with their base types, enhancing flexibility.
- **Reusability:** Subclasses can be reused in any context that expects the superclass type.
- **Maintainability:** Ensuring that subtypes maintain the behavior of their supertypes results in fewer bugs and unexpected behaviors.

Adherence to the Liskov Substitution Principle is crucial for maintaining the integrity and reliability of a system in object-oriented design.


## *The Interface segregation principle*

The Interface Segregation Principle (ISP) is one of the SOLID principles of object-oriented design and programming, introduced by Robert C. Martin. The principle states that:

_"No client should be forced to depend on methods it does not use."_

In other words, it’s better to have several small, specific interfaces rather than a large, all-encompassing one. When an interface is too large, implementing classes are often burdened with methods and properties that they don’t use, but must implement nonetheless. This can lead to bloated classes and can violate the Single Responsibility Principle.
### Example:

Consider a scenario where you have an interface `IPrinter` with methods related to printing tasks:
```java
interface IPrinter {

    void print(Document d);

    void fax(Document d) throws Exception;

    void scan(Document d) throws Exception;

}
```

In this scenario, if a class only wants to implement printing functionality and is forced to implement `fax` and `scan` methods, it violates the Interface Segregation Principle.

To adhere to ISP, you would create smaller, more specific interfaces:
```java
interface IPrinter {
    
    void print(Document d);
    
}

interface IFax {
    
    void fax(Document d) throws Exception;
    
}

interface IScanner {

    void scan(Document d) throws Exception;

}
```
Now, classes can implement only the interfaces and methods that are relevant to their behavior.
### Benefits of Interface Segregation Principle:
- **Increased Cohesion:**  Having more specific interfaces increases the cohesion of the system as each interface and implementing class are more focused on specific behaviors.
- **Reduced Side Effects:** When changes are made to an interface, they can have side effects on the implementing classes. By keeping interfaces small and specific, the risk of changes affecting unrelated parts of the system is reduced.
- **Ease of Understanding and Maintenance:** Smaller and more focused interfaces are generally easier to understand and maintain. Developers can more easily grasp the purpose and usage of each interface.
- **Flexibility:** Clients can choose to implement only the interfaces they need, leading to a more flexible and adaptable system.

By adhering to the Interface Segregation Principle, developers can create systems that are more modular, easier to understand, maintain, and adapt to changing requirements.

## *The Dependency inversion principle*

The Dependency Inversion Principle (DIP) is the last of the five SOLID principles of object-oriented design and programming. It helps to decouple software modules, making systems more modular, easier to understand, maintain, and adapt. The principle consists of two key guidelines:

1. High-Level Modules should not depend on low-level modules. Both should depend on abstractions.
2. Abstractions should not depend on details. Details should depend on abstractions.

### Explanation:
- **High-Level Modules**: These are modules that typically encapsulate the core functionality of an application, the policy, or the business rules. They should not be dependent on low-level modules or details. 
- **Low-Level Modules**: These modules are responsible for details, implementation, and often represent utility functions and operations in the system. 
- **Abstractions**: These typically take the form of interfaces or abstract classes in object-oriented design. They help in decoupling the high-level and low-level modules.

### Example:
Consider an example of a ReportGenerator class (high-level module) that depends on a FileWriter class (low-level module) to write a report to a file.
```java
class FileWriter {
    
    void writeToFile(String content) {
        // Writes content to a file
    }
    
}

class ReportGenerator {
    
    private FileWriter fileWriter;
  
    ReportGenerator(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    void generateReport() {
        // Generates the report
        String content = "Report Content";
        fileWriter.writeToFile(content);
    }
    
}
```
In this example, ReportGenerator directly depends on the low-level FileWriter class. To apply Dependency Inversion Principle, you would introduce an abstraction between them:
```java
interface IWriter {
    
    void write(String content);
    
}

class FileWriter implements IWriter {
    
    @Override
    public void write(String content) {
        // Writes content to a file
    }
    
}

class ReportGenerator {
    
    private IWriter writer;
  
    ReportGenerator(IWriter writer) {
        this.writer = writer;
    }

    void generateReport() {
        // Generates the report
        String content = "Report Content";
        writer.write(content);
    }

}
```
Now, ReportGenerator depends on the IWriter abstraction and not on the low-level FileWriter class. This allows for easier changes, testing, and additions of different writer implementations, adhering to the Dependency Inversion Principle.
### Benefits of Dependency Inversion Principle:
- **Decoupling**: High-level and low-level modules are decoupled, making the system more modular and easier to manage. 
- **Flexibility**: It’s easier to replace or add new implementations of abstractions without affecting existing code. 
- **Testability**: By depending on abstractions, high-level modules can be easily tested with mock implementations. 
- **Maintainability**: The system is easier to maintain and adapt to changing requirements due to reduced coupling. 

Following the Dependency Inversion Principle leads to more adaptable, maintainable, and testable code, thereby improving the overall design and architecture of software applications.
