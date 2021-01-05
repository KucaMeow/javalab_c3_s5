package ru.javalab.rabbitmq1p.service;

import org.springframework.http.ResponseEntity;
import ru.javalab.rabbitmq1p.dto.PassportDetails;

/**
 * Service for handling passport details
 */
public interface PassportDetailsService {
    /**
     * Handle new passport details
     * @param passportDetails PassportDetails object with passport details info
     * @return Response entity OK with same passport details object
     */
    ResponseEntity<PassportDetails> handleNewDetails(PassportDetails passportDetails);
}
