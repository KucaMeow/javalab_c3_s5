package ru.itis.hm2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.hm2.model.PersonalData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class ServiceUnconfirmedHandler {

    private final static String ROUTING_KEY = "user.exist.unconfirmed";
    private final static String USERS_EXCHANGE = "users_exchange";
    private final ObjectMapper objectMapper;

    public ServiceUnconfirmedHandler(ObjectMapper objectMapper) {
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
                PrintWriter printWriter = new PrintWriter(new FileOutputStream("unconfirmed.txt", true), true);

                printWriter.println("Unconfirmed user tried to use service");
                printWriter.println(objectMapper.writeValueAsString(personalData));
                printWriter.println();
                printWriter.close();
            }, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
