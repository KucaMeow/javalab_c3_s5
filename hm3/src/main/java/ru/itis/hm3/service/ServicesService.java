package ru.itis.hm3.service;

import ru.itis.hm3.model.Car;

public interface ServicesService {

    Car changeWheelsInCar(Integer carId, boolean w1, boolean w2, boolean w3, boolean w4);
    Car fillWheels(Integer carId);
}
