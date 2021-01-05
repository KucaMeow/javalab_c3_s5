package ru.javalab.rabbitmq1p.handler;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;
import ru.javalab.rabbitmq1p.dto.PassportDetails;
import ru.javalab.rabbitmq1p.service.PdfGenerator;

import java.io.*;
import java.util.concurrent.TimeoutException;


/**
 * Handler for passports exchange
 * Generates pdf for put passport info from fired employee
 */
@Service
public class GettingFiredHandler {

    private final static String EXCHANGE_NAME = "passports";
    private final static String EXCHANGE_TYPE = "fanout";

    private final PdfGenerator pdfGenerator;

    public GettingFiredHandler(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                PassportDetails passportDetails = null;
                try {
                    passportDetails = (PassportDetails) (new ObjectInputStream(new ByteArrayInputStream(message.getBody()))).readObject();
                    byte[] out = pdfGenerator.createPdfFromPassDetailsWithLabels(
                            "Getting fired",
                            "Name",
                            "Surname",
                            "Passport number",
                            "Age",
                            "Passport get date",
                            passportDetails
                    );
                    File file = new File("documents/fired/" + passportDetails.getPass_code() + ".pdf");
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
