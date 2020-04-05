FROM openjdk:8-jdk-alpine as builder
ADD . /src
WORKDIR /src
RUN ./mvnw package -DskipTests

FROM anapsix/alpine-java:latest
LABEL maintainer="Alex"
COPY --from=builder /src/target/*-runner.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]