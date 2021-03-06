[![Scope](https://app.scope.dev/api/badge/b4efdb06-41dd-4109-9910-433d1cae87e4/default)](https://app.scope.dev/external/v1/explore/b774c8da-2494-4ede-8d2f-b0321ff3cd7c/b4efdb06-41dd-4109-9910-433d1cae87e4/default?branch=master)

# nike-riposte-integration

_Description of your service goes here._

(NOTE: This project is based on 
[Riposte Microservice Template](https://github.com/Nike-Inc/riposte-microservice-template).
See that template project's readme for some general details on working with a Riposte project.) 

## Scope - Local Development

1. With the correct endpoint selected in the Scope Desktop App, execute the following `docker-compose` command locally inside the project folder:
```bash
$> docker-compose up --build --abort-on-container-exit --exit-code-from riposte-tests
``` 
2. Once the tests have finished executing, you can access the `Scope Test Report` directly through the `Scope Native App`.


## Information for integrating clients

_Link to API docs and other relevant info for integrating clients goes here._

## Local development

### Service source code

The service's main source code lives in the [nike-riposte-integration-core-code](nike-riposte-integration-core-code)
module.

### Build the service

```bash
./gradlew clean build
``` 

### Run/debug the service locally

The template project's 
["Running the server"](https://github.com/Nike-Inc/riposte-microservice-template#running_the_server)
section has full details on all your options. Here's a cheat sheet (**these are all equivalent**).

* **Run/debug directly in your IDE.** For example in IntelliJ you can just right click on the `com.undefinedlabs.Main` class and 
select either `Run 'Main.main()'` or `Debug 'Main.main()'` from the right-click-menu. Selecting the debug option will 
let you hit breakpoints immediately without launching a remote debug session.
    + NOTE: The first time it runs using this launch option it will fail, complaining about the `@appId` and 
    `@environment` System properties. You will need to edit the configuration for this launch option to include the 
    `-D@appId=nike-riposte-integration -D@environment=local` System properties. But you only need to do this 
    once - any later launches will remember these settings.
* **Run w/ Gradle** (no remote debug):

```bash
./gradlew run
```

* **Remote debug w/ Gradle** (remote debug on port 5005):

```bash
./gradlew run --debug-jvm
```

* **Run/remote debug the built fat-jar**, a.k.a. shadow jar (remote debug on port 5005):

```bash
./debugShadowJar.sh
``` 

### Execute remote tests

This project contains "remote tests" that are meant to be executed at the service running somewhere (usually at the
deployed service running in test/prod environment, but you can also point them at your service running locally). These 
remote tests serve as functional tests, integration tests, UATs, smoke tests, or whatever else you want to call them. 
They live in the [nike-riposte-integration-remote-tests](nike-riposte-integration-remote-tests) module,
isolated from the service code.

To execute the remote tests, run the following gradle command: 

```bash
./gradlew functionalTest -DremoteTestEnv=[environment]
```

The value of the `-DremoteTestEnv=[environment]` System property can be `local`, `test`, or `prod`.

Remote tests should not be confused with "Component Tests", which live in the main core-code module and serve as
_compile-time_ integration tests. For more details, see the template project readme sections on 
[Remote Tests](https://github.com/Nike-Inc/riposte-microservice-template#remote_tests) and 
[Component Tests](https://github.com/Nike-Inc/riposte-microservice-template#component_tests). 
