package ru.itis.hm3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.hm3.model.Car;
import ru.itis.hm3.model.PetrolStation;
import ru.itis.hm3.repository.PetrolStationRepository;

@Service
public class PetrolService {

    @Autowired
    PetrolStationRepository petrolStationRepository;

    public void fillCarTank(Car car) {
        double need = 45 - car.getPetrolTank().getContent();
        PetrolStation petrolStation = petrolStationRepository.findByPetrolTankContentGreaterThan(need);
        if(petrolStation == null) {
            throw new IllegalStateException("No available petrol stations");
        }
        petrolStation.getPetrolTank().setContent(petrolStation.getPetrolTank().getContent() - need);
        car.getPetrolTank().setContent(45);
    }
}
