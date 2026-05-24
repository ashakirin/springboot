package com.example.orderservice.events;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ topology for the externalized {@link com.example.orderservice.order.OrderCompleted}
 * event.
 *
 * <p>Spring Modulith's AMQP externalization publishes to <em>exchanges</em> by name (the value
 * before {@code ::} in the {@code @Externalized} annotation). We declare the topic exchange so
 * the broker recognises the target. External consumers bind their own queues to it.
 *
 * <p>The {@link JacksonJsonMessageConverter} bean (Jackson 3) is what
 * {@code spring.modulith.events.rabbitmq.enable-json} (default {@code true}) wires into the
 * outgoing {@code RabbitTemplate}, so the event record is serialized as JSON.
 */
@Configuration
class RabbitTopology {

    static final String EXCHANGE = "orders.completed";

    @Bean
    TopicExchange ordersCompletedExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
