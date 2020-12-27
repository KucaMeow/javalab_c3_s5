package ru.itis.hm2.controller;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.hm2.dto.Answer;
import ru.itis.hm2.dto.PassportDetails;
import ru.itis.hm2.dto.User;
import ru.itis.hm2.handler.RegistrationHandler;
import ru.itis.hm2.model.PassportData;
import ru.itis.hm2.service.PassportDetailsProcessor;
import ru.itis.hm2.service.ServiceProcessor;

import java.io.File;

@RestController
public class MainController {

    @Autowired
    PassportDetailsProcessor passportDetailsProcessor;
    @Autowired
    ServiceProcessor serviceProcessor;
    @Autowired
    RegistrationHandler registrationHandler;

    @PostMapping("/passport")
    public ResponseEntity<Answer> sendData(PassportDetails passportDetails, MultipartFile scan) {
        return passportDetailsProcessor.processDetails(passportDetails, scan);
    }

    @GetMapping("/register")
    public ResponseEntity<PassportData> getNextToRegister() {
        return ResponseEntity.ok(registrationHandler.nextPassportData());
    }

    @PostMapping("/register/{id}")
    public ResponseEntity<Answer> registerUser(@PathVariable int id) {
        return registrationHandler.register(id);
    }

    @PostMapping("/service")
    public ResponseEntity<Answer> service(User userData) {
        return serviceProcessor.process(userData);
    }
}
