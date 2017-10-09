package com.example.consumer;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;

public class InsufficientFundsException extends AmqpRejectAndDontRequeueException {

    public InsufficientFundsException(String message) {
        super(message);
    }

}
