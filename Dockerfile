FROM adoptopenjdk/openjdk11:alpine-slim
ADD build/libs/book-exchange-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]