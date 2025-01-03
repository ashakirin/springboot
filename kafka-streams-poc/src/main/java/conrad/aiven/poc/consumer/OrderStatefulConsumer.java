package conrad.aiven.poc.consumer;

import conrad.aiven.poc.avro.Order;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Printed;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG;

/**
 * @author diego.costa on 18.02.21
 **/

public class OrderStatefulConsumer {
    private String topic;
    private Properties properties;
    private KafkaStreams streams;

    public static OrderStatefulConsumer create(Properties properties, String topic) {
        return new OrderStatefulConsumer(properties, topic);
    }

    private OrderStatefulConsumer(Properties properties, String topic) {
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
        ).groupBy((id, order) -> order.getState().toString(), Grouped.with(Serdes.String(), orderAvroSerde))
                .count()
                .toStream().print(Printed.toSysOut());

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
        properties.put(CLIENT_ID_CONFIG, "stateful-cli.01");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "poc-stateful-app.01");
    }
}
