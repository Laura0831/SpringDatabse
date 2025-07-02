FROM openjdk:17-jdk-alpine
MAINTAINER Laura
COPY target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]