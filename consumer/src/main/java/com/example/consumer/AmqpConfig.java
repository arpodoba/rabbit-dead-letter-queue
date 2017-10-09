package com.example.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    public Exchange worksExchange() {
        return ExchangeBuilder.topicExchange("original.exchange")
                .durable()
                .build();
    }

    @Bean
    public Queue incomingQueue() {
        return QueueBuilder.durable("original")
                .withArgument("x-dead-letter-exchange", "") // move message to default exchange if error
                .withArgument("x-dead-letter-routing-key", "dead-letter") // set dead-letter queue routing key
                .build();
    }

    @Bean
    public Binding worksBinding() {
        return BindingBuilder
                .bind(incomingQueue())
                .to(worksExchange()).with("incoming-queue").noargs();
    }

    @Bean
    public Queue dlQueue() {
        return QueueBuilder
                .durable("dead-letter") // dead-letter queue
                .withArgument("x-message-ttl", 30000) // 30 sec wait in dead-letter queue
                .withArgument("x-dead-letter-exchange", "") // return message to default exchange after 30 sec
                .withArgument("x-dead-letter-routing-key", "original") // set original queue routing key
                .build();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
