# scala-drools
An example Drools project in Scala.

## notes

The Scala compiler does not generate classes that comply to this specification by default, but the combination of using @BeanProperty with case classes will take care of that. 
Case classes behave similar to Java beans in the sense that their equality behavior is based on their structural attributes instead of their memory reference, so equals and 
hashCode are taken care of. 
The @BeanProperty annotation generates a bean class that provides the required getters/setters. 
The end result is a model class that behaves exactly like a JavaBean, so interoperability with libraries that assume this convention is possible.

- Don't use fat jar with drools, switch to sbt-native-packager
- Scala case classes are perfect for your rules
- Use only java collections within the classes used by your rules. Avoid the scala collections in that precise case but rely on collection.JavaConversions._ implicits to hide that restriction.
- In knowledge bases only use declarative classes (declare) for internal usage, such as intermediary reasoning state.
