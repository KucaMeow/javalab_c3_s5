package ru.javalab.rabbitmq1p.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import ru.javalab.rabbitmq1p.dto.PassportDetails;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorImpl implements PdfGenerator {
    @Override
    public void addParagraph(String paragraph, Document document) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 14, BaseColor.BLACK);
        Paragraph para = new Paragraph( paragraph, font);
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
        document.add(Chunk.NEWLINE);
    }

    @Override
    public byte[] createPdfFromPassDetailsWithLabels(String documentLabel, String nameLabel, String surnameLabel, String pass_codeLabel, String ageLabel, String dateLabel, PassportDetails passportDetails) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            addParagraph(documentLabel, document);
            addParagraph(nameLabel + ": " + passportDetails.getName(), document);
            addParagraph(surnameLabel + ": " + passportDetails.getSurname(), document);
            addParagraph(pass_codeLabel + ": " + passportDetails.getPass_code(), document);
            addParagraph(ageLabel + ": " + passportDetails.getAge(), document);
            addParagraph(dateLabel + ": " + passportDetails.getIssue(), document);
            document.close();
        } catch (DocumentException e) {
            throw new IllegalStateException(e);
        }
        return out.toByteArray();
    }
}
