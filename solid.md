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



###
