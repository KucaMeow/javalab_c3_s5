package ru.itis.hm2.service;

import org.springframework.http.ResponseEntity;
import ru.itis.hm2.dto.Answer;
import ru.itis.hm2.dto.User;

public interface ServiceProcessor {
    public ResponseEntity<Answer> process(User user);
}
