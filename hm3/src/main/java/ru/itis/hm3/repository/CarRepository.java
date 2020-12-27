package ru.itis.hm3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.hm3.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
}
