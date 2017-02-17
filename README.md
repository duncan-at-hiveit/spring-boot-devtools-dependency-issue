# Spring Boot Devtools remote hot reload issue

Project to outline an issue found with autowiring and remote Spring Boot devtools with Spring Boot 1.5.1.RELEASE

## Build and demo

There are 2 important builds in this project; one shows the issue that we're having, and the other shows a fluke on how to have it working.

### Not working version

./gradlew hotreloaddependant:bootRepackage
java -jar hotreloaddependant/build/libs/hotreloaddependant-0.0.1-SNAPSHOT.jar

Hit http://localhost:8080 to make sure it's working.  Then set up remote devtools reloading in your IDE (using the RemoteSpringApplication process) and make a change to the "Dependency.java" file and make the module to force a redeploy.  Spring will then fall over during the startup process with the error:

Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'hotreloadController': Unsatisfied dependency expressed through field 'dependency'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.Dependency' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}

### Working version

./gradlew bootRepackage
java -jar build/libs/hotreloaddependant-0.0.1-SNAPSHOT.jar

Go through the same process as above, and you'll see that by pure chance this one succeeds by virtue of the dependency being a with a child folder of the hotreload folder.  
