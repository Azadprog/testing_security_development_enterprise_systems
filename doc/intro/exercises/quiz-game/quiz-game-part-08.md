# Quiz Game - Part 08


From now on, we will use SpringBoot and leave behind JEE Containers like WildFly.
The goal now is to adapt your code written so far into SpringBoot.

Start a new Maven project.
In the `pom.xml` file, you need the following BOM in your `<dependencyManagement>`:

`org.springframework.boot:spring-boot-starter-parent`


Among your dependencies, you should have:

* `org.joinfaces:jsf-spring-boot-starter`
* `org.springframework.boot:spring-boot-starter-web`
* `org.springframework.boot:spring-boot-starter-data-jpa`
* `org.springframework.boot:spring-boot-starter-validation`
* `org.springframework.boot:spring-boot-starter-test`
* `org.junit.jupiter:junit-jupiter-engine`
* `com.h2database:h2`

For each dependency, choose the appropriate version and scope.
In particular, make sure that `h2` is in `test` scope.


Move over into this project the entities you wrote so far,
i.e., `Category`, `SubCategory` and `Quiz`.
Those should not really need any adaptation.

Move over the EJB classes, and change their names to remove references to
EJB, e.g., into
`CategoryService`, `QuizService` and `DefaultDataInitializerService`.
These classes need to be adapted, as using Spring beans instead of EJB annotations.
Also move over and adapt all of their existing Arquillian tests.
You can skip the old tests working directly on the entities by manual transactions.


Move over all the existing controllers and Web files. 
Those will need some minor changes in the path links, 
as here in SpringBoot we do not use
a default prefix in the URLs, and the default redirection for the root
"/" is not setup yet.   
 
Create an `Application` class in the `src/main/java` folder which will be your 
entry point for the quiz game.
Annotate such class with `@SpringBootApplication`.
Starting it directly should fail, as `h2` is in `test` scope.
The main class should represent the "production" configuration, but we have
not seen yet the connections with real, non-Docker databases.
We will go back to this point when doing deployments on cloud providers.

Under the `src/test/java` folder, create a class named `LocalApplicationRunner`
with a main method that does start the application in `Application`.
Starting such class from an IDE should work, as should use the test classpath,
which contains `h2`. 
Once started, you should be able to access the game at:

`localhost:8080/index.xhtml`

Solutions to this exercise can be found in the 
`intro/exercises/quiz-game/part-08` module. 