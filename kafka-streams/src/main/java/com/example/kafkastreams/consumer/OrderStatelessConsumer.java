package com.example.kafkastreams.consumer;

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

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG;

/**
 * @author diego.costa on 18.02.21
 * <p>
 * Simple Kafka Stream topology to consume the order topic and only printing the messages.
 **/
public class OrderStatelessConsumer {

    private String topic;
    private KafkaStreams streams;

    public static OrderStatelessConsumer create(String topic) {
        return new OrderStatelessConsumer(topic);
    }

    private OrderStatelessConsumer(String topic) {
        this.topic = topic;
    }

    public void start() {
        final StreamsBuilder builder = new StreamsBuilder();

        builder.stream(
                topic,
                Consumed.with(Serdes.String(), Serdes.String())
        ).print(Printed.toSysOut());

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put(CLIENT_ID_CONFIG, "stateless-cli.01");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "poc-stateless-app.01");

        streams = new KafkaStreams(builder.build(), props);

        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
