package ru.itis.hm2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Answer {
    int code;
    String message;
}
