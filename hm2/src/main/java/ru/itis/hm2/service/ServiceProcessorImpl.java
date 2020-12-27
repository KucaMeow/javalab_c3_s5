package ru.itis.hm2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.itis.hm2.dto.Answer;
import ru.itis.hm2.dto.User;
import ru.itis.hm2.model.PersonalData;
import ru.itis.hm2.repository.PersonalDataRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Service
public class ServiceProcessorImpl implements ServiceProcessor {

    private final static String UNCONFIRMED_ROUTING_KEY = "user.exist.unconfirmed";
    private final static String CONFIRMED_ROUTING_KEY = "user.exist.confirmed";
    private final static String NOT_EXIST_ROUTING_KEY = "user.notexist";

    private final static String USERS_EXCHANGE = "users_exchange";
    private final static String EXCHANGE_TYPE = "topic";

    private final PersonalDataRepository personalDataRepository;
    private final ObjectMapper objectMapper;

    private final Channel channel;

    public ServiceProcessorImpl(PersonalDataRepository personalDataRepository, ObjectMapper objectMapper) {
        this.personalDataRepository = personalDataRepository;
        this.objectMapper = objectMapper;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(USERS_EXCHANGE, EXCHANGE_TYPE);

        } catch (IOException | TimeoutException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ResponseEntity<Answer> process(User user) {
        Optional<PersonalData> personalDataOptional = personalDataRepository.findByPassCodeIs(user.getPassCode());
        String currentKey = "";
        if(personalDataOptional.isEmpty()) {
            currentKey = NOT_EXIST_ROUTING_KEY;
        } else if(personalDataOptional.get().isConfirmed()) {
            currentKey = CONFIRMED_ROUTING_KEY;
        } else {
            currentKey = UNCONFIRMED_ROUTING_KEY;
        }

        try {
            channel.basicPublish(USERS_EXCHANGE, currentKey, null,
                    objectMapper.writeValueAsString(personalDataOptional.orElse(null)).getBytes());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        if(personalDataOptional.isEmpty()) {
            return ResponseEntity.ok(Answer.builder().code(1).message("Register please").build());
        } else if(personalDataOptional.get().isConfirmed()) {
            return ResponseEntity.ok(Answer.builder().code(1).message("Is in process").build());
        } else {
            return ResponseEntity.ok(Answer.builder().code(1).message("Wait for confirmation").build());
        }
    }
}
