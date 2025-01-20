FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY ./pom.xml ./pom.xml
COPY ./src ./src
ENV JAVA_OPTS="-Dspring.devtools.restart.enabled=true"
CMD ["mvn", "spring-boot:run"]