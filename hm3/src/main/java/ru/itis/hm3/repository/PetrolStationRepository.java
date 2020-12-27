package ru.itis.hm3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.hm3.model.PetrolStation;

@Repository
public interface PetrolStationRepository extends JpaRepository<PetrolStation, Integer> {
    PetrolStation findByPetrolTankContentGreaterThan (double content);
}
