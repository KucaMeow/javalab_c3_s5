package ru.javalab.rabbitmq1p.handler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Service;
import ru.javalab.rabbitmq1p.dto.PassportDetails;
import ru.javalab.rabbitmq1p.service.PdfGenerator;

import java.io.*;
import java.util.concurrent.TimeoutException;

/**
 * Handler for passports exchange
 * Generates pdf for getting credit from passport info
 */
@Service
public class GettingCreditHandler {

    private final static String EXCHANGE_NAME = "passports";
    private final static String EXCHANGE_TYPE = "fanout";

    private final PdfGenerator pdfGenerator;

    public GettingCreditHandler(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            //Подключение к очереди RabbitMQ
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, EXCHANGE_NAME, "");

            //Обработка сообщения из RabbitMQ
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                PassportDetails passportDetails = null;
                try {
                    passportDetails = (PassportDetails) (new ObjectInputStream(new ByteArrayInputStream(message.getBody()))).readObject();
                    byte[] out = pdfGenerator.createPdfFromPassDetailsWithLabels(
                            "KREDIT NA MILLIONY DENYAK\nAHAHAHAHHAHAHAH",
                            "IMYA LOHA",
                            "FAMILIYA HAHAHAHHA",
                            "PASPORT NOMER",
                            "VOZRAST",
                            "DATA PASPORTA",
                            passportDetails
                    );
                    File file = new File("documents/credits/" + passportDetails.getPass_code() + ".pdf");
                    file.createNewFile();

                    FileOutputStream stream = new FileOutputStream(file);
                    stream.write(out);
                    stream.flush();
                    stream.close();
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }

            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
