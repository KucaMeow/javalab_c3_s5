package ru.itis.mongo.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.mongo.service.CarService;

@RepositoryRestController
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping(value = "cars/{car-id}/changeColour", method = RequestMethod.PUT)
    public ResponseEntity<?> changeColour(@RequestParam(value = "car-id") String id) {
        return ResponseEntity.ok(EntityModel.of(carService.changeCarColour(id)));
    }

}
