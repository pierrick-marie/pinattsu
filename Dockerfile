FROM eclipse-temurin:17.0.6_10-jre
RUN mkdir /opt/app
COPY ./build/libs/blog-0.0.1-SNAPSHOT.jar /opt/app/spring-app.jar
CMD ["java", "-jar", "/opt/app/spring-app.jar"]
