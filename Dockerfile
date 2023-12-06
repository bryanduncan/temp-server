#FROM --platform=linux/amd64 openjdk:8
#WORKDIR /server
#COPY ./src/ /server/
#RUN javac main/java/server/MyServer.java main/java/server/RequestHandler.java main/java/server/MyHandler.java main/java/server/MongoDB.java
#EXPOSE 8100
#ENTRYPOINT ["java", "server/MyServer"]

# Stage 1: Build the application with Gradle
FROM gradle:jdk8 AS build
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

# Stage 2: Create a smaller image with only the JAR file and dependencies
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./app/build/libs/app.jar .

COPY ./app/src/main/java/server/index.html ./index.html
EXPOSE 8100

# Run the application
CMD ["java", "-jar", "app.jar"]