FROM pimarie/pinattsu-rest-server:0.1.0

COPY ./.secret-pinattsu-db-password /run/secrets/secret-pinattsu-db-password

COPY ./spring-rest-server-run.sh /usr/local/bin/run-rest-server.sh 
RUN chmod +x /usr/local/bin/run-rest-server.sh 
CMD ["/usr/local/bin/run-rest-server.sh"]