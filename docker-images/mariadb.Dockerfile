FROM mariadb:latest

COPY .secret-root-db-password /run/secrets/secret-root-db-password
COPY .secret-user-db-password /run/secrets/secret-user-db-password

