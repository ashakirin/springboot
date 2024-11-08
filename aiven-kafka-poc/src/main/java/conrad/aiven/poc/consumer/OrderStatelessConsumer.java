package conrad.aiven.poc.consumer;

import conrad.aiven.poc.avro.Order;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Printed;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG;

/**
 * @author diego.costa on 18.02.21
 *
 * Simple Kafka Stream topology to consume the order topic and only printing the messages.
 *
 **/

public class OrderStatelessConsumer {

    private String topic;
    private Properties properties;
    private KafkaStreams streams;

    public static OrderStatelessConsumer create(Properties properties, String topic) {
        return new OrderStatelessConsumer(properties, topic);
    }

    private OrderStatelessConsumer(Properties properties, String topic) {
        addProducerProperties(properties);
        this.properties = properties;
        this.topic = topic;
    }

    public void start() {
        final StreamsBuilder builder = new StreamsBuilder();

        final Serde<Order> orderAvroSerde = new SpecificAvroSerde<>();
        Map<String, String> serdeConfigMap = buildSerdeConfigMap();
        orderAvroSerde.configure(serdeConfigMap, false);

        builder.stream(
                topic,
                Consumed.with(Serdes.Long(), orderAvroSerde)
        ).print(Printed.toSysOut());

        streams = new KafkaStreams(builder.build(), properties);

        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    /**
     * That's may looks weird, but Serde must also have the schema register url configured and
     * security related properties in order to be able to request the avro schema from Schema Register
     */
    private Map<String, String> buildSerdeConfigMap() {
        Map<String, String> serdeConfigMap = properties.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        entry -> entry.getValue().toString())
                );
        return serdeConfigMap;
    }

    private void addProducerProperties(Properties properties) {
        properties.put(CLIENT_ID_CONFIG, "stateless-cli.01");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "poc-stateless-app.01");
    }
}
