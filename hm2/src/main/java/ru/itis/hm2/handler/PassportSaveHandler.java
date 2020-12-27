package ru.itis.hm2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.hm2.model.PersonalData;
import ru.itis.hm2.repository.PersonalDataRepository;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class PassportSaveHandler {

    private final static String EXCHANGE_NAME = "passports";
    private final ObjectMapper objectMapper;
    private final PersonalDataRepository personalDataRepository;

    public PassportSaveHandler(ObjectMapper objectMapper, PersonalDataRepository personalDataRepository) {
        this.objectMapper = objectMapper;
        this.personalDataRepository = personalDataRepository;
        init();
    }

    public void init() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(1);
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                PersonalData personalData  = objectMapper.readValue(message.getBody(), PersonalData.class);
                personalData.setConfirmed(false);
                personalDataRepository.save(personalData);
                log.info("Personal data with id " + personalData.getId() + " is saved");
            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
