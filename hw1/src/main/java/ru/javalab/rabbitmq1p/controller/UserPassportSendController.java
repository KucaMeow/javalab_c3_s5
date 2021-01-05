package ru.javalab.rabbitmq1p.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.javalab.rabbitmq1p.dto.PassportDetails;
import ru.javalab.rabbitmq1p.service.PassportDetailsService;

/**
 * REST Controller for sending person's passport details
 */
@RestController
public class UserPassportSendController {

    private final PassportDetailsService passportDetailsService;

    public UserPassportSendController(PassportDetailsService passportDetailsService) {
        this.passportDetailsService = passportDetailsService;
    }

    /**
     * Handle sending new passport details to server
     * @return ResponseEntity ok with same details if it was saved to server
     */
    @ApiOperation(
            value = "Handle sending new passport details to server",
            produces = "ResponseEntity ok with same details if it was saved to server"
    )
    @PostMapping()
    public ResponseEntity<PassportDetails> sendPassportDetails(@RequestBody PassportDetails passportDetails) {
        return passportDetailsService.handleNewDetails(passportDetails);
    }
}
