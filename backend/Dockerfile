# Build step
FROM maven:3.8.7-openjdk-18-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src src/

RUN if [ ! -d "/app/target" ]; then mkdir /app/target; fi && \
    mvn clean package


# Run Step
FROM openjdk:21-slim-buster

WORKDIR /app

COPY --from=build /app/target/progPorj-1.0-SNAPSHOT.jar /app/progPorj.jar

ENV CLASSPATH=/usr/local/tomcat/lib/*:$CLASSPATH

CMD ["java", "-cp", "progPorj.jar", "de.fhws.biedermann.webshop.Start"]
