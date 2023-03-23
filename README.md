Pinattsu REST API server
========================================

This project is a part of Pinattsu project described here: https://github.com/users/pierrick-marie/projects/2

# Introduction

Pinattsu means "peanuts" in Japanese. It's the code name of my web blog project.

## Why *Pinattsu*?

Because I love peanuts so much and I like the music of the word "Pinattsu", prononce "Pinatzu".

## Purpose

Pinattsu aims to provide a web platform to publish blog posts.

The project is divided in three parts:

1. a database with MariaDB: https://github.com/pierrick-marie/pinattsu
2. a REST API provided by SpringBoot with Kotlin and Gradle for backend (That repository)
3. a web application with Next.js and React.js for frontend

That three elements run is separate in three docker containers communicating through a docker virtual networks.

This repository is dedicated to the REST API server with SpringBoot.

# Features

1. connect to mariaDB database
2. expose a REST API to manipulate database
   3. Security: access control, CORS filter, CSRF protection

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

# License BSD 3-Clause

https://raw.githubusercontent.com/pierrick-marie/springboot-discovery/main/LICENSE

# Auteur

Developer: Pierrick MARIE contact at pierrickmarie.info

# Contribute

DO not hesitate to send PL or contact me by mail.

# Badges

[![License](https://img.shields.io/badge/License-BSD%203--Clause-green.svg)](https://opensource.org/licenses/BSD-3-Clause) [![made-with-Kotlin](https://img.shields.io/badge/Made%20with-Kotlin-%23E34F26.svg)](https://kotlinlang.org/) [![made-with-SpringBoot](https://img.shields.io/badge/Made%20with-SpringBoot-blue.svg)](https://spring.io/projects/spring-boot)
