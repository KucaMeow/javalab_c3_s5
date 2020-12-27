package ru.itis.hm2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.hm2.model.PersonalData;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class ServiceHandler {


    private final static String ROUTING_KEY = "user.*";
    private final static String USERS_EXCHANGE = "users_exchange";
    private final ObjectMapper objectMapper;

    public ServiceHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        init();
    }

    public void init() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, USERS_EXCHANGE, ROUTING_KEY);

            channel.basicConsume(queueName, false, (consumerTag, message) -> {
                PersonalData personalData  = objectMapper.readValue(message.getBody(), PersonalData.class);
                log.info("User used service with: " + objectMapper.writeValueAsString(personalData));
            }, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
