# k8s-native-tutorial-app

This tutorial application demonstrating cloud native application in minikube environment.
It is based on SpringBoot technology.
Tutorial application exposes simple REST API, persistence is based on MySQL.
Kubernetes deployment uses Helm Chart.

# MySQL configuration
This version of application requires MySQL instance running on localhost on port 3306.
MySQL instance must be initialized using init-db.sql script (creates database, users and permissions).
Database tables will be created automatically by starting SpringBoot application
Helm Chart overwrites default MySQL URL and credentials to values configured in values.yaml.
To access MySQL instance on localhost, URL in values.yaml contains special host name: host.minikube.internal allowing localhost access

Packages:
- controller: REST controller and DTOs
- persistence: persistence repository and entities (dependent on version either simple in memory storage or MySQL based storage)
- service: business logic

## Prerequisites
- jdk > 13
- Maven
- Docker
- Minikube
- Helm
- MySQL instance 8.X must be running on localhost on port 3306 and initialized with init-db.sql script 

## Build and push image
To build image it is necessary to run the following maven command:
```
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=ashakirin/k8s-native-tutorial-app:0.0.2-SNAPSHOT
```
To publish image to DockerHub, it is necessary to run:
```
docker push YOUR_REPO/k8s-native-tutorial-app:0.0.2-SNAPSHOT
```

## Deploy using Helm Chart
To deploy application to Kubernetes environment (minikube), it is enough to run:
```
helm install k8s-native-tutorial-chart ./helm/k8s-native-tutorial-app
```

## Check the status
To validate either deployment was successful or not, run:
```
kubectl get pods
```
The output should look like:
```
NAME                                       READY   STATUS    RESTARTS   AGE
k8s-native-tutorial-app-7d8455984c-ww87q   1/1     Running   0          8s
```

To validate installed ingress, run:
```
kubectl get ingress
```
The output should look like:
```
NAME                              CLASS   HOSTS               ADDRESS     PORTS   AGE
k8s-native-tutorial-app-ingress   nginx   k8s-tutorial.info   localhost   80      119s
```

## Send request direct to the service
To test service directly, run the following command:
```
minikube service k8s-native-tutorial-app-service
```
Minikube will display endpoint for your application:
```
|-----------|---------------------------------|-------------|---------------------------|
| NAMESPACE |              NAME               | TARGET PORT |            URL            |
|-----------|---------------------------------|-------------|---------------------------|
| default   | k8s-native-tutorial-app-service |        8080 | http://192.168.49.2:30935 |
|-----------|---------------------------------|-------------|---------------------------|
🏃  Starting tunnel for service k8s-native-tutorial-app-service.
|-----------|---------------------------------|-------------|------------------------|
| NAMESPACE |              NAME               | TARGET PORT |          URL           |
|-----------|---------------------------------|-------------|------------------------|
| default   | k8s-native-tutorial-app-service |             | http://127.0.0.1:54072 |
|-----------|---------------------------------|-------------|------------------------|
```
You can test REST API using the following command (the port be used from previous output):
```
curl 127.0.01:PORT_FROM_OUTPUT/reservations   
```
Command should return the list of car reservations, if any was already created, or empty list:
```
[{"id":"1","carModel":"VW","startDate":"2023-01-10","finishDate":"2023-01-10"}]
``` 

To create new reservation, try the following:
```
curl --location --request POST --header 'Content-Type: application/json' --data-raw '{ "carModel":"VW", "startDate":"2022-12-27", "finishDate":"2022-12-29"}' localhost:PORT_FROM_OUTPUT/reservations   
```

## Test Rest API through the ingress
In order to access API through the ingress, the following commands are necessary:
```
minikube addons enable ingress
minikube tunnel
```
Command for test API through the ingress will look like:
```
curl http://localhost/reservations
```
The output will be the same as with service test:
```
[{"id":"1","carModel":"VW","startDate":"2023-01-10","finishDate":"2023-01-10"}]
```