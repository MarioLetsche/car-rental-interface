# car-rental-interface
## Setup

After the Postgres Database was set up this project can be built with the

*./gradlew build clean*

command.

The project can be started with

*./gradlew bootrun*

The project can be manipulated in different ways. You can either visit:

http://localhost:8080/car-rental-interface/swagger-ui/index.htm

or use the Angular frontend in the project car-rental.

## Known Issue

The database sometimes sets up the "serial" values with int instead of bigint, which causes the program to fail. Please check with PGAdmin.