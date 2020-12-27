package ru.itis.mongo.repository.search;

import ru.itis.mongo.dto.DriverDto;
import ru.itis.mongo.dto.DriverRequest;

import java.util.List;

public interface DriverByRequestRepository {
    List<DriverDto> findByRequest(DriverRequest driverRequest);
}
