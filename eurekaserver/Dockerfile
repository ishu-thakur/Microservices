#start a base image containing java runtime
FROM openjdk:11-slim as build

#information who will maintain the image
MAINTAINER ishuthakur

#add application jar to the container
COPY target/eurekaserver-0.0.1-SNAPSHOT.jar eurekaserver-0.0.1-SNAPSHOT.jar

#Execute the application
ENTRYPOINT ["java","-jar","/eurekaserver-0.0.1-SNAPSHOT.jar"]

