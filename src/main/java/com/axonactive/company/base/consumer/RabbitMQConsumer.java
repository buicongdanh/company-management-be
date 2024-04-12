package com.axonactive.company.base.consumer;

import com.axonactive.company.base.config.RabbitMQConfig;
import com.axonactive.company.employee.dto.EmployeeResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class RabbitMQConsumer {
    private final String DIRECT_EXCHANGE = "direct-exchange";
    private final String TOPIC_EXCHANGE = "topic-exchange";

    private final String FANOUT_EXCHANGE = "fanout-exchange";
    private ConnectionFactory factory;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    private void init() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setVirtualHost(RabbitMQConfig.VIRTUAL_HOST);
        factory.setHost(RabbitMQConfig.HOST);
        factory.setPort(Integer.parseInt(RabbitMQConfig.PORT));
        factory.setUsername(RabbitMQConfig.USER);
        factory.setPassword(RabbitMQConfig.PASSWORD);

        this.receiveMessageDirectExchange();
        this.receiveMessageFanoutExchange();
    }

    public void receiveMessageDirectExchange() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(DIRECT_EXCHANGE, "direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, DIRECT_EXCHANGE, "secondKey");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received secondKey: '" + message + "'");
            mapper.findAndRegisterModules();
            EmployeeResponseDTO readValue = mapper.readValue(message, EmployeeResponseDTO.class);
            System.out.println("readValue secondKey = " + readValue.toString());
        };

        channel.basicConsume(queueName, false, deliverCallback, getCancelCallback(), getConsumerShutdownSignalCallback());
    }

    public void receiveMessageFanoutExchange() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        channel.queueBind("queue2", FANOUT_EXCHANGE, "");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received fanout consumer 2: '" + message + "'");
            mapper.findAndRegisterModules();
            EmployeeResponseDTO readValue = mapper.readValue(message, EmployeeResponseDTO.class);
            System.out.println("readValue fanout consumer 2= " + readValue.toString());
        };

        channel.basicConsume("queue2", true, deliverCallback, getCancelCallback(), getConsumerShutdownSignalCallback());
    }

    private CancelCallback getCancelCallback() {
        return consumerTag -> {
            System.out.println("CALL CANCEL");
        };
    }

    private ConsumerShutdownSignalCallback getConsumerShutdownSignalCallback() {
        return (consumerTag, shutdownSignalException) -> {
            System.out.println("CALL SHUTDOWN");
        };
    }
}
