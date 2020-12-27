package ru.itis.hm3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.hm3.service.ServicesService;

@RepositoryRestController
public class ServiceController {

    @Autowired
    private ServicesService servicesService;

    @RequestMapping(value = "/cars/{car-id}/fill-wheels", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> fillWheels(@PathVariable("car-id") Integer carId) {
        return ResponseEntity.ok(EntityModel.of(servicesService.fillWheels(carId)));
//        return new ResponseEntity<>(servicesService.fillWheels(carId), HttpStatus.OK);
    }
}
