FROM maven:3.6.3-openjdk-14
WORKDIR chat
ADD target/chat-1.jar app.jar
ENTRYPOINT java -jar app.jar