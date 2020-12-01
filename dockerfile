FROM maven:3.6-jdk-11 AS build
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package

FROM adoptopenjdk/openjdk11:latest
COPY --from=build /usr/src/app/target/desafio*.jar /usr/app/desafio.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/desafio.jar"]
