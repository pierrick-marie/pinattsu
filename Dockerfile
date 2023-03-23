FROM eclipse-temurin:17.0.6_10-jre
RUN mkdir /opt/app
COPY ./build/libs/pinattsu-rest-server-0.1.0.jar /opt/app/rest-server.jar
CMD ["java", "-jar", "/opt/app/rest-server.jar"]
