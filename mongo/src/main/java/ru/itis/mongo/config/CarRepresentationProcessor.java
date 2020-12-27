package ru.itis.mongo.config;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.itis.mongo.controller.CarController;
import ru.itis.mongo.model.Car;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CarRepresentationProcessor implements RepresentationModelProcessor<EntityModel<Car>> {
    @Override
    public EntityModel<Car> process(EntityModel<Car> model) {
        Car car = model.getContent();

        if(car != null) {
            model.add(linkTo(methodOn(CarController.class)
                    .changeColour(car.get_id())).withRel("changeColour"));
        }

        return model;
    }
}
