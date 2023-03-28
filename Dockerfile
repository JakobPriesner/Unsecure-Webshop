# Build step
FROM maven:3.8.7-openjdk-18-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src/ src/

RUN if [ ! -d "/app/target" ]; then mkdir /app/target; fi && \
    mvn clean package


# Run Step
FROM tomcat:latest

WORKDIR /app

COPY --from=build /app/target/progPorj-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/progPorj.war
CMD ["catalina.sh", "run"]
