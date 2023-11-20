FROM --platform=linux/amd64 openjdk:8
WORKDIR /server
COPY ./src/ /server/
RUN javac server/MyServer.java server/RequestHandler.java server/MyHandler.java
EXPOSE 8100
ENTRYPOINT ["java", "server/MyServer"]