package conrad.aiven.poc.producer;

import conrad.aiven.poc.avro.Order;
import conrad.aiven.poc.avro.OrderState;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

/**
 * @author diego.costa on 16.02.21
 * <p>
 * An Avro Order Producer that sends orders to Kafka in an asynchronously but checking the results with a callback.
 * Synchronous send is also possible, but not recommended.
 * Specially to guarantee high throughput, kafka producer uses an internal buffer to accumulate messages and only then send in batch mode.
 * There are a some configurations that regulates how this producer buffer behaves.
 * That's a good approach with the callback as the possible point of error handling.
 **/
public class OrderProducer {

    private Random randomID = new Random();
    private KafkaProducer producer;
    private String topic;

    public static OrderProducer create(Properties properties, String topic) {
        return new OrderProducer(properties, topic);
    }

    private OrderProducer(Properties properties, String topic) {
        addProducerProperties(properties);
        producer = new KafkaProducer<Long, Order>(properties);
        this.topic = topic;
    }

    /**
     * Create a random Order and send asynchronously to Kafka
     */
    public OrderProducer sendRandomOrder() {
        Order order = createRandomOrder();
        ProducerRecord record = new ProducerRecord<Long, Order>(topic, order.getId(), order);
        producer.send(record, (recordMetadata, e) -> {
            if (e != null) {
                System.out.println("Problem sending order: " + record.key());
            }
        });
        return this;
    }

    /**
     * As the producer uses a buffer, if the limit is nor reached, the messages are not send. "Close" method call
     * will force all the messages that are still in buffer to be send.
     */
    public void close() {
        producer.close();
    }

    private Order createRandomOrder() {
        Order order = new Order();
        long id = randomID.nextInt(90000000);
        order.setDescription("Some description of order " + id);
        order.setId(id);
        order.setState(OrderState.values()[randomID.nextInt(3)]);
        order.setPrice(Double.valueOf(new DecimalFormat("0.00").format(randomID.nextInt(50) * 3.57)));
        order.setUser("diego.costa");
        return order;
    }

    private void addProducerProperties(Properties properties) {
        properties.put(CLIENT_ID_CONFIG, String.valueOf(randomID.nextInt(1000)));
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        properties.put(ACKS_CONFIG, "all");
    }

}
