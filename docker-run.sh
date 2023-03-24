#!/bin/sh

java -jar -Ddb.url=jdbc:mariadb://$(echo $DB_URL) -Ddb.user=$(echo $DB_USER) -Ddb.password=$(echo $DB_PASSWORD) /opt/app/rest-server.jar
