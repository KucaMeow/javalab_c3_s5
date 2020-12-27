package ru.itis.hm3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.hm3.model.PetrolTank;

@Repository
public interface PetrolTankRepository extends JpaRepository<PetrolTank, Integer> {
}
