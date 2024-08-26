FROM openjdk:17
LABEL maintainer="rogerranium@yahoo.fr"
EXPOSE 8081
ARG JAR_FILE=target/mail-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
