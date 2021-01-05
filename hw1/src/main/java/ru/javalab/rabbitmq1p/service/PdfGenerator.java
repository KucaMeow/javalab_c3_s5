package ru.javalab.rabbitmq1p.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import ru.javalab.rabbitmq1p.dto.PassportDetails;

/**
 * Interface of class to make pdf
 */
public interface PdfGenerator {
    /**
     * Adds paragraph to pdf document
     * @param paragraph Paragraph string to add
     * @param document Pdf document object to add paragraph to
     */
    void addParagraph (String paragraph, Document document) throws DocumentException;

    //Просто метод для удобства
    /**
     * Generates Document object with current labels and passportDetails info
     * @param documentLabel Label for document name
     * @param nameLabel Label for name field
     * @param surnameLabel Label for surname field
     * @param pass_codeLabel Label for password code field
     * @param ageLabel Label for age field
     * @param dateLabel Label for date of issue field
     * @return Document ByteArrayInputStream with added paragraphs
     */
    byte[] createPdfFromPassDetailsWithLabels(String documentLabel, String nameLabel, String surnameLabel,
                                              String pass_codeLabel, String ageLabel, String dateLabel,
                                              PassportDetails passportDetails);
}
