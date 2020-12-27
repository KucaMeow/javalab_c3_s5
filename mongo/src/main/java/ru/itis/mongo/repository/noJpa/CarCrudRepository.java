package ru.itis.mongo.repository.noJpa;

import ru.itis.mongo.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarCrudRepository {
    List<Car> findAll();
    Optional<Car> find(String _id);
    Car save(Car car);
    Car update(Car car);
    Car delete(Car car);
}
