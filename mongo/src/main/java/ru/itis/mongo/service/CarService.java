package ru.itis.mongo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.mongo.model.Car;
import ru.itis.mongo.repository.jpa.CarRepository;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public Car changeCarColour (String carId) {
        Car car = carRepository.findById(carId).orElseThrow(IllegalArgumentException::new);
        car.setColour("black");
        carRepository.save(car);
        return car;
    }
}
