package ru.itis.mongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.mongo.model.Car;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDto {
    private String colour;
    private List<String> features;
    private Integer maxSpeed;

    public static CarDto from(Car car) {
        return CarDto.builder()
                .colour(car.getColour())
                .features(new ArrayList<>(car.getFeatures()))
                .maxSpeed(car.getMaxSpeed())
                .build();
    }
}
