package ru.itis.hm2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.hm2.dto.Answer;
import ru.itis.hm2.dto.PassportDetails;
import ru.itis.hm2.model.PassportData;
import ru.itis.hm2.model.PersonalData;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeoutException;

@Service
public class PassportDetailsProcessorImpl implements PassportDetailsProcessor {


    private final String FANOUT_EXCHANGE_NAME = "passports";
    private final String DIRECT_EXCHANGE_NAME = "registration";
    private final ObjectMapper objectMapper;
    private final Channel channel;


    public PassportDetailsProcessorImpl (ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, "fanout");
            channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, "direct");
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
    @Override
    public ResponseEntity<Answer> processDetails(PassportDetails passportDetails, MultipartFile scan) {
        PersonalData personalData = PersonalData.builder()
                .name(passportDetails.getName())
                .passCode(passportDetails.getPass_code())
                .surname(passportDetails.getSurname())
                .build();
        PassportData passportData = PassportData.builder()
                .personalData(personalData)
                .build();

        try {
            passportData.setBase64File(Base64.getEncoder().encodeToString(scan.getBytes()));
            channel.basicPublish(DIRECT_EXCHANGE_NAME, "",null, objectMapper.writeValueAsString(passportData).getBytes());
            channel.basicPublish(FANOUT_EXCHANGE_NAME, "", null, objectMapper.writeValueAsString(personalData).getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return ResponseEntity.ok(
                Answer.builder()
                .code(0)
                .message("Passport details processed")
                .build()
        );
    }
}
