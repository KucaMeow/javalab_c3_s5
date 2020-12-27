package ru.itis.hm2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.hm2.model.PersonalData;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class PassportHandlerWithDocumentGeneration {

    private final static String EXCHANGE_NAME = "passports";
    private final ObjectMapper objectMapper;

    public PassportHandlerWithDocumentGeneration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        init();
    }

    public void init() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(2);
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                PersonalData personalData  = objectMapper.readValue(message.getBody(), PersonalData.class);
                generatePdf(personalData);
            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void generatePdf(PersonalData personalData) {
        try {
            PdfReader pdfReader = new PdfReader("template.pdf");
            FileOutputStream fos = new FileOutputStream("pdf/" + personalData.getPassCode() + ".pdf");
            PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
            pdfStamper.setFormFlattening(true);
            pdfStamper.getAcroFields().setField("name", personalData.getName());
            pdfStamper.getAcroFields().setField("surname", personalData.getSurname());
            pdfStamper.getAcroFields().setField("passport", personalData.getPassCode());
            pdfStamper.close();
            pdfReader.close();
        } catch (IOException | DocumentException e) {
            throw new IllegalStateException(e);
        }
    }
}
