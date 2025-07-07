FROM openjdk:17.0.2-jdk-slim-buster

EXPOSE 8080

ADD target/TransferMoney-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]