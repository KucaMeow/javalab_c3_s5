package ru.itis.hm3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.hm3.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
