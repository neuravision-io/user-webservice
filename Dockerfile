FROM openjdk:21-jdk
MAINTAINER Samuel Abramov
COPY build/libs/user-webservice-0.0.1-RELEASE.jar user-webservice-0.0.1-RELEASE.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/user-webservice-0.0.1-RELEASE.jar"]