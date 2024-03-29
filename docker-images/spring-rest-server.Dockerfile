FROM pimarie/pinattsu-rest-server-jar:0.1.0

COPY .secret-user-db-password /run/secrets/secret-user-db-password

COPY ./docker-images/spring-rest-server-run.sh /usr/local/bin/run-rest-server.sh 
RUN chmod +x /usr/local/bin/run-rest-server.sh 
CMD ["/usr/local/bin/run-rest-server.sh"]