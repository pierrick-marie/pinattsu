#!/bin/sh

ROOT_PASSWORD=.secret-root-db-password
USER_PASSWORD=.secret-user-db-password

echo ""

if [ -z "$(cat ${ROOT_PASSWORD})" ]; then
	echo "! Error ! => You have to configure the root password of the database"
	echo "Use the following command:"
	echo "echo \"{YOUR_ROOT_PASSWORD}\" > ${ROOT_PASSWORD}"

	echo ""

	echo "If this is the first time you run the project, you also have to configure the password for the user of the database"
	echo "Use the following command:"
	echo "echo \"{YOUR_USER_PASSWORD}\" > ${USER_PASSWORD}"

	echo ""

	exit 1;
else
	if [ -z "$(cat ${USER_PASSWORD})" ]; then
	echo "! Error! => You have to configure the password for the user of the database"
	echo "Use the following command:"
	echo "echo \"{YOUR_USER_PASSWORD}\" > ${USER_PASSWORD}"
	
	echo ""

	exit 2;
	else
		docker compose up
	fi
fi
