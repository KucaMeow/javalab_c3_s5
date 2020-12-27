package ru.itis.mongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.mongo.model.Car;
import ru.itis.mongo.model.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDto {
    private String name;
    private Double cash;
    private List<CarDto> cars;



    public static DriverDto from(Driver driver) {
        return DriverDto.builder()
                .cars(driver.getCars().stream().map(CarDto::from).collect(Collectors.toList()))
                .cash(driver.getCash())
                .name(driver.getName())
                .build();
    }
}
