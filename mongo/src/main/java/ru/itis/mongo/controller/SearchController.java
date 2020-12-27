package ru.itis.mongo.controller;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.mongo.dto.DriverDto;
import ru.itis.mongo.dto.DriverRequest;
import ru.itis.mongo.model.Driver;
import ru.itis.mongo.repository.search.DriverByRequestRepository;
import ru.itis.mongo.repository.search.DriverRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class SearchController {

    @Autowired
    DriverByRequestRepository driverByRequestRepository;

    @GetMapping ("/driver/search/r")
    public ResponseEntity<List<DriverDto>> searchByRequest(DriverRequest request) {
        return ResponseEntity.ok(driverByRequestRepository.findByRequest(request));
    }

    @Autowired
    DriverRepository driverRepository;

    @GetMapping("/driver/search/p")
    public ResponseEntity<List<DriverDto>> searchByPredicate(@QuerydslPredicate(root = Driver.class,
            bindings = DriverRepository.class) Predicate predicate) {
        return ResponseEntity.ok(
                StreamSupport.stream(driverRepository.findAll(predicate).spliterator(), true)
                .map(DriverDto::from)
                        .collect(Collectors.toList())
        );
    }
}
