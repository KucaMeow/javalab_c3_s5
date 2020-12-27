package ru.itis.mongo.repository.noJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ru.itis.mongo.model.Car;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class CarCrudRepositoryTemplateImpl implements CarCrudRepository {

    private final MongoTemplate template;

    public CarCrudRepositoryTemplateImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public List<Car> findAll() {
        return template.findAll(Car.class, "cars");
    }

    @Override
    public Optional<Car> find(String _id) {
        return Optional.ofNullable(template
                .findOne(new Query(where("_id").is(_id)), Car.class, "cars"));
    }

    @Override
    public Car save(Car car) {
        return template.save(car, "cars");
    }

    @Override
    public Car update(Car car) {
        return save(car);
    }

    @Override
    public Car delete(Car car) {
        template.remove(car, "cars");
        car.set_id(null);
        return car;
    }
}
