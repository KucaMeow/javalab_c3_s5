package ru.itis.mongo.repository.search;

import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itis.mongo.dto.DriverDto;
import ru.itis.mongo.dto.DriverRequest;
import ru.itis.mongo.model.Driver;
import ru.itis.mongo.repository.search.DriverRepository;

import static ru.itis.mongo.model.QDriver.driver;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DriverByRequestRepositoryImpl implements DriverByRequestRepository{

    @Autowired
    DriverRepository driverRepository;

    @Override
    public List<DriverDto> findByRequest(DriverRequest driverRequest) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (driverRequest.getCarColour() != null) {
            predicate.or(driver.cars.any().colour.containsIgnoreCase(driverRequest.getCarColour()));
        }
        if (driverRequest.getCarMaxSpeed() != null) {
            predicate.or(driver.cars.any().maxSpeed.between(driverRequest.getCarMaxSpeed()-10, driverRequest.getCarMaxSpeed()+10));
        }
        if(driverRequest.getCash() != null) {
            predicate.or(driver.cash.between(driverRequest.getCash()-10, driverRequest.getCash()+10));
        }
        if(driverRequest.getName() != null) {
            predicate.or(driver.name.containsIgnoreCase(driverRequest.getName()));
        }
        List<DriverDto> toR = new ArrayList<>();
        driverRepository.findAll(predicate.getValue()).forEach(a -> toR.add(DriverDto.from(a)));

        return toR;
    }
}
