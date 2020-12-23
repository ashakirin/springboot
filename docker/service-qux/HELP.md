# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### Commands
* Build and push Docker image: 
```mvn compile jib:build```
```(daemon: mvn compile jib:dockerBuild)```
* Run Docker image: 
```docker run -p 8081:8081 ashakirin/demo-service-foo:0.0.1-SNAPSHOT```
* Test application:
```http://localhost:8081/foo/hello```

mvn compile jib:build
(daemon: mvn compile jib:dockerBuild)

docker run -p 8083:8083 docker.io/ashakirin/demo-service-qux
http://localhost:8083/qux/hello

