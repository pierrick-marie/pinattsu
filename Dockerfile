FROM eclipse-temurin:17-alpine

RUN mkdir /opt/app
COPY ./build/libs/pinattsu-rest-server-0.1.0.jar /opt/app/rest-server.jar

COPY ./run.sh /usr/local/bin/run-rest-server.sh
RUN chmod +x /usr/local/bin/run-rest-server.sh
CMD ["/usr/local/bin/run-rest-server.sh"]