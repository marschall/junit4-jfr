JUnit 4 JFR [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/junit4-jfr/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/junit4-jfr) [![Javadocs](https://www.javadoc.io/badge/com.github.marschall/junit4-jfr.svg)](https://www.javadoc.io/doc/com.github.marschall/junit4-jfr)
===========

A JUnit extension that generates JFR events.

```xml
<dependency>
  <groupId>com.github.marschall</groupId>
  <artifactId>junit4-jfr</artifactId>
  <version>0.1.0</version>
  <scope>test</scope>
</dependency>
```

Requires Java 11 based on OpenJDK.

![Flight Recording of a JUnit Test](https://raw.githubusercontent.com/marschall/junit4-jfr/master/src/main/javadoc/screenshot.png)

If you can migrate to JUnit 5 and [marschall/junit-jfr](https://github.com/marschall/junit-jfr) as it offers much higher fidelity.


Usage
-----

* Add a `JfrMethodRule` to your unit test class, see [JfrMethodRuleTest](https://github.com/marschall/junit4-jfr/blob/master/src/test/java/com/github/marschall/junit4/jfr/JfrMethodRuleTest.java) for and example.
* Generate a flight recording from your unit tests, eg using
```
-XX:StartFlightRecording:filename=recording.jfr
-XX:FlightRecorderOptions:stackdepth=128
```

```java
public class ProfiledTest {

  @Rule
  public JfrMethodRule jfr = new JfrMethodRule();

  @Test
  public void testMethod() {
    // implementation
  }

}
```