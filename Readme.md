# car-rental-interface

Needs Java Version 17 or 21

## Database Setup

In some instances you need to add the path/To/Postgres/bin to your environment variables to make the 'psql' command work.

In the src/main/resources/static folder you can find the setup files for the postgres database. Run them with their command (found in the files) in the following order:

- database_setup.sql
- setup.sql
- data.sql
- triggers.sql

You need an Postgres installation to run these commands.

## Setup

After the Postgres Database was set up this project can be built with the

```bash
./gradlew build clean
```

command.

The project can be started with

```bash
./gradlew bootRun
```

The project can be manipulated in different ways. You can either visit the OpenAPI:

http://localhost:8080/car-rental-interface/swagger-ui/index.html

or use the Angular frontend in the project car-rental here: https://github.com/MarioLetsche/car-rental.