Pinattsu
=========================================

Pinattsu means "peanuts" in Japanese. It's the code name of my web blog project.

**Why *Pinattsu*?** 

Because I love peanuts so much and I like the music of the word "Pinattsu", prononce "Pinatzu".

# Purpose of the project

This project aims to provide a web platform to publish blog posts.

The project is divided in three parts:

1. a database with MariaDB: (That repository)
2. a REST API provided by SpringBoot with Kotlin and Gradle for backend (https://github.com/pierrick-marie/pinattsu-rest-server)
3. a web application with Next.js and React.js for frontend

That three elements run is separate in three docker containers communicating through a docker virtual networks.

This repository contains 
* all instructions to fetch build and run all docker containers
* the source code of the REST server
* the source code of the frontend (incoming!)

# Run the entire project

Before running the project, you have to put a root password for your database in the file `./secret-root-db-password` and a password for the database user in the file `.secret-user-db-password`.

Once the passwords are configured, run the following commands:

```shell
./run.sh
```

# 1. The database

I chose MariaDB for the database. Its docker container is based on the image `mariadb:latest`.

The build file for the database is `mariadb.Dockerfile` in this repository. 

The configuration of the database is finished in the `docker-compose.yml` file.

 ```yml
  database:
    container_name: pinattsu-database
    image: pinattsu/database:latest
    build: 
      context: .
      dockerfile: mariadb.Dockerfile
    networks:
      - pinattsu-network
    environment:
      MARIADB_ROOT_PASSWORD_FILE: /run/secrets/secret-root-db-password
      MARIADB_DATABASE: pinattsu_DB
      MARIADB_USER: pinattsu 
      MARIADB_PASSWORD_FILE: /run/secrets/secret-user-db-password
 ```

The name of the database for this project is `pinattsu_DB`. MariaDB user `pinattsu` has a full access to the database. 

## 1.1 Change passwords

You have to configure the password of root and the user that will access to the database.

### * Root password

Put a root password in the file named `.secret-root-db-password`.

```shell
echo "{YOUR_ROOT_PASSWORD}" > .secret-root-db-password
```

This file is ignored by git. It is copied during the build of the docker image of the database to setup the root password of mariaDB.

### * Password of mariaDB user 

Put a root password in the file named `.docker-images/secret-user-db-password`.

```shell
echo "{YOUR_USER_PASSWORD}" > .docker-images/secret-user-db-password
```

This file is ignored by git. It is copied during the build of the docker image of the database to setup the password of the user of mariaDB.

## 1.2 Create the database

**All the following command have to be run from the folder `docker-images`!**

To create or reset the database use the following command:

```shell
cd docker-images
docker compose build database 
```

## 1.3 Run and restart the database

To run the database use the following command:

```shell
cd docker-images
docker compose up database
```

The name of the container of the database is `pinattsu-database`.

Once the container is started, you can stop it with:

```shell
docker stop pinattsu-database
```

You can restart you database with:

```shell
docker star pinattsu-database
```

All changes in database are saved permanently!

## 1.4 Access to the database

By default you can only access to the database from the docker virtual network named `pinattsu-network`.
You can access to the database with the mariadb client installed in the container:

```sheel
docker exec -it pinattsu-database mariadb --user pinattsu --password={YOUR_USER_PASSWORD] -b pinattsu_DB
```

`{YOUR_USER_PASSWORD]` is the password you choose for user of mariaDB.

# 2. The REST server

I chose SpringBoot to build a REST server. The sever:

1. connects to mariaDB database
2. exposes a REST API to manipulate database

Main security concerns:

* access control
* CORS filter
* CSRF protection

## 2.1 Docker image of the server

A raw docker image of the REST server is available from docker hub at this address: https://hub.docker.com/r/pimarie/pinattsu-rest-server

The image is based on Temurin-17-alpine provided by Eclipse.
It contains the jar of the server. You can run that image with the following command:

```shell
docker run --rm -e DB_URL={DATABASE_URL} -e DB_USER={DATABASE_USER} -e DB_PASSWORD={DATABASE_PASSWORD} --network {NETWORK} pimarie/pinattsu-rest-server
```
Where:

* {DATABASE_URL} is the URL of the database
* {DATABASE_USER} is the user name of the database
* {DATABASE_PASSWORD} is the password to access to the database
* {NETWORK} is the network use to reach the database

As you can see it's a little bit complex command. Fortunately, `docker-compose.yml` builds and runs for us a new image based on `pimarie/pinattsu-rest-server-jar` with every required informations. The new image is called `pinattsu/rest-server:latest`. 

```yml
  # SpringBoot REST server
  rest-server:
    container_name: pinattsu-rest-server
    image: pinattsu/rest-server:latest
    build: 
      context: .
      dockerfile: spring-rest-server.Dockerfile
    networks:
      - pinattsu-network
    ports: 
      - "8080:8080"
    environment:
      DATABASE_URL: pinattsu-database:3306/pinattsu_DB
      DATABASE_USER: pinattsu
      DATABASE_PASSWORD_FILE: /run/secrets/secret-user-db-password
    depends_on:
      database:
        condition: service_started
```

If you look into the configuration of the new docker image, {DATABASE_URL}, {DATABASE_USER} and {NETWORK} are directly pass as environment variables. 
{DATABASE_PASSWORD_FILE} is the same file used to configure the password of the user of the database.

## 2.2 Create the server

**All the following command have to be run from the folder `docker-images`!**

To create a container of the server use the following command:

```shell
docker compose build rest-server
```

## 2.3 Run and restart the server

To run the server use the following command:

```shell
cd docker-images
docker compose up rest-server
```

The name of the container of the server is `pinattsu-rest-server`.

Once the container is started, you can stop it with:

```shell
docker stop pinattsu-rest-server
```

You can restart you database with:

```shell
docker star pinattsu-database
```

## 2.4 Access to the REST API

You can directly access to the REST server at this address from your local machine: `localhost:8080`.

# License BSD 3-Clause

https://raw.githubusercontent.com/pierrick-marie/springboot-discovery/main/LICENSE

# Release Note

### Version 0.1.0

Version 0.1.0 is the first version of that project.

You can access to the web server at this address: localhost:8080.
Authors are listed at this address: localhost:8080/authors while articles are at this address: localhost:8080/articles.
A REST API is available at this address: localhost:8080/api/{author | article}

# Author

Developer: Pierrick MARIE contact at pierrickmarie.info

# Contributing

Do not hesitate to improve to this program. Feel free to send PR or contact me to send comments. You are welcome to fork this project also ;)

# Badges

[![License](https://img.shields.io/badge/License-BSD%203--Clause-green.svg)](https://opensource.org/licenses/BSD-3-Clause) [![made-with-Kotlin](https://img.shields.io/badge/Made%20with-Kotlin-%23E34F26.svg)](https://kotlinlang.org/) [![made-with-SpringBoot](https://img.shields.io/badge/Made%20with-SpringBoot-blue.svg)](https://spring.io/projects/spring-boot)

# Documentation

Start with SpringBoot: https://spring.io/guides/tutorials/spring-boot-kotlin/

#### Thymeleaf

* https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.pdf
* https://www.thymeleaf.org/doc/articles/layouts.html
* https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#using-texts

#### HTTPS

* https://www.baeldung.com/spring-boot-https-self-signed-certificate

#### Application properties

* https://www.baeldung.com/properties-with-spring
* https://www.youtube.com/watch?v=PmGLn3ua_lU

#### Authentication

* https://spring.io/guides/gs/securing-web/
* https://www.baeldung.com/kotlin/spring-security-dsl
* https://www.baeldung.com/spring-security-login

#### Others

* https://spring.io/guides/tutorials/spring-security-and-angular-js/
* https://www.baeldung.com/spring-boot-angular-web
* https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/
