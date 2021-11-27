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


To run with Kubernetes you need:

1. Install kubectl and minikube

   1.1 curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl

   1.2 sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl

   1.3 kubectl version (Check that everything is ok. You can skip this step)

   1.4 curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64

   1.5 sudo install minikube-linux-amd64 /usr/local/bin/minikube

   1.6 minikube config set driver docker

   1.7 minikube start

2. kubectl get nodes (Check that cluster started)

3. Create postgresdb-secret.yml like this (you can use [base64 converter](http://base64.ru/) )

         apiVersion: v1
         kind: Secret
         metadata:
         name: postgresdb-secret
         type: Opaque
         data:
         postgres-username: cG9zdGdyZXM= //use here your login with base64 and drop this comment
         postgres-password: cGFzc3dvcmQ= //use here your password with base64 and drop this comment

4. kubectl apply -f postgresdb-secret.yml

5. Check that secret is created: kubectl get secret

6. Create postgresdb-configmap.yml like this

          apiVersion: v1
          kind: ConfigMap
          metadata:
          name: postgresdb-configmap
          data:
          database_url: jdbc:postgresql://postgresdb-service/postgres
          postgres-db: postgres

7. kubectl apply -f postgresdb-configmap.yml

8. Check: kubectl get configmaps

9. Now create postgresdb-deployment.yml like this 

          apiVersion: apps/v1
          kind: Deployment
          metadata:
             name: postgresdb-deployment
             labels:
                app: postgresdb
          spec:
             replicas: 1
             selector:
                matchLabels:
                  app: postgresdb
             template:
                metadata:
                  _labels:_
                  app: postgresdb
          spec:
             containers:
                  - name: postgresdb
                  image: postgres
                  ports:
                     - containerPort: 5432
                  env:
                     - name: POSTGRES_USER
                      valueFrom:
                        secretKeyRef:
                          name: postgresdb-secret
                          key: postgres-username
                     - name: POSTGRES_PASSWORD
                      valueFrom:
                        secretKeyRef:
                          name: postgresdb-secret
                          key: postgres-password
                     - name: POSTGRES_DB
                      valueFrom:
                        configMapKeyRef:
                          name: postgresdb-configmap
                          key: postgres-db
          ---
          apiVersion: v1
          kind: Service
          metadata:
            name: postgresdb-service
          spec:
            selector:
              app: postgresdb
            ports:
              - protocol: TCP
              port: 5432
              targetPort: 5432

10. Run:  kubectl apply -f postgresdb-deployment.yml

11. Check: kubectl get pods (or if you need you can se logs: kubectl logs -l app=postgresdb)

12. Now create spring-deployment.yml like this

          apiVersion: apps/v1
          kind: Deployment
          metadata:
             name: spring-boot
             labels:
                app: spring-boot
          spec:
             replicas: 1
             selector:
                matchLabels:
                  app: spring-boot
             template:
                metadata:
                  labels:
                  app: spring-boot
          spec:
             containers:
                  - name: spring-boot
                  image: *login*/*repo* (for Ex.: ikioresko/job4j_chat)
                  ports:
                     - containerPort: 8080
                  env:
                     - name: SPRING_DATASOURCE_USERNAME
                      valueFrom:
                        secretKeyRef:
                          name: postgresdb-secret
                          key: postgres-username
                     - name: SPRING_DATASOURCE_PASSWORD
                      valueFrom:
                        secretKeyRef:
                          name: postgresdb-secret
                          key: postgres-password
                     - name: SPRING_DATASOURCE_URL
                      valueFrom:
                        configMapKeyRef:
                          name: postgresdb-configmap
                          key: database_url
          ---
          apiVersion: v1
          kind: Service
          metadata:
            name: spring-boot-service
          spec:
            selector:
              app: spring-boot
            type: LoadBalancer
            ports:
              - protocol: TCP
              port: 8080
              targetPort: 8080
              nodePort: 32100

13. Run: kubectl apply -f spring-deployment.yml

14. Check: kubectl get pods

15. Check: kubectl get service

16. minikube service spring-boot-service

17. Also, you can add SQL schemas

    1. kubectl exec -it postgresdb-pod-name -- sh
    2. psql -U postgres

That's all.

![alt text](https://github.com/ikioresko/job4j_rest/blob/80d6a770da5238c84076aab054c50d2e992d5494/chat/images/2.png)
