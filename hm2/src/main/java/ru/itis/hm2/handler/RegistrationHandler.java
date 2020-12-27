package ru.itis.hm2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.itis.hm2.dto.Answer;
import ru.itis.hm2.model.PassportData;
import ru.itis.hm2.repository.PassportDataRepository;
import ru.itis.hm2.repository.PersonalDataRepository;

import java.io.*;
import java.util.concurrent.TimeoutException;

@Service
public class RegistrationHandler {

    private final static String EXCHANGE_NAME = "registration";
    private final ObjectMapper objectMapper;
    private final PersonalDataRepository personalDataRepository;
    private final PassportDataRepository passportDataRepository;
    private static PassportData passportDataToHandle = null;

    public RegistrationHandler(ObjectMapper objectMapper, PersonalDataRepository personalDataRepository, PassportDataRepository passportDataRepository) {
        this.objectMapper = objectMapper;
        this.personalDataRepository = personalDataRepository;
        this.passportDataRepository = passportDataRepository;
        init();
    }

    public void init() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(1);
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                PassportData passportData  = objectMapper.readValue(message.getBody(), PassportData.class);
                passportData.setPersonalData(personalDataRepository
                        .findByPassCodeIs(passportData.getPersonalData().getPassCode()).get());
                passportDataRepository.save(passportData);

                while (passportDataToHandle != null){}
                passportDataToHandle = passportData;
            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void handlePassportData() {
        passportDataToHandle = null;
    }

    public PassportData nextPassportData() {
        return passportDataToHandle;
    }

    public ResponseEntity<Answer> register(int id) {
        PassportData passportData = passportDataRepository.getOne(id);
        passportData.getPersonalData().setConfirmed(true);
        personalDataRepository.save(passportData.getPersonalData());
        passportDataRepository.save(passportData);
        handlePassportData();
        return ResponseEntity.ok(Answer.builder()
                .code(0)
                .message("Passport data confirmed")
                .build());
    }
}
