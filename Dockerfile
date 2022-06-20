# syntax=docker/dockerfile:1

FROM openjdk:18 as base

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src

FROM base as test
RUN ["./mvnw", "test"]

FROM base as development
CMD ["./mvnw", "spring-boot:run"]

FROM base as build
RUN ./mvnw package

FROM openjdk:18-jdk-slim as production
EXPOSE 8080

COPY --from=build /app/target/conversion-*.jar /conversion.jar

CMD ["java", "-jar", "/conversion.jar"]