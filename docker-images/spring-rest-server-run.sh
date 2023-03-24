#!/bin/sh

# This script is used to build a new docker image based on pimarie/pinattsu-rest-server.
# The script runs the rest server jar with all requiered informations to access to the database.

# Wait few seconds, time to start the database with docker compoe
sleep 5

java -jar -Ddb.url=jdbc:mariadb://$(echo $DATABASE_URL) -Ddb.user=$(echo $DATABASE_USER) -Ddb.password=$(cat $DATABASE_PASSWORD_FILE) /opt/app/rest-server.jar
