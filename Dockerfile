#getting base image
FROM java:8
ADD /target/ServiceDiscovery-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
RUN pwd
RUN ls -ltr