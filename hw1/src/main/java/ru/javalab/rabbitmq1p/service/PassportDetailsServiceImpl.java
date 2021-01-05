package ru.javalab.rabbitmq1p.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.javalab.rabbitmq1p.dto.PassportDetails;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeoutException;

//Получается наш продюсер, который будет получать как-то паспорт и отправлять на консьюмеров
@Service
public class PassportDetailsServiceImpl implements PassportDetailsService {

    private final static String EXCHANGE_NAME = "passports";
    private final static String EXCHANGE_TYPE = "fanout";
    private final Channel channel;

    public PassportDetailsServiceImpl () {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public ResponseEntity<PassportDetails> handleNewDetails(PassportDetails passportDetails) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(passportDetails);
            channel.basicPublish(EXCHANGE_NAME, "",null, out.toByteArray());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return ResponseEntity.ok(passportDetails);
    }
}
