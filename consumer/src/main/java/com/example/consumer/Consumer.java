package com.example.consumer;

import com.example.consumer.api.PaymentOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "original")
    public void process(@Payload PaymentOrder paymentOrder, @Header(required = false, name = "x-death") List<String> xDeathHeader) throws InsufficientFundsException {
        logger.info("Processing at \'{}\' payload \'{}\'", new Date(), paymentOrder);

        // TODO add check xDeathHeader for count
        if (true) {
            throw new InsufficientFundsException("insufficient funds on account " + paymentOrder.getFrom());
        }
    }

}