## Aiven Kafka POC

Just some tests using Aiven Kafka managed version.

A standalone Spring-Boot application that, when running, initiate to produce an AVRO version of a `Order` and consumes
it with two different Kafka Stream topologies. The stateless only prints the just consumed message, the other one
aggregates and count grouped by the `Order.State`.

- Avro Kafka Producer
- Kafka Stream Stateless topology
- Kafka Stream Stateful topology
- Java 11
- Kafka 2.7.0

There is no specificity related to Aiven besides the authentication

![Diagram](aiven-kafka-poc.png?raw=true "POC Diagram"){width=50% height=50%}

### How to run

- Create a free Aiven account and add the Kafka Service
- In Aiven Admin Console, enable Schema Register
- Create a topic named as `order`

#### Authentication

- From Aiven Admin Console, Kafka Overview, download to `/src/main/resources` :_Access Key_, _Access Certificate_, _CA
  Certificate_
- Java certificate and truststore creation commands inside `/src/main/resources`:

`openssl pkcs12 -export -inkey service.key -in service.cert -out client.keystore.p12 -name service_key`

`keytool -import -file ca.pem -alias CA -keystore client.truststore.jks`

All the related properties must be configured at the `application.property` file:

``` 
kafka.broker=[Kafka Service URI]
kafka.schema-register=[Schema Register Service URI]
# use this format: https://%s:%s@kafka-d575776-pm-7c4f.aivencloud.com:16853
# kafka username:password will be concatenated
kafka.ssl.keystore.password=[your java keystore password]
kafka.ssl.key.password=[your java ssl key password]
kafka.topic=order
kafka.user.username=avnadmin
kafka.user.password=[your admin password]
```

### Spring-Boot run

`mvn spring-boot:run`

The producer will start to generate `Orders`, the stateless topology will print all the consumed messages...

```  
[KSTREAM-SOURCE-0000000000]: 75760106, {"id": 75760106, "user": "diego.costa", "state": "VALIDATED", "price": 128.52, "description": "Some description of order 75760106"}
[KSTREAM-SOURCE-0000000000]: 25673245, {"id": 25673245, "user": "diego.costa", "state": "VALIDATED", "price": 171.36, "description": "Some description of order 25673245"}
[KSTREAM-SOURCE-0000000000]: 76753596, {"id": 76753596, "user": "diego.costa", "state": "VALIDATED", "price": 32.13, "description": "Some description of order 76753596"}
```

the stateful topology will consume, aggregate and count orders by state

``` 
[KTABLE-TOSTREAM-0000000007]: VALIDATED, 19
[KTABLE-TOSTREAM-0000000007]: CREATED, 21
```

### Aiven References

- https://help.aiven.io/en/articles/489572-getting-started-with-aiven-kafka
- https://help.aiven.io/en/articles/2302613-using-schema-registry-with-aiven-kafka

### Apache Kafka & Confluent References

- https://kafka.apache.org/0102/documentation/#quickstart_kafkastreams
- https://docs.confluent.io/platform/current/streams/developer-guide/dsl-api.html
- https://docs.confluent.io/platform/current/streams/developer-guide/datatypes.html
