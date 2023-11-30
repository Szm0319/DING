package com.example.ding.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue myQueue() {
        return new Queue("newOrder", true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("Order");
    }

    @Bean
    public Binding binding(Queue myQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(myQueue).to(directExchange).with("newOrder");
    }

}
