FROM tomcat:10.1.25-jdk17-temurin
LABEL maintainer="rogerranium@yahoo.fr"
COPY /target/ROOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
