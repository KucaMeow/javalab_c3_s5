package ru.itis.hm3.config;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.itis.hm3.controller.ServiceController;
import ru.itis.hm3.model.Car;
import ru.itis.hm3.service.ServicesService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CarRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Car>> {
    @Override
    public EntityModel<Car> process(EntityModel<Car> model) {
        Car car = model.getContent();

        if (car != null) {
            model.add(linkTo(methodOn(ServiceController.class)
                    .fillWheels(car.getId())).withRel("fill-wheels"));
        }
        return model;
    }
}
