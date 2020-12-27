package ru.itis.mongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverRequest {
    private String name;
    private Double cash;
    private String carColour;
    private Integer carMaxSpeed;
}
