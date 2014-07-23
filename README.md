# Gradle-based JEE 6 Explorations (EJB 3 / Servlet 3 / CDI)

## Requirements
* Local JBoss AS7+ or Wildfly8+
* Eclipse 4.2
* JDK 1.6+

## Usage
Build via `./gradlew build` (will download Gradle if run for first time;
[proxy configuration needed](http://www.gradle.org/docs/current/userguide/build_environment.html#sec:accessing_the_web_via_a_proxy))

Create EAR File with `./gradlew ear`

Create Eclipse Configuration `./gradlew eclipse`
