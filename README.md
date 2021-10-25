# Add Divide Game

The application consists of the following components:

* 1 Axon server that acts as the Event storage
* 2 Players (Game server) which start independently and connect to the Axon server
* 1 Database for Axon tracking event processing

### Concepts used

* DDD
* CQRS
* Event sourcing
* Reactive programming

### Tools used

* Gradle as build tool
* Docker compose: for DB and Event storage
* Axon framework: for event sourcing
* Axon server: for event storage
* Postgresql: for Axon tracking token
* Spring Webflux with functional endpoints: for rest application

---

### Requisites

* Docker
* Java 11
---

### Start Services: Axon server(event storage), Postgresql

Go to the `docker` directory and execute:
```
docker-compose up
```

Axon provides a visual overview of the application status, for this you can see the state of the application, services connected and events at:

`localhost:8024`

To terminate and remove services:
```
docker-compose down
```
### Start application

For this implementation there are 2 profiles for each player. We can think of them as 2 services.

To start each service with a profile set the profile flag to one of these values:
* player1
* player2

If using IntelliJ it is possible to set this value on `Edit configuration` > `Active Profiles`

If using the terminal, go to the root of the project, use the following command for player1:
```
java -jar game-server/build/libs/game-server-game-0.0.1-SNAPSHOT.jar --spring.profiles.active=player1
```

For player2:
```
java -jar game-server/build/libs/game-server-game-0.0.1-SNAPSHOT.jar --spring.profiles.active=player2
```

### Things to improve/add

* Add Api documentation (OpenApi3)
* Dockerize all components
* Tests for all logic
* Add test coverage
* Validation of inputs
* Exception handling
* Separation of Player into its own service with own Aggregate 
* Save all games in the DB
* Add 'query' side operations to check for different games
* Registration of players into the system with DB
* Repository for saving users state, registration
* Use of snapshots for Event replay



