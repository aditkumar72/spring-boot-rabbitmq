package com.example.messaging_rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    private static final int DEFAULT_COUNT_DOWN = 5;

    private CountDownLatch countDownLatch = new CountDownLatch(DEFAULT_COUNT_DOWN);

    public void resetLatch(int count) {
        countDownLatch = new CountDownLatch(count);
    }

    @RabbitListener(queues = MessagingRabbitmqApplication.QUEUE_NAME)
    public void subscriber1(String message) {
        System.out.println("Received <" + message + "> from subscriber1");
        countDownLatch.countDown();
    }

    @RabbitListener(queues = MessagingRabbitmqApplication.QUEUE_NAME)
    public void subscriber2(String message) {
        System.out.println("Received <" + message + "> from subscriber2");
        countDownLatch.countDown();
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
}
