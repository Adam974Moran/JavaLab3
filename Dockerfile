FROM maven:3.9.6
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/app/target/sunrise_and_sunset_application-0.0.1-SNAPSHOT.jar"]