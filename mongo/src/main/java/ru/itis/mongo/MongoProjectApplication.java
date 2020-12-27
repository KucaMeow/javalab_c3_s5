package ru.itis.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.itis.mongo.model.Car;
import ru.itis.mongo.model.Driver;
import ru.itis.mongo.repository.jpa.CarRepository;
import ru.itis.mongo.repository.jpa.DriverRepository;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class MongoProjectApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MongoProjectApplication.class, args);

        /*CarRepository carRepository = context.getBean(CarRepository.class);
        DriverRepository driverRepository = context.getBean(DriverRepository.class);

        Car car = Car.builder()
                .colour("red")
                .features(Arrays.asList("fast", "small"))
                .maxSpeed(100)
                .build();

        Car car1 = Car.builder()
                .colour("black")
                .features(Collections.singletonList("big"))
                .maxSpeed(150)
                .build();

        Car car2 = Car.builder()
                .colour("red")
                .features(Arrays.asList("slow", "small"))
                .maxSpeed(70)
                .build();

        Car car3 = Car.builder()
                .colour("green")
                .features(Arrays.asList("fast", "small"))
                .maxSpeed(130)
                .build();

        carRepository.saveAll(Arrays.asList(car, car1, car2, car3));

        Driver driver1 = Driver.builder()
                .cars(Collections.singletonList(car))
                .name("Alex")
                .cash(1700.50)
                .build();
        Driver driver2 = Driver.builder()
                .cars(Arrays.asList(car1, car3))
                .name("Felix")
                .cash(500)
                .build();

        driverRepository.saveAll(Arrays.asList(driver1, driver2));*/
    }

}
