package ru.itis.hm2.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.hm2.dto.Answer;
import ru.itis.hm2.dto.PassportDetails;

public interface PassportDetailsProcessor {
    public ResponseEntity<Answer> processDetails(PassportDetails passportDetails, MultipartFile scan);
}
