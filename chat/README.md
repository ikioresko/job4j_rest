Just java chat application on Spring boot REST Api with security.

The application implements chat with rooms. Also, you can run this app on the Docker.


1. Download package
   sudo curl -L "https://github.com/docker/compose/releases/download/1.28.6/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
2. Give rules
   sudo chmod +x /usr/local/bin/docker-compose

   2.1. Download java 14 if you don't have yet
   wget -O Downloads/jdk.tar.gz https://download.java.net/openjdk/jdk14/ri/openjdk-14+36_linux-x64_bin.tar.gz

   2.2. Extract
   sudo mkdir /usr/lib/jvm
   sudo tar -xf Downloads/jdk.tar.gz -C /usr/lib/jvm

   2.3. Set system variables and check version
   export JAVA_HOME=/usr/lib/jvm/jdk-14
   export PATH=$PATH:$JAVA_HOME/bin
   java --version
   javac --version

3. mvn install
4. Change Dockerfile and docker-compose.yml if you need it
5. docker build -t chat .
6. docker-compose up

Thats all. Now you can use app. (Note: This app with Spring Security and used Auth filter)

![alt text](https://github.com/ikioresko/job4j_rest/blob/aab79220083b1774b6dc9fa9dd03129471a0200c/chat/images/1.png)