package conrad.aiven.poc;

import conrad.aiven.poc.config.ConnectionProperties;
import conrad.aiven.poc.consumer.OrderStatefulConsumer;
import conrad.aiven.poc.consumer.OrderStatelessConsumer;
import conrad.aiven.poc.producer.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author diego.costa on 19.02.21
 **/
@Service
@EnableScheduling
public class KafkaThingsStarter {

    @Autowired
    private ConnectionProperties properties;

    @Value("${kafka.topic}")
    private String topic;

    private OrderProducer orderProducer;

    @PostConstruct
    private void run() throws IOException {
        OrderStatelessConsumer
                .create(properties.buildProperties(), topic)
                .start();

        OrderStatefulConsumer
                .create(properties.buildProperties(), topic)
                .start();

        orderProducer = OrderProducer
                .create(properties.buildProperties(), topic);
    }

    @Scheduled(fixedDelay = 10000)
    public void createOrders() {
        orderProducer.sendRandomOrder();
    }

}
