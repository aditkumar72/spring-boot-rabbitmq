package com.example.messaging_rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        receiver.resetLatch(10);
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.TOPIC_EXCHANGE_NAME, "foo.bar.baz", "Hello from RabbitMQ!");
        }
        receiver.getCountDownLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
