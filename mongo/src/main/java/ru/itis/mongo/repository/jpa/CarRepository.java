package ru.itis.mongo.repository.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.itis.mongo.model.Car;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {
}
