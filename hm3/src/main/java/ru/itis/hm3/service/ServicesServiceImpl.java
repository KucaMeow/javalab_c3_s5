package ru.itis.hm3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.hm3.model.Car;
import ru.itis.hm3.repository.CarRepository;
import ru.itis.hm3.repository.ServiceRepository;

import java.util.List;

@Service
public class ServicesServiceImpl implements ServicesService {

    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    CarRepository carRepository;

    @Override
    public Car changeWheelsInCar(Integer carId, boolean w1, boolean w2, boolean w3, boolean w4) {
        Car car = carRepository.findById(carId).get();
        int count = 0;
        count += w1 ? 1 : 0;
        count += w2 ? 1 : 0;
        count += w3 ? 1 : 0;
        count += w4 ? 1 : 0;

        List<ru.itis.hm3.model.Service> availableServices = serviceRepository.findAllBySizeOfWheels(count);

        if(availableServices.isEmpty()) {
            throw new IllegalStateException("No available services");
        }
        ru.itis.hm3.model.Service service = availableServices.get(0);

        int i = 0;
        if(w1){
            car.setW1(service.getWheels().get(i++));
            service.getWheels().remove(car.getW1());
        }
        if(w2){
            car.setW2(service.getWheels().get(i++));
            service.getWheels().remove(car.getW2());
        }
        if(w3){
            car.setW3(service.getWheels().get(i++));
            service.getWheels().remove(car.getW3());
        }
        if(w1){
            car.setW4(service.getWheels().get(i++));
            service.getWheels().remove(car.getW4());
        }

        return car;
    }

    @Override
    public Car fillWheels(Integer carId) {
        if(!carRepository.findById(carId).isPresent()) throw new IllegalArgumentException("Can't find");
        Car car = carRepository.findById(carId).get();
        car.getW1().setPressure(2.5);
        car.getW2().setPressure(2.5);
        car.getW3().setPressure(2.5);
        car.getW4().setPressure(2.5);
        carRepository.save(car);
        return car;
    }
}
