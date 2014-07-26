# Gradle-based JEE 6 Explorations (EJB 3 / Servlet 3 / CDI)


This is your project! It is a sample, deployable Gradle JEE6 project, which uses the following software-stack:

* JEE Version 6.0 (JPA 2.0, EJB 3.1, Bean Validation 1.0, CDI 1.0, JAX-RS 1.0)
* AngularJS (as REST Client) with Topcoat CSS library


The project shows essential JEE 6 technologies, like bean validation with Hibernate specific annotations and also custom validations (including JUnit)

## Requirements
* Local JBoss EAP6+, JBoss AS7+ or Wildfly8+
* Eclipse 4.2+
* JDK 1.6+

## Usage

Demo is available under: 

* Start JBoss Enterprise Application Platform 6 or JBoss AS 7 with the Web Profile, open a command line and navigate to the root of the JBoss server directory.
* The following shows the command line to start the server with the web profile:
        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat
* Copy the ear to JBOSS_HOME/standalone/deployments
* WebApp is showing up under http://localhost:8080/demo