FROM mariadb:latest

COPY ./.secret-root-db-password /run/secrets/secret-root-db-password
COPY ./.secret-pinattsu-db-password /run/secrets/secret-pinattsu-db-password

