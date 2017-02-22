# Spring Boot Devtools remote hot reload issue

Project to outline an issue found with autowiring and remote Spring Boot devtools with Spring Boot 1.5.1.RELEASE, and also on reloading templates from jar dependencies when using bootRun and local devtools support.

## Build and demo

There are 3 important builds in this project; one shows the issue that we're having with autowiring using remote devtools support, one shows a fluke on how to have autowiring working, and the final one shows template reloading issues when using bootRun with devtools.

### Autowiring - not working version

./gradlew hotreloaddependant:bootRepackage

java -jar hotreloaddependant/build/libs/hotreloaddependant-0.0.1-SNAPSHOT.jar

Hit http://localhost:8080 to make sure it's working.  Then set up remote devtools reloading in your IDE (using the RemoteSpringApplication process with spring.devtools.remote.secret=mysecret) and make a change to the string within "Dependency.java" file and make the module to force a redeploy.  Spring will then fall over during the startup process with the error:

Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'hotreloadController': Unsatisfied dependency expressed through field 'dependency'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.Dependency' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}

### Autowiring - working version

./gradlew bootRepackage

java -jar build/libs/hotreloaddependant-0.0.1-SNAPSHOT.jar

Go through the same process as above, and you'll see that by pure chance this one succeeds by virtue of the dependency being a with a child folder of the hotreload folder.  It works because DefaultSourceFolderUrlFilter matches both the hotreloaddependency AND the hotreload classes folders as resources to reload with a change is made to hotreloaddependency classes alone, and this has all Spring components load up in the same classloaders by chance.

### bootRun template reloading issue

./gradlew bootrun-dependant-template-issue:bootRun

This fires up the application using bootRun.  This module has a dependency on hotreloaddependency module, and specifically a template in the hotreloaddependency project called greetings2.html.  Fire up this application and navigate to http://localhost:8080/2, and this template will be shown.  Make a change to the template, do a ./gradlew hotreloaddependency:jar to force a rebuild of the jar, refresh the browser page and more often than not you will see an error like:

    org.thymeleaf.exceptions.TemplateInputException: Exception parsing document: template="greeting2", line 3 - column 41] with root cause
    org.xml.sax.SAXParseException: Premature end of file.
